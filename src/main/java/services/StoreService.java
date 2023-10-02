package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreService {
    // Database connection parameters
    private final String dbUrl = "jdbc:postgresql://localhost:54322/store-db";
    private final String dbUser = "postgres";
    private final String dbPassword = "secret";
    private final Logger logger = Logger.getLogger(StoreService.class.getName());
    // Method to fetch Store data by storeId from the other database
    public int getStoreById(int storeId) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT store_id FROM store WHERE store_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, storeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("store_id");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while fetching Store ID by storeId: " + e.getMessage(), e);
        }
        return -1;
    }
}
