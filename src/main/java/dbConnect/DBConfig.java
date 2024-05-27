package dbConnect;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {
    private static Connection dbConnection;

    public Connection getConnection() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = DBConfig.class.getResourceAsStream("/application.properties");
            prop.load(input);
            String url = prop.getProperty("spring.datasource.url");
            String username = prop.getProperty("spring.datasource.username");
            String password = prop.getProperty("spring.datasource.password");
            System.out.println(url + " " + username + " " + password);
            if (dbConnection == null) {
                return DriverManager.getConnection(url, username, password);
            }
            return dbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
