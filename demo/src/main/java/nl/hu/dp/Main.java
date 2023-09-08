package nl.hu.dp;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        try{
            database.testConnection();
        } catch (SQLException e){
            System.err.println(e);
        }
    }
}