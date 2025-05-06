package com.jdbc_banking_app;

import com.jdbc_banking_app.service_layer_2.BankingService;

public class Main {
    public static void main(String[] args) {
        BankingService bankingService = new BankingService();
        bankingService.performTransfer(1,2,20);
    }
}