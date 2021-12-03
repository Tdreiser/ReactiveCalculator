package ru.shlokov.reactivecalculator.service;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import reactor.core.publisher.Mono;

public class PythonFunction {
    public static Mono<String> scriptReader(String script, long numberOfIteration, boolean withIterationNumber) {
        /*
        На вход подаётся простой скрипт который обязательно должен вернуть Long
        (Можно попроовать учесть момент со всеми сторонними импортами в интерпрритаторе с
        обьявлениями функций итд, а не только result = какая-нибудь лямбда)
        Для exec лучше ограничить ввод исключительно функциями для работы с Int.
        * */

        String result;
        try(PythonInterpreter pythonInterpreter = new PythonInterpreter()) {
            long startTime = System.currentTimeMillis();
            pythonInterpreter.exec("result =" + script);
            long finishTime = System.currentTimeMillis();

            result = (withIterationNumber
                        ? ("" + (numberOfIteration + 1L))
                        : "")
                    + "," + pythonInterpreter.get("result").toString()
                    + "," + (finishTime - startTime);

        }catch (PyException err) { //Можно как нибудь парсить стектрейс только для вывода сообщения
            return Mono.just("Wrong python function: " + err);
        }
        return Mono.just(result);
    }
}
