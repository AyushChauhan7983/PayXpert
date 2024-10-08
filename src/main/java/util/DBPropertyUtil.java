package util;
import java.util.*;
import java.io.*;
public class DBPropertyUtil {
    public static String getConnectionString(String propertyFileName) {
        try (FileInputStream input = new FileInputStream(propertyFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("db.connection.string");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately (e.g., log it)
            return null;
        }
    }

}
