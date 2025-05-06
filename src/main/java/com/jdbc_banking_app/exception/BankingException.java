package com.jdbc_banking_app.exception;

public class BankingException extends RuntimeException{
    public BankingException(String message){
        super(message);
    }

    public BankingException(String message, Throwable cause){
        super(message,cause);
    }
}
