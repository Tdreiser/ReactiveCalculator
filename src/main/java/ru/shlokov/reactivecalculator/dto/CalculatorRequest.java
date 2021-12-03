package ru.shlokov.reactivecalculator.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculatorRequest {

    @ApiModelProperty(example = "abs(-30)")
    private String firstFunction;

    @ApiModelProperty(example = "max(1,2,3)")
    private String secondFunction;

    @ApiModelProperty(example = "2")
    private long numberOfCalculates;

    @ApiModelProperty(example = "true")
    private boolean isOrdered;
}
