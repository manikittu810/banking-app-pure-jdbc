package com.jdbc_banking_app.service_layer_1;

import com.jdbc_banking_app.utility.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {

    public void transferAmount(int fromAccountId, int toAccountId, double amount) {
        String debitSQL = "update accounts SET balance = balance - ? where id = ?";
        String creditSQL = "update accounts SET balance = balance + ? where id = ?";

        try (Connection con = DBUtility.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement debitStmt = con.prepareStatement(debitSQL);
                    PreparedStatement creditStmt = con.prepareStatement(creditSQL)) {

                //for debiting money
                debitStmt.setDouble(1,amount);
                debitStmt.setInt(2,fromAccountId);
                debitStmt.executeUpdate();

                //for crediting money

                creditStmt.setDouble(1,amount);
                creditStmt.setInt(2,toAccountId);
                creditStmt.executeUpdate();


                con.commit();
                System.out.println(" Transfer Successful");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Transfer failed. Rolled back");
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException("Database error",e);
        }
    }
}
