/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kristina.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kristina.exception.prodavnica_exception;

public class ResourcesManager {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/computerstore?user=root&password=");
        return con;
    }

    public static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }

    public static void closeConnection(Connection con) throws prodavnica_exception {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new prodavnica_exception("Failed to close database connection.", ex);
            }
        }
    }

    public static void rollbackTransactions(Connection con) throws prodavnica_exception {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new prodavnica_exception("Failed to rollback database transactions.", ex);
            }
        }
    }
}

