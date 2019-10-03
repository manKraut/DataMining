package com.company.command;

import com.company.QueryData;
import com.company.db.DatabaseService;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HitsByUsersURLs implements Runnable {

    private String url;
    private List<String> words;
    private QueryData queryData;
    private DatabaseService dbService;
    public Integer totalHitsOnSiteCounter = 0; //total number of hits on each website

    public HitsByUsersURLs(String url, List<String> words, QueryData queryData, DatabaseService dbService){
        this.url = url;
        this.words = words;
        this.queryData = queryData;
        this.dbService = dbService;
    }

    public void run() {

        String html = null;
        try {
            html = Jsoup.connect(url).get().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String word : words) {
            Pattern p = Pattern.compile(word);
            assert html != null;
            Matcher m = p.matcher(html);
            while (m.find()) {
                totalHitsOnSiteCounter++;
            }

            System.out.println("Occurrences found with the word: " + word + " into website: " + url + " are ::" + totalHitsOnSiteCounter);

            //insert data into Mining_Results table
            dbService.insertDataMiningTable(word, totalHitsOnSiteCounter, url);
        }
    }
}
