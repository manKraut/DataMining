package com.company.analysis;

import com.company.db.CreateTables;
import com.company.db.DatabaseService;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupParserOnURLs implements Runnable {

    private String url;
    private List<String> words;
    private DatabaseService dbService;
    public Integer totalHitsOnSiteCounter = 0; //total number of hits on each website

    public JsoupParserOnURLs(String url, List<String> words, DatabaseService dbService) {
        this.url = url;
        this.words = words;
        this.dbService = dbService;
    }

    public void run() {
        synchronized (JsoupParserOnURLs.class) {
            try {
                String html = Jsoup.connect(url).get().html();
                for (String word : words) {
                    Pattern p = Pattern.compile(word);
                    Matcher m = p.matcher(html);
                    while (m.find()) {
                        totalHitsOnSiteCounter++;
                    }
                    System.out.println("Occurrences found with the word: " + word + " into website: " + url + " are ::" + totalHitsOnSiteCounter);

                    //insert data into Mining_Results table
                    dbService.insertDataMiningTable(word, totalHitsOnSiteCounter, url);
                    totalHitsOnSiteCounter = 0;
                }
            } catch (IOException e) {
                System.out.println("Can't connect to " + url + ". Please check validity or functionality of url");
                //e.printStackTrace();
            }
        }
    }
}
