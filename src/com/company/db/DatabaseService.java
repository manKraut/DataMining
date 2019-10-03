package com.company.db;

import com.company.Pair;
import com.company.QueryData;
import com.company.command.RetrieveUrlsFromFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class DatabaseService {

    private static CreateTables tables = new CreateTables();

    public static void initializeDatabase(QueryData queryData) {

        //create Database "database"
        DataΒaseCreate db = new DataΒaseCreate();
        db.createNewDatabase("Database");
        RetrieveUrlsFromFile retrieveUrls = new RetrieveUrlsFromFile(queryData);
        //create table "defaultUrls" where default websites are to be load on from file "sites.txt" in directory
        String canonicalPath = null;
        try {
            canonicalPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        retrieveUrls.readLinesFromPath(canonicalPath + "\\sites.txt");
        tables.dropTables(Arrays.asList("default_url", "user_url", "mining_result", "key_word"));
        tables.createSingleColNewTable("default_url", "URL");
        tables.createSingleColNewTable("key_word", "word");
        tables.insertUrls("default_url", queryData.getUrls());
        tables.createNewTable("mining_result", Arrays.asList(
                new Pair<>("date_time", "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP"),
                new Pair<>("url", "TEXT"),
                new Pair<>("key_word", "TEXT"),
                new Pair<>("key_word_count", "INTEGER"),
                new Pair<>("positive_count", "INTEGER"),
                new Pair<>("negative_count", "INTEGER")
        ));
    }

    public void insertUrls(List<String> usersUrls) {
        tables.insertToTableColumn("default_url", usersUrls, "url");
    }

    public void insertDataMiningTableWithImpression(String word, Integer totalHitsOnSiteCounter, String url, Integer posImpressionHitsCounter, Integer negImpressionHitsCounter) {
        tables.insertDataMiningTableWithImpression("mining_result", word, totalHitsOnSiteCounter, url, posImpressionHitsCounter, negImpressionHitsCounter);
    }

    public void insertDataMiningTable(String word, Integer totalHitsOnSiteCounter, String url) {
        tables.insertDataMiningTable("mining_result", word, totalHitsOnSiteCounter, url);

    }

    public void insertKeyWords(List<String> keyWords) {
        tables.insertToTableColumn("key_word", keyWords, "word");
    }
}
