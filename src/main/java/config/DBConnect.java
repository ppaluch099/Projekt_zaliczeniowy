package config;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect{
    protected Connection connection;

    public Connection getConnection(){
            final String url="jdbc:mysql://localhost:3306/galeria";
            final String uname = "root";
            final String pass = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, uname, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}