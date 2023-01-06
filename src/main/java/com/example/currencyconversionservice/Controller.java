package com.example.currencyconversionservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class Controller {

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to, @PathVariable BigDecimal quantity) {
        return new CurrencyConversion(1000L, from, to, BigDecimal.valueOf(65), quantity, quantity.multiply(BigDecimal.valueOf(65)), "8000");
    }

}
