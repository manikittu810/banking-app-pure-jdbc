package com.jdbc_banking_app.DAO;

import com.jdbc_banking_app.utility.DBUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    private static  final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

    public void transferAmount(int fromAccountId, int toAccountId, double amount) {
        String debitSQL = "update accounts SET balance = balance - ? where id = ?";
        String creditSQL = "update accounts SET balance = balance + ? where id = ?";

        try (Connection con = DBUtility.getConnection()) {
            logger.info("initiating transfer : fromAccountId={}, toAccountId={}, amount={}",fromAccountId,toAccountId,amount);
            printAccountBalances(con, "Before Transaction : ");
            con.setAutoCommit(false);

            if(!hasSufficientBalance(con,fromAccountId,amount,10)){
                logger.warn("Insufficient balance in the account  : {}",fromAccountId);
//                System.out.println("Insufficient balance. Minimum required balance after transfer: $10.00");
                return ;
            }

            try (PreparedStatement debitStmt = con.prepareStatement(debitSQL);
                 PreparedStatement creditStmt = con.prepareStatement(creditSQL)) {

                //for debiting money
                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAccountId);
                debitStmt.executeUpdate();
                logger.debug("debited {} from account : {}",amount,fromAccountId);

                //for crediting money

                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAccountId);
                creditStmt.executeUpdate();
                logger.debug("credited {} into account : {}", amount,toAccountId);


                con.commit();
                System.out.println(" \n Transfer Successful");
                printAccountBalances(con, "After Transaction");


            } catch (SQLException e) {
                con.rollback();
//                System.out.println("Transfer failed. Rolled back");
                logger.error("Transfer failed. Transaction rolled back.", e);
                e.printStackTrace();
            }

        } catch (Exception e) {
//            throw new RuntimeException("Database error", e);
            logger.error("Database error",e);
        }
    }

    private void printAccountBalances(Connection con, String label) throws SQLException {
        String query = "SELECT id, name, balance, status, last_updated FROM accounts";
        String filePath = "/Users/smk/Desktop/FC_Integration/banking-application/accounts_log.txt";

        try (
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                FileWriter fw = new FileWriter(filePath, true);
                PrintWriter pw = new PrintWriter(fw)) {
            String header = "\n ---------------------" + label + "------------\n";
            String columns = String.format("%-5s %-15s %-10s %-10s %-20s", "ID", "NAME", "BALANCE", "STATUS", "LAST_UPDATED");
            String separator = "--------------------------------------------------------------";

            System.out.println(header + columns + "\n" + separator);
            pw.println(header + columns + "\n" + separator);
            logger.info("--------------{}-----------",label);
            while(rs.next()) {
                logger.info("ID :{},Name : {},Balance :{}",rs.getInt(1),rs.getString(2),rs.getDouble(3));
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                String status = rs.getString("status");
                String last_updated = rs.getString("last_updated");

                String row = String.format("%-5d %-15s %-10.2f %-10s %-20s", id, name, balance, status, last_updated);
                System.out.println(row);
                pw.println(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasSufficientBalance(Connection con,int fromId, double transferAmount, double minBalance) throws SQLException{
        String balanceCheckQuery = "select balance from accounts where id = ?";
        try(PreparedStatement stmt = con.prepareStatement(balanceCheckQuery)){
            stmt.setInt(1,fromId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                double currentBalance = rs.getDouble("balance");
                return currentBalance - transferAmount >= minBalance;
            }else{
                System.out.println("from account doesn't exist");
                return false;
            }
        }catch (SQLException e){
            throw new SQLException("No balance available",e);
        }
    }
}
