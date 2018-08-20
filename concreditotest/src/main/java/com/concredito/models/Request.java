package com.concredito.models;

import com.concredito.database.DataBaseHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Request extends DataBaseHandler {

    private String table;

    public Request() {
        this.table = "Requests";
        openConnection();
    }

    public int getNextRequest() {
        try {
            Statement stmt = conn.createStatement();
            if (stmt.execute("SELECT id FROM " + this.table + " where status = 2 order by amount desc limit 1 ")) {
                ResultSet rs = stmt.getResultSet();
                return rs.next() ? rs.getInt("id") : 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void processRequests(int id) {
        try {
            if(id == 0) {
                System.out.println("No hay ninguna solicitud de crédito pendiente.");
                return;
            }

            int status = (this.hasCreditCard(id) && this.isOlderThan20(id)) ? 1 : 3;

            String query = "update " + table + " set status = ? where status = 2 and id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, status);
            preparedStmt.setInt(2, id);
            preparedStmt.executeUpdate();

            if (status == 1) System.out.println("La solicitud de crédito con ID " + id + " fue aceptada.");
            else System.out.println("La solicitud de crédito con ID " + id + " fue rechazada.");
            closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean hasCreditCard(int id) {
        try {
            Statement stmt = conn.createStatement();
            if (stmt.execute("SELECT creditCard FROM " + table + " where id =" + id)) {
                ResultSet rs = stmt.getResultSet();
                while(rs.next()) {
                    return rs.getInt("creditCard")==1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean isOlderThan20(int id) {
        try {
            Statement stmt = conn.createStatement();
            if (stmt.execute("SELECT age FROM " + table + " where id = " + id)) {
                ResultSet rs = stmt.getResultSet();
                while(rs.next()) {
                    return rs.getInt("age")>=20;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
