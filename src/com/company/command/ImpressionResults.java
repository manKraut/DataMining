package com.company.command;

import com.company.db.DatabaseService;
import com.company.analysis.JsoupImpressionParserOnURLs;
import com.company.QueryData;

import java.util.Deque;

public class ImpressionResults extends Thread implements Command {
    private QueryData queryData;
    private DatabaseService dbService;
    private Deque<Thread> threads;

    public ImpressionResults(QueryData queryData, DatabaseService dbService, Deque<Thread> threads) {

        this.queryData = queryData;
        this.dbService = dbService;
        this.threads = threads;
    }

    public void run() {

        if (queryData.getKeyWords().isEmpty() || (queryData.getPositiveImpress().isEmpty() && queryData.getNegativeImpress().isEmpty())) {
            System.out.println("Please, first provide list of key words AND specific words for any particular impression to mine, try option 1 or 3.");
            return;
        }

        if (queryData.getUsersUrls().isEmpty()) {

            for (String url : queryData.getUrls()) {
                Thread t = new Thread(new JsoupImpressionParserOnURLs(url, queryData, dbService));
                t.start();
                threads.add(t);
            }


        } else {
            // Pass elements of URLs' list into table
            dbService.insertUrls(queryData.getUsersUrls());
            for (String url : queryData.getUsersUrls()) {
                Thread t = new Thread(new JsoupImpressionParserOnURLs(url, queryData, dbService));
                t.start();
                threads.add(t);
            }
        }

    }

    @Override
    public void execute() {
        System.out.println("Keywords provided for mining are :: " + queryData.getKeyWords());
        run();
    }
}
