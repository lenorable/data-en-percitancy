package nl.hu.dp.p1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sql {
    connect con = new connect();

    public void getAllUsers() throws SQLException{
        Connection conect = con.getCon();

        Statement statement = conect.createStatement();

        String query = "SELECT * FROM reiziger;";
        ResultSet resultSet = statement.executeQuery(query);
    
        ArrayList<Map> names = new ArrayList<Map>();

        while (resultSet.next()){
            Map tijdelijk = new HashMap<String, String>();

            tijdelijk.put(resultSet.getString(2), resultSet.getString(4));
        
            names.add(tijdelijk);
        }

        for (Map set : names){
            System.out.println(set.entrySet());
        }
        
        statement.close();

    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        sql sql = new sql();

        sql.getAllUsers();
    }
}
