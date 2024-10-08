package util;
import java.sql.*;
public class DBConnUtil {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Update with your database driver
            String url = "jdbc:mysql://localhost:3306/payxpert";
            String username = "root";
            String password = "ayush7983";

            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found.", e);
        }
    }


}
