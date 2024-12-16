package org.example.retoconjuntohibernate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Util {
    private static Connection conn = null;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DI_JasperReports", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
