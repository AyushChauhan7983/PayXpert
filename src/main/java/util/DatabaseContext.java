package util;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseContext {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/payxpert";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ayush7983";
    private static final Logger logger = Logger.getLogger(DatabaseContext.class.getName());

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Error loading JDBC driver", e);

        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

}
