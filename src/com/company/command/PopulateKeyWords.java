package com.company.command;
import com.company.QueryData;
import com.company.db.DatabaseService;

import java.util.Scanner;

public class PopulateKeyWords implements Command {
    private QueryData queryData;
    private DatabaseService dbService;

    public PopulateKeyWords(QueryData queryData, DatabaseService dbService) {

        this.queryData = queryData;
        this.dbService = dbService;
    }

    private void testScanner() {

        System.out.println("In this option you can provide" +
                " key words in order to mine several data from the" +
                " web. First, please give the number of words that" +
                " you would like to enter : ");

        boolean rep = true;
        while (rep) {
            try {
                Scanner obj1 = new Scanner(System.in);
                int numberOfWords;
                numberOfWords = obj1.nextInt();
                // call function to create list with the provided keywords
                loadKeyWordsList(numberOfWords);

                // Pass elements of URLs' list into table
                dbService.insertKeyWords(queryData.getKeyWords());

                rep = false;
            } catch (Exception e) {
                System.out.println("It seems something went wrong, please try again !");
            }
        }
    }

    private void loadKeyWordsList(int number) {

        for (int i = 0; i < number; i++) {
            System.out.println("Type a keyword then press ENTER");
            Scanner input = new Scanner(System.in);
            String keyWord;
            keyWord = input.nextLine();
            keyWord = keyWord.replaceAll("\\p{Punct}", ""); //remove punctuation from string
            if (keyWord.matches("-?\\d+(\\.\\d+)?")) {                 //check if string is a number
                System.out.println("Something went wrong... Please try again !");
                i = i - 1;
            } else {
                queryData.getKeyWords().add(keyWord);
            }
        }
    }

    @Override
    public void execute() {
        testScanner();
    }

}
