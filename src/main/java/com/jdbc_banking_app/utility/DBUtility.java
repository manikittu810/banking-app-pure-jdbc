package com.jdbc_banking_app.utility;

import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBUtility {
    private static HikariDataSource hikariDataSource;
    static {

        try (InputStream inputStream = DBUtility.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(inputStream);

            HikariDataSource config = new HikariDataSource();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("db.timeout", "5000")) * 1000);
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.poolSize", "10")));

            hikariDataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception{
        return hikariDataSource.getConnection();
    }

    public static void close(){
        if(hikariDataSource!= null){
            hikariDataSource.close();
        }
    }


}
