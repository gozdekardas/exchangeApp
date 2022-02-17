package com.exchange.exchangeApp;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exchange.exchangeApp.exceptions.ConversionDateNotFound;
import com.exchange.exchangeApp.exceptions.TransactionNotFoundException;
import com.exchange.exchangeApp.model.Exchange;
import com.exchange.exchangeApp.service.ExchangeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class TestController extends ExchangeAppApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ExchangeService exchangeService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void exchangeApiOk()
            throws Exception {


        mockMvc.perform(get("/exchangeApi/exchangeRate?fromCurrency=EUR&toCurrency=USD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void exchange()
            throws Exception {

        mockMvc.perform(get("/exchangeApi/exchangeRate?fromCurrency=EUR&toCurrency=USD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rate").value(1.13125))
        ;

    }

    @Test
    public void exchangeRate()
            throws Exception {

        mockMvc.perform(get("/exchangeApi/exchangeRate?fromCurrency=EUR&toCurrency=USD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
    }


    @Test
    public void testFindTransactionId() {
        Exchange exchange = exchangeService.findByTransactionId(5).orElseThrow(TransactionNotFoundException::new);
        Assertions.assertEquals(5, exchange.getTransactionId());
    }

    @Test
    public void testListByConversionDate() {
        Date date = Date.valueOf("2022-02-14");

        List<Exchange> exchange = exchangeService.findByConversionDate(date).orElseThrow(ConversionDateNotFound::new);
        Assertions.assertEquals(6, exchange.get(0).getTransactionId());
        Assertions.assertEquals(7, exchange.get(1).getTransactionId());
        Assertions.assertEquals(8, exchange.get(2).getTransactionId());
        Assertions.assertEquals(9, exchange.get(3).getTransactionId());
    }

    @Test
    public void testAddConversion() {

        Date date = Date.valueOf(LocalDate.now());

        Exchange newExchange = new Exchange();
        newExchange.setSourceAmount(10);
        newExchange.setSourceCurrency("EUR");
        newExchange.setTargetCurrency("TRY");
        newExchange.setConversionDate(date);
        newExchange.setTargetAmount(20);


        int transactionId = exchangeService.saveExchange(newExchange);
        Assertions.assertEquals(13, transactionId);
    }

}
