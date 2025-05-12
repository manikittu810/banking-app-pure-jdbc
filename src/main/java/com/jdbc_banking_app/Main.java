package com.jdbc_banking_app;

import com.jdbc_banking_app.service.BankingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankingService bankingService = new BankingService();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter from Account_ID (Debit action) : " );
        int fromId = sc.nextInt();
        System.out.println("Enter to  Account_ID (Credit action) : " );
        int toId = sc.nextInt();
        System.out.println("Enter Amount to transfer from " + fromId + " to " + toId);
        double amount = sc.nextDouble();
        System.out.println("Initiating transfer of amount : "+ amount +" from account_ID : " + fromId + " to account_ID "+ toId);
        bankingService.performTransfer(fromId,toId,amount);
    }
}