package dbConnect;

import java.sql.Connection;
public class TestingConnection {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DBConfig.getConnection();
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
