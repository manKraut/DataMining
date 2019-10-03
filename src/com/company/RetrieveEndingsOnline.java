package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RetrieveEndingsOnline {

    private com.company.QueryData queryData;

    public RetrieveEndingsOnline(com.company.QueryData queryData) {

        this.queryData = queryData;
    }

    public void extractEndingsWithJsoup() {

        //This site contains 100 valid domain suffixes
        String url = "https://iwantmyname.com/domains";
        try {
            //Retrieve page in html using Jsoup library
            String html = Jsoup.connect(url).get().html();
            //Parse html file through and select specific blocks which contain desired info
            Document doc = Jsoup.parse(html);
            Elements alpha = doc.select("li > span.tld");
            //convert retrieved Elements into String and split it accordingly
            //resulting to a String List which is stored
            String str = alpha.text();
            str = str.replaceAll("\\s" , "");
            String [] endList = str.split("•");
            for (String i : endList){
                if (!i.isEmpty()) {
                    queryData.getUrlsEndings().add(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

