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
            System.out.println("Učitavanje MySQL JDBC drajvera...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // savremenija verzija
            System.out.println("MySQL JDBC drajver uspešno učitan.");
        } catch (ClassNotFoundException ex) {
            System.err.println("Greška: JDBC drajver nije pronađen.");
        }
    }

    // Konekcija na bazu
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/computer_store?user=root&password=&useSSL=false&serverTimezone=UTC";
        System.out.println("Povezivanje na bazu sa URL-om: " + url);
        Connection con = DriverManager.getConnection(url);
        con.setAutoCommit(false); // isključi autocommit
        System.out.println("Konekcija uspostavljena, autocommit isključen.");
        return con;
    }

    // Zatvaranje ResultSet i PreparedStatement
    public static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (resultSet != null) {
            try {
                resultSet.close();
                System.out.println("ResultSet uspešno zatvoren.");
            } catch (SQLException e) {
                System.err.println("Greška pri zatvaranju ResultSet-a:");
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                System.out.println("PreparedStatement uspešno zatvoren.");
            } catch (SQLException e) {
                System.err.println("Greška pri zatvaranju PreparedStatement-a:");
            }
        }
    }

    // Zatvaranje konekcije
    public static void closeConnection(Connection con) throws prodavnica_exception {
        if (con != null) {
            try {
                con.close();
                System.out.println("Konekcija uspešno zatvorena.");
            } catch (SQLException ex) {
                throw new prodavnica_exception("Neuspešno zatvaranje konekcije sa bazom.", ex);
            }
        }
    }

    // Rollback transakcije
    public static void rollbackTransactions(Connection con) throws prodavnica_exception {
        if (con != null) {
            try {
                con.rollback();
                System.out.println("Transakcije uspešno rollback-ovane.");
            } catch (SQLException ex) {
                throw new prodavnica_exception("Neuspešan rollback transakcije.", ex);
            }
        }
    }

    // Test konekcije
    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            System.out.println("Test konekcije uspešan!");
            System.out.println("Autocommit status: " + con.getAutoCommit());
        } catch (SQLException | RuntimeException e) {
            System.err.println("Neuspešno povezivanje sa bazom:");
        }
    }
}
