package dbConnect;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConfig {

    private static final Logger logger = Logger.getLogger(DBConfig.class.getName());
    private static Connection dbConnection;

    public static Connection getConnection() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);

            String url = prop.getProperty("spring.datasource.url");
            String username = prop.getProperty("spring.datasource.username");
            String password = prop.getProperty("spring.datasource.password");
            if (dbConnection == null) {
                return DriverManager.getConnection(url, username, password);
            }
            return dbConnection;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to initialize database connection.", e);
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
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
