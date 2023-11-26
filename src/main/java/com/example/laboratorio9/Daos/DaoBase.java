package com.example.laboratorio9.Daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DaoBase {
    public Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }

        String username = "root";
        String password = "root";
        String database = "lab_9";
        String url = "jdbc:mysql://localhost:3306/"+database;

        return DriverManager.getConnection(url,username,password);
        //return DriverManager.getConnection("jdbc:mysql://34.176.190.19:3306/"+database,username,"T2hHs4}SEU>k-c&i");
    }
}
