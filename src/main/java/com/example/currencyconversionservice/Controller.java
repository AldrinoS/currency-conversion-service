package com.example.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class Controller {

    @Autowired
    CurrencyExchangeProxy proxy;

    public static final String CURRENCY_EXCHANGE_URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable String from,
                                                  @PathVariable String to, @PathVariable BigDecimal quantity) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> currencyConversion = new RestTemplate().getForEntity(CURRENCY_EXCHANGE_URL, CurrencyConversion.class, uriVariables);

        CurrencyConversion result = currencyConversion.getBody();
        result.setQuantity(quantity);
        result.setTotalCalculatedAmount(quantity.multiply(result.getConversionMultiple()));
        result.setEnvironment(result.getEnvironment() + " Rest Template");

        return result;
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionFeign(@PathVariable String from,
                                                  @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversion result = proxy.retrieve(from, to);
        result.setQuantity(quantity);
        result.setTotalCalculatedAmount(quantity.multiply(result.getConversionMultiple()));
        result.setEnvironment(result.getEnvironment() + " Feign");

        return result;
    }

}
