package com.company.command;

import com.company.QueryData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RetrieveUrlsFromFile {
    private QueryData queryData;

    public RetrieveUrlsFromFile(QueryData queryData) {

        this.queryData = queryData;
    }

    public void readLinesFromPath(String pathName){

        //open file "sites.txt" into directory and store retrieved data
        File file = new File(pathName);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine()){
            queryData.getUrls().add(sc.nextLine());
        }
    }
}
