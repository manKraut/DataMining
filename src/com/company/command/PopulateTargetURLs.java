package com.company.command;

import com.company.QueryData;
import com.company.RetrieveEndingsOnline;
import com.company.command.Command;

import java.util.Scanner;

public class PopulateTargetURLs implements Command {

    private QueryData queryData;

    public PopulateTargetURLs(QueryData queryData) {
        this.queryData = queryData;
    }


    private void loadTargetSitesList() {
        System.out.println("Please stand by while system is preparing for execution...\n");
        //load endings from the web using Jsoup html parser
        RetrieveEndingsOnline retrieveEndings = new RetrieveEndingsOnline(queryData);
        retrieveEndings.extractEndingsWithJsoup();
        System.out.println("Following, you can provide your own websites, " +
                "by typing a valid domain name (url) into the command prompt.\nFirst choose" +
                " the number of websites you want to include to your search:");
        boolean rep = true;
        int num = 0;
        while (rep) {
            Scanner obj = new Scanner(System.in);
            String temp = obj.nextLine();
            if (temp.matches("\\d+")) {        //validates user's entry with respect to whether it's an integer or not
                num = Integer.parseInt(temp);
                rep = false;
            } else {
                System.out.println("Please provide a valid Option...");
            }
        }
        rep = true;
        while (rep) {

            System.out.println("Type preferred url then press ENTER::");

            //read domain entry from user
            Scanner link = new Scanner(System.in);
            String url = link.nextLine();
            System.out.println(url);
            if (isValid(url)) {
                //add "http://" protocol to given domain
                if (!url.contains("http://")) {
                    url = "http://" + url;
                }
                queryData.getUsersUrls().add(url);
                num--;
                if (num == 0) {
                    rep = false;
                }
            } else {
                System.out.println("Please provide a valid URL...");
            }
        }
    }

    private boolean isValid(String url) {
        System.out.println(queryData.getUrlsEndings());
        for (String validEnding : queryData.getUrlsEndings()) {
            System.out.println("contains "+validEnding+"?");
            if (url.toUpperCase().contains(validEnding)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        loadTargetSitesList();
        System.out.println("Now you are ready to initiate process and retrieve results from your given websites.\nPress 4");
    }
}
