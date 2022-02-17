package com.exchange.exchangeApp.service;

import com.exchange.exchangeApp.model.Exchange;
import com.exchange.exchangeApp.repository.ExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExchangeService {

    @Autowired
    private ExchangeRepo repo;

    public Optional<Exchange> findByTransactionId (int transactionId) {
        return repo.findByTransactionId(transactionId);
    }

    public Optional<List<Exchange>> findByConversionDate(Date conversionDate) {
        return repo.findByConversionDate(conversionDate);
    }

    public int saveExchange(Exchange exchange) {
        repo.save(exchange);
        return exchange.getTransactionId();
    }
}
