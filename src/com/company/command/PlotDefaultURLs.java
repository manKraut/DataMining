package com.company.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PlotDefaultURLs implements Command {

    public void previewDefaultUrls(){

        String canonicalPath = null;
        try {
            canonicalPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(canonicalPath + "\\sites.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine()){
            System.out.println(sc.next());
        }
    }

    @Override
    public void execute() {
        previewDefaultUrls();
    }
}
