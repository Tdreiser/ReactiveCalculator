package ru.shlokov.reactivecalculator.service;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class FluxBuilder {

    // TODO: check it
    @Value("${customDelay}")
    private long delay;


    public Flux<String> createNotOrderedBuild(String firstFunction, String secondFunction, Long numberOfCalculates) {
        /*
        Метод для несортированной выдачи результатов функци
        */
        Flux<String> fluxFunction1 = Flux.interval(Duration.ofSeconds(delay))
                .flatMap(sequence -> PythonFunction.scriptReader(firstFunction, sequence, true))
                .map(o -> "1," + o + "\n")//номер функции
                .take(numberOfCalculates + 1);

        Flux<String> fluxFunction2 = Flux.interval(Duration.ofSeconds(delay))
                .flatMap(sequence -> PythonFunction.scriptReader(secondFunction, sequence, true))
                .map(o -> "2," + o + "\n")//номер функции
                .take(numberOfCalculates + 1);

        String header = "Function №, Iteration №,Result,Time of execution(mc)\n";

        return Flux.merge(fluxFunction1, fluxFunction2)
                .startWith(header);//шапка таблицы только для удобства, её можно убрать
    }

    public Flux<String> createOrderedBuild(String firstFunction, String secondFunction, Long numberOfCalculates) {
        /*
        Метод для сортированной выдачи результатов функци
        */
        Flux<String> fluxFunction1 = Flux.interval(Duration.ofSeconds(delay))
                .flatMap(sequence -> PythonFunction.scriptReader(firstFunction, sequence, true));

        Flux<String> fluxFunction2 = Flux.interval(Duration.ofSeconds(delay))
                .flatMap(sequence -> PythonFunction.scriptReader(secondFunction, sequence, false));

        String header = "Iteration №,Function №1, Time of execution(mc),Function №2, Time of execution(mc)\n";

        return Flux.zip(fluxFunction1, fluxFunction2)
                .flatMap(a -> Flux.just(a.getT1() + a.getT2() + "\n"))
                .startWith(header)//шапка таблицы только для удобства, её можно убрать
                .take(numberOfCalculates + 1);
    }

}
