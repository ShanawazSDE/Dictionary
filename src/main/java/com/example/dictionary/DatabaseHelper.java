package com.example.dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
public class DatabaseHelper {
    private static final String url = "jdbc:mysql://localhost:3306/dictionary";
    private static final String user = "root";
    private static final String password = "mySqlLqsym";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    String query = "";

    public Connection makeConnection() {
        try {
           // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }



    public String search(String word) {
        if(connection == null){
            makeConnection();
        }
        StringBuilder ans = new StringBuilder();
        try {
            query = String.format("select meaning from dictionary_table where word = '%s'",word);
            resultSet = statement.executeQuery(query);
            while(resultSet != null && resultSet.next()){
                //System.out.println(resultSet.getString("meaning"));
                ans.append(resultSet.getString("meaning"));
                ans.append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ans.toString();
    }

    public ObservableList<String> getWords() throws SQLException {
        if(connection == null){
            makeConnection();
        }
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
             resultSet = statement.executeQuery("select word from dictionary_table");
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (resultSet != null && resultSet.next()){
              list.add(resultSet.getString(1));
            //  System.out.println(resultSet.getString("word"));
        }
        return list;
    }

    public static void main(String[] args) throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.makeConnection();
        String s = databaseHelper.search("Azure ");
        System.out.println(s);
       // ObservableList<String> list = databaseHelper.getWords();
        //System.out.println(list.get(10));
    }
}
