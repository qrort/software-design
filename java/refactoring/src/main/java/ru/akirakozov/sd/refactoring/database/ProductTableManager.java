package ru.akirakozov.sd.refactoring.database;


import ru.akirakozov.sd.refactoring.Product;

import java.sql.*;
import java.util.function.Consumer;

public class ProductTableManager {
    private final Connection databaseConnection;
    private final String tableName;

    public ProductTableManager(String tableName) throws SQLException {
        this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:test.db");
        this.tableName = tableName;
        createProductTableIfNotExists();
    }

    public void createProductTableIfNotExists() {
        executeUpdateStatement(
                "CREATE TABLE IF NOT EXISTS " + tableName +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)");
    }

    public void dropProductTable() {
        executeUpdateStatement("DROP TABLE " + tableName);
    }

    public void addProduct(Product product) {
        String sqlQuery = "INSERT INTO " + tableName + " (NAME, PRICE) " +
                "VALUES ('" + product.getName() + "'," + product.getPrice() + ")";
        executeUpdateStatement(sqlQuery);
    }

    public String getTableName() {
        return tableName;
    }

    public void executeQueryStatement(String sqlQuery, Consumer<ResultSet> consumer) {
        try {
            Statement queryStatement = databaseConnection.createStatement();
            ResultSet rs = queryStatement.executeQuery(sqlQuery);
            consumer.accept(rs);
            rs.close();
            queryStatement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdateStatement(String sqlQuery) {
        try {
            Statement updateStatement = databaseConnection.createStatement();
            updateStatement.executeUpdate(sqlQuery);
            updateStatement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}