package com.exchange.exchangeApp.exceptions;

import com.exchange.exchangeApp.model.NewError;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<Object> exception(TransactionNotFoundException exception) {

        NewError newError = new NewError();

        newError.setCode(001);
        newError.setMessage("Transaction Id not found");

        return new ResponseEntity<>(newError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConversionDateNotFound.class)
    public ResponseEntity<Object> exceptionConversionDate(ConversionDateNotFound exception) {

        NewError newError = new NewError();

        newError.setCode(002);
        newError.setMessage("ConversionDate not found");

        return new ResponseEntity<>(newError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ExchangeApiError.class)
    public ResponseEntity<Object> exceptionApi() {

        NewError newError = new NewError();

        newError.setCode(003);
        newError.setMessage("Kur Bilgisi Alırken Hata");

        return new ResponseEntity<>(newError, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    NewError showCustomMessage(Exception e){

        NewError newError = new NewError();

        newError.setCode(004);
        newError.setMessage("Your input is invalid");

        return newError;
    }
}
