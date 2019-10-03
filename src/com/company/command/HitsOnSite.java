package com.company.command;

import com.company.QueryData;
import com.company.analysis.JsoupParserOnURLs;
import com.company.db.DatabaseService;

import java.util.Deque;

public class HitsOnSite extends Thread implements Command {

    private QueryData queryData;
    private Deque<Thread> threads;
    private DatabaseService dbService;

    public HitsOnSite(QueryData queryData, Deque<Thread> threads, DatabaseService dbService) {
        this.queryData = queryData;
        this.threads = threads;
        this.dbService = dbService;
    }

    public void run() {

        if (queryData.getKeyWords().isEmpty()) {
            System.out.println("Please, consider providing key words first... Try option 1");
            return;
        }

        //create a 2D Table that holds default URLs and key words, in which Threads will write
        // their results from the Web search.

        if (queryData.getUsersUrls().isEmpty()) {
            for (String url : queryData.getUrls()) {
                Thread t = new Thread(new JsoupParserOnURLs(url, queryData.getKeyWords(), dbService));
                t.start();
                threads.add(t);
            }

        } else {
            // Pass elements of URLs' list into table
            dbService.insertUrls(queryData.getUsersUrls());

            for (String url : queryData.getUsersUrls()) {
                Thread t = new Thread(new JsoupParserOnURLs(url, queryData.getKeyWords(), dbService));
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
