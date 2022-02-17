package com.exchange.exchangeApp.repository;

import com.exchange.exchangeApp.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepo extends JpaRepository<Exchange, Integer> {

    Optional<Exchange> findByTransactionId(int transactionId);

    Optional<List<Exchange>> findByConversionDate(Date conversiondate);



}
