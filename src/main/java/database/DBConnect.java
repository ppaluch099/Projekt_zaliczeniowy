package database;

import com.mysql.jdbc.Connection;

import java.sql.*;

public class DBConnect{
    private Connection connection ;

    String url = "jdbc:mysql://localhost:3306/baza_produktow";
    String user = "root";
    String pass = "";
        Class.forName("com.mysql.jdbc.Driver");
    java.sql.Connection con = DriverManager.getConnection(url, user, pass);
}
