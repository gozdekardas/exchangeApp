package com.exchange.exchangeApp.controller;

import com.exchange.exchangeApp.exceptions.ConversionDateNotFound;
import com.exchange.exchangeApp.exceptions.ExchangeApiError;
import com.exchange.exchangeApp.exceptions.TransactionNotFoundException;
import com.exchange.exchangeApp.model.Exchange;
import com.exchange.exchangeApp.model.Latest;
import com.exchange.exchangeApp.model.Rate;
import com.exchange.exchangeApp.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchangeApi")
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;
    @Autowired
    RestTemplate restTemplate;
    private String url = "http://data.fixer.io/api/latest?access_key={key}&base={fromCur}&symbols=TRY,USD,CHF";
    private String apiKey = "2b2eebe45fe1d29c38797733f68f79ac";

    //get conversion with transaction id
    @GetMapping("/conversion/{transactionId}")
    ResponseEntity<Exchange> retrieveExchanges(@PathVariable(required = true) int transactionId) {

        Exchange conversions = exchangeService.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException());
        return ResponseEntity.ok().body(conversions);
    }

    //get conversions with conversion date
    @GetMapping("/conversions")
    ResponseEntity<List<Exchange>> retrieveExchanges(@RequestParam(required = true) Date conversionDate) {

        List<Exchange> conversions = exchangeService.findByConversionDate(conversionDate)
                .orElseThrow(() -> new ConversionDateNotFound());


        return ResponseEntity.ok().body(conversions);


    }

    //get exchangeRate
    @GetMapping("/exchangeRate")
    Rate exchangeRate(@RequestParam String fromCurrency, @RequestParam String toCurrency) {
        System.out.println(fromCurrency);
        System.out.println(toCurrency);

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("fromCur", fromCurrency);
        uriVariables.put("key", apiKey);


        Latest latestRates = restTemplate.getForObject(
                url, Latest.class, uriVariables);

        if(!latestRates.getSuccess())throw new ExchangeApiError();

        System.out.println(latestRates.getRates().getCHF());
        System.out.println(latestRates.getRates().getUSD());
        System.out.println(latestRates.getRates().getTRY());


        Double exchangeRate = 0.0;
        if (toCurrency.equals("USD")) {
            exchangeRate = latestRates.getRates().getUSD();
        } else if (toCurrency.equals("CHF")) {
            exchangeRate = latestRates.getRates().getCHF();
        } else if (toCurrency.equals("TRY")) {
            exchangeRate = latestRates.getRates().getTRY();
        }

        Rate rate = new Rate();
        rate.setRate(exchangeRate);

        return rate;


    }

    //get and set exchange rate and amount
    @PostMapping("/exchange")
    Exchange exchange(@RequestParam Double sourceAmount, @RequestParam String sourceCurrency, @RequestParam String targetCurrency) {
        System.out.println(sourceAmount);
        System.out.println(sourceCurrency);
        System.out.println(targetCurrency);

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("fromCur", sourceCurrency);
        uriVariables.put("key", apiKey);

        Latest latestRates = restTemplate.getForObject(
                url, Latest.class, uriVariables);

        if(!latestRates.getSuccess())throw new ExchangeApiError();

        Double newAmount = 0.0;
        if (targetCurrency.equals("USD")) {
            newAmount = sourceAmount * latestRates.getRates().getUSD();
        } else if (targetCurrency.equals("CHF")) {
            newAmount = sourceAmount * latestRates.getRates().getCHF();
        } else if (targetCurrency.equals("TRY")) {
            newAmount = sourceAmount * latestRates.getRates().getTRY();
        }

        Date date = Date.valueOf(LocalDate.now());

        Exchange newExchange = new Exchange();
        newExchange.setSourceAmount(sourceAmount);
        newExchange.setSourceCurrency(sourceCurrency);
        newExchange.setTargetCurrency(targetCurrency);
        newExchange.setConversionDate(date);
        newExchange.setTargetAmount(newAmount);


        int transactionId = exchangeService.saveExchange(newExchange);
        newExchange.setTransactionId(transactionId);

        return newExchange;
    }
}
