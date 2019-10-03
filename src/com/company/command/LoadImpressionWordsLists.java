package com.company.command;

import com.company.QueryData;

import java.util.List;
import java.util.Scanner;

public class LoadImpressionWordsLists implements Command {

    private QueryData queryData;

    public LoadImpressionWordsLists(QueryData queryData) {

        this.queryData = queryData;
    }

    private void inputImpressionWords(){

        System.out.println("Here, you can provide any specific" +
                " word(s) that might form either a negative or \n" +
                "positive impression, of every keyword that you \n" +
                "want to search the web for. First, please refer \n" +
                "to what list of words would you like to include to your main search? :\n" +
                "Press P for positive or N for negative : ");

        boolean rep = true;
        String npOption = null; //variable to hold negative or positive option
        int numberOfWords = 0;
        while (rep) {
            try {
                Scanner obj1 = new Scanner(System.in);
                npOption = obj1.nextLine();
                if(!(npOption.equals("P") || npOption.equals("p") || npOption.equals("N") || npOption.equals("n"))){
                    System.out.println("It seems something went wrong, please try again");
                }else {
                    rep = false;
                }
            } catch (Exception e) {
                System.out.println("It seems something went wrong, please try again");
            }
        }

        System.out.println("Now, please provide the number of words that you want to include along with your main search : ");
        rep = true;
        while(rep) {
            try {
                Scanner obj2 = new Scanner(System.in);
                numberOfWords = obj2.nextInt();
                rep = false;
            } catch (Exception e) {
                System.out.println("It seems something went wrong, please try again");
            }
        }

        // call appropriate function to create list with the provided words
        if(npOption.equals("P") || npOption.equals("p")) {
            loadWordsList(numberOfWords, queryData.getPositiveImpress());
            System.out.println("Would you like to provide negative impression words now? If yes press Y otherwise press N :");
            boolean test = true;
            String ynoption = null; //variable to hold yes or no option
            while(test){
                try {
                    Scanner obj = new Scanner(System.in);
                    ynoption = obj.nextLine();
                    if (!(ynoption.equals("N") || ynoption.equals("n") || ynoption.equals("y") || ynoption.equals("Y"))) {
                        System.out.println("It seems something went wrong, please try again");
                    }else {
                        test = false;
                    }
                }catch(Exception e){
                    System.out.println("It seems something went wrong, please try again");
                }
            }
            if (ynoption.equals("Y") || ynoption.equals("y")){
                System.out.println("Now, please provide the number of words that you want to include along with your main search : ");
                boolean ynRep = true;
                while(ynRep) {
                    try {
                        Scanner obj3 = new Scanner(System.in);
                        int number;
                        number = obj3.nextInt();
                        loadWordsList(number, queryData.getPositiveImpress());
                        ynRep = false;
                    } catch (Exception e) {
                        System.out.println("It seems something went wrong, please try again");
                    }
                }
            }
        }else{
            loadWordsList(numberOfWords, queryData.getNegativeImpress());
            System.out.println("Would you like to provide positive impression words now? If yes press Y otherwise press N :");
            boolean test = true;
            String ynoption = null; //variable to hold yes or no option
            while(test){
                try {
                    Scanner obj = new Scanner(System.in);
                    ynoption = obj.nextLine();
                    if (!(ynoption.equals("N") || ynoption.equals("n") || ynoption.equals("y") || ynoption.equals("Y"))) {
                        System.out.println("It seems something went wrong, please try again");
                    }else {
                        test = false;
                    }
                }catch(Exception e){
                    System.out.println("It seems something went wrong, please try again");
                }
            }
            if (ynoption.equals("Y") || ynoption.equals("y")){
                System.out.println("Now, please provide the number of words that you want to include along with your main search : ");
                rep = true;
                while(rep) {
                    try {
                        Scanner obj3 = new Scanner(System.in);
                        int number;
                        number = obj3.nextInt();
                        loadWordsList(number, queryData.getPositiveImpress());
                        rep = false;
                    } catch (Exception e) {
                        System.out.println("It seems something went wrong, please try again");
                    }
                }
            }
        }
    }

    private void loadWordsList(int number, List<String> wordList){

        for (int i=0; i<number; i++) {
            System.out.println("Type a keyword then press ENTER");
            Scanner input = new Scanner(System.in);
            String keyWord;
            keyWord = input.nextLine();
            keyWord = keyWord.replaceAll("\\p{Punct}", ""); //remove punctuation from string
            if (keyWord.matches("-?\\d+(\\.\\d+)?")) {                 //check if string is a number
                System.out.println("Sorry, it would be preferable for a word to be analyzed. Please, try again!");
                i = i - 1;
            }else {
                wordList.add(keyWord);
            }
        }
    }


    @Override
    public void execute() {
        inputImpressionWords();
    }
}
