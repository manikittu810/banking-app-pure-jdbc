package com.jdbc_banking_app.service_layer_2;

import com.jdbc_banking_app.service_layer_1.AccountDAO;

public class BankingService {
    private final AccountDAO accountDAO = new AccountDAO();
     public void performTransfer(int fromId,int toId, double amount){
        accountDAO.transferAmount(fromId,toId,amount);
     }
}
