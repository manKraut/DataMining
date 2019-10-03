package com.company.analysis;

import com.company.QueryData;
import com.company.db.DatabaseService;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupImpressionParserOnURLs implements Runnable {

    private String url;
    private QueryData queryData;
    private DatabaseService dbService;
    private Integer totalHitsOnSiteCounter = 0; //total number of hits on each website

    public JsoupImpressionParserOnURLs(String url, QueryData queryData, DatabaseService dbService) {
        this.url = url;
        this.queryData = queryData;
        this.dbService = dbService;
    }

    @Override
    public void run() {
        synchronized (JsoupImpressionParserOnURLs.class) {
            try {
                String html = Jsoup.connect(url).get().html();
                for (String word : queryData.getKeyWords()) {
                    Pattern p = Pattern.compile(word);
                    Matcher m = p.matcher(html);
                    while (m.find()) {
                        totalHitsOnSiteCounter++;
                    }
                    Map<String, Integer> resultMapForUrl = new HashMap<>();
                    if (totalHitsOnSiteCounter > 0) {
                        Integer posImpressionHitsCounter = findImpresCount(html, queryData.getPositiveImpress(), word);
                        //insert number of hits of positive word found into site
                        resultMapForUrl.put("Positive", posImpressionHitsCounter);

                        Integer negImpressionHitsCounter = findImpresCount(html, queryData.getNegativeImpress(), word);

                        resultMapForUrl.put("Negative", negImpressionHitsCounter);

                        queryData.getMiningResults().put(url, resultMapForUrl);
                        dbService.insertDataMiningTableWithImpression(
                                word, totalHitsOnSiteCounter, url,
                                posImpressionHitsCounter, negImpressionHitsCounter);

                        System.out.println("Total hits of negative impression words on site " + url + "are :: " + negImpressionHitsCounter);
                        System.out.println("Total hits of positive impression words on site " + url + "are :: " + posImpressionHitsCounter);
                    }
                    totalHitsOnSiteCounter = 0;
                }
            } catch (IOException e) {
                System.out.println("Can't connect to " + url + ". Please check validity or functionality of url");
               //e.printStackTrace();
            }
        }
    }

    private int findImpresCount(String html, List<String> impressionWords, String word) {
        int impressCount = 0;
        int impressCountForEachWord = 0;
        for (String imprWord : impressionWords) {
            Pattern imprp = Pattern.compile(imprWord);
            Matcher imprm = imprp.matcher(html);
            while (imprm.find()) {
                impressCountForEachWord++;
                impressCount++;
            }
            System.out.println("Occurrences found with the impression word: " + imprWord + " for the key word: " + word + " into website: " + url + " are ::" + impressCountForEachWord);
            impressCountForEachWord = 0;
        }
        return impressCount;
    }
}
