package com.jdbc_banking_app.service;

import com.jdbc_banking_app.exception.BankingException;
import com.jdbc_banking_app.DAO.AccountDAO;

public class BankingService {
    private final AccountDAO accountDAO = new AccountDAO();
     public void performTransfer(int fromId,int toId, double amount){
         if(amount<=0){
             throw new BankingException("Amount is less than or equal to zero, enter amount greater than zero.");
         }
         if(fromId == toId){
             throw new BankingException("Cannot send funds from "+ fromId + "to " +toId);
         }
             accountDAO.transferAmount(fromId, toId, amount);
     }
}
