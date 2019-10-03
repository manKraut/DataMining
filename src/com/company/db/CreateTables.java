package com.company.db;

import com.company.Pair;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CreateTables {

    private String SQLiteUrl = "jdbc:sqlite:Database.db";

    CreateTables() {
    }
    //CREATE / ALTER TABLE OPERATIONS

    // Method to create new tables
    public void createSingleColNewTable(String tableName, String columnName) {
        Pair<String, String> column = new Pair<>(columnName, "STRING");
        createNewTable(tableName, asList(new Pair<>("id", " INTEGER PRIMARY KEY autoincrement"), column));
    }

    public void dropTables(List<String> tableNames) {
        tableNames.forEach(this::dropTable);
    }

    private void dropTable(String tableName) {
        String sqlCreate = String.format("DROP TABLE IF EXISTS %s;",
                tableName);
        try (Connection conn = DriverManager.getConnection(SQLiteUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreate);
        } catch (SQLException e) {
            System.out.println("dropTable: " + tableName);
            System.out.println(e.getMessage());
        }
    }

    // Method to create new tables
    void createNewTable(String tableName, List<Pair<String, String>> columns) {

        String columnDefs = columns.stream()
                .map(x -> String.format("%s %s", x.getKey(), x.getValue()))
                .collect(Collectors.joining(",\n"));
        // SQLite statement for creating a new table
        String sqlCreate = String.format("CREATE TABLE IF NOT EXISTS %s (%s);",
                tableName, columnDefs);
        try (Connection conn = DriverManager.getConnection(SQLiteUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreate);
        } catch (SQLException e) {
            System.out.println("createNewTable: " + tableName);
            System.out.println(e.getMessage());
        }
    }

    //Method to insert values into table "DefaultUrls"
    public void insertUrls(String tableName, List<String> listOfURLs) {
        insertToTableColumn(tableName, listOfURLs, "URL");

    }

    public void insertToTableColumn(String tableName, List<String> values, String columnName) {
        PreparedStatement pstm;
        String stm = "INSERT INTO " + tableName + "(" + columnName + ") VALUES(?);";

        try {
            Connection conn = DriverManager.getConnection(SQLiteUrl);

            for (String value : values) {
                pstm = conn.prepareStatement(stm);
                pstm.setString(1, value);
                pstm.execute();
            }
        } catch (SQLException e) {
            System.out.println("insertToTableColumn");
            System.out.println(e.getMessage());
        }
    }

    public synchronized void insertToTable(String tableName, List<Pair<String, String>> values) {
        PreparedStatement pstm;
        String keys = values.stream()
                .map(Pair::getKey)
                .collect(Collectors.joining(","));
        String valuesString = values.stream()
                .map(Pair::getValue)
                .collect(Collectors.joining(","));
        String stm = String.format("INSERT INTO %s (%s) VALUES (%s);",
                tableName, keys, valuesString);
        try {
            Connection conn = DriverManager.getConnection(SQLiteUrl);
            pstm = conn.prepareStatement(stm);
            pstm.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    synchronized void insertDataMiningTableWithImpression(String tableName, String keyWord, int numOfPresence, String url, int positiveCount, int negativeCount) {

        insertToTable(tableName, asList(
                new Pair<>("url", String.format("\"%s\"", url)),
                new Pair<>("key_word", String.format("\"%s\"", keyWord)),
                new Pair<>("key_word_count", numOfPresence + ""),
                new Pair<>("positive_count", positiveCount + ""),
                new Pair<>("negative_count", negativeCount + "")

        ));
    }

    public synchronized void insertDataMiningTable(String tableName, String keyWord, int numOfPresence, String url) {

        insertToTable(tableName, asList(
                new Pair<>("url",String.format("\"%s\"", url)),
                new Pair<>("key_word", String.format("\"%s\"", keyWord)),
                new Pair<>("key_word_count", numOfPresence + "")
        ));
    }
}
