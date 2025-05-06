package com.jdbc_banking_app.account_details;

public class Account {
    private int id;
    private String name;
    private double balance;

    public Account(int id,String name, double balance){
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void setId(){
        this.id = id;
    }
    public void setName(){
        this.name = name;
    }
    public void setBalance(){
        this.balance = balance;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getBalance(){
        return balance;
    }

    @Override
    public String toString(){
        return "{ Account_id :" + id +
                "Account_Holder_Name : " + name +
                "balance : " + balance +
                "}";
    }

}
