package com.concredito.database;

import java.sql.*;

public abstract class DataBaseHandler {
    protected Connection conn;

    protected void openConnection() {
        try {
           // Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ConcreditoExamen?user=root&password=arduino123&serverTimezone=UTC&autoReconnect=true&useSSL=false");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
