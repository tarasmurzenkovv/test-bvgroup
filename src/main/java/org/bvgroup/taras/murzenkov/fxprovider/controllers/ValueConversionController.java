package org.bvgroup.taras.murzenkov.fxprovider.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValueConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.request.ValuesConversionRequest;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.ValueConversion;
import org.bvgroup.taras.murzenkov.fxprovider.service.ValueConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "value-conversions", description = "Value conversition between currencies")
@RestController
@RequestMapping("/api/v1/fx-rates")
@RequiredArgsConstructor
public class ValueConversionController {
    private final ValueConversionService valueConversionService;

    @Operation(
            summary = "Converts from the amount in given currency to provided ones"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The conversion operation is successful",
                            content = {@Content(schema = @Schema(implementation = ValueConversion.class))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If the currency from is equal to currency to")
                    ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @PostMapping("/value-conversion/{currencyFrom}/{currencyTo}")
    @ResponseStatus(CREATED)
    public ValueConversion convertValue(
            @PathVariable String currencyFrom,
            @PathVariable String currencyTo,
            @RequestParam BigDecimal amount) {
        return valueConversionService.convert(new ValueConversionRequest(currencyFrom, currencyTo, amount));
    }


    @Operation(
            summary = "Converts from the amount in given currency to provided list of the provides ones"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The conversion operation is successful",
                            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ValueConversion.class)))}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If the currency from is equal to currency to")
                    ,
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @PostMapping("/value-conversion/{currencyFrom}")
    @ResponseStatus(CREATED)
    public List<ValueConversion> convertValues(
            @PathVariable String currencyFrom,
            @RequestBody ValuesConversionRequest valuesConversionRequest) {
        return valueConversionService.convert(currencyFrom, valuesConversionRequest);
    }
}
