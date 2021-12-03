package ru.shlokov.reactivecalculator.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.shlokov.reactivecalculator.dto.CalculatorRequest;
import ru.shlokov.reactivecalculator.service.FluxBuilder;
import ru.shlokov.reactivecalculator.validator.ParamValidator;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {
    private final FluxBuilder fluxBuilder;
    private final ParamValidator paramValidator;


    @ApiOperation(value = "Method for execute python2.7 functions", response = Flux.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "functions calculates"),
            @ApiResponse(code = 400, message = "bad input")
    })

    @PostMapping(produces = "text/csv")
    public ResponseEntity<Flux<String>> calculate(@RequestBody CalculatorRequest request) {

        String firstFunction = request.getFirstFunction();
        String secondFunction = request.getSecondFunction();
        long numberOfCalculates = request.getNumberOfCalculates();
        boolean isOrdered = request.isOrdered();

        if (!paramValidator.isValidParameter(numberOfCalculates)) {
            return new ResponseEntity<>(Flux.just("please check params in body"), HttpStatus.BAD_REQUEST);
        }
        boolean isValidFunctions = paramValidator.isValidFunction(firstFunction)
                && paramValidator.isValidFunction(secondFunction);

        HttpStatus status = isValidFunctions
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        Flux<String> fluxForResponse = isOrdered
                ? fluxBuilder.createOrderedBuild(firstFunction, secondFunction, numberOfCalculates)
                : fluxBuilder.createNotOrderedBuild(firstFunction, secondFunction, numberOfCalculates);

        return new ResponseEntity<>(fluxForResponse, status);
    }

}
