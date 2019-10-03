package com.company.io;

public class Menu {
    public static void menu () {

        System.out.println(
                "\n\nIn this application you can provide any number of key words of your preference,\n" +
                        "in order to extract specific data from the Web. There is a default list of\n" +
                        "URLs, coming with this application, which is the source of any search you would like\n" +
                        "to do. Instead, you can provide your own list of websites, by selecting the appropriate\n" +
                        "selection from the main menu. Once you have provided the application with key words,\n" +
                        "you can see how, each of it, is trending right now on the Web and then, by providing further\n" +
                        "impression words, you can extract the impression that exists, for each key word. You will have the\n" +
                        "ability to watch dynamically, the data extraction and the results will be stored into a database,\n" +
                        "which you can then extract as a .json file, for any further manipulation. Following, you can find the Menu options.\n" +
                        "Please enjoy!\n\n\n"+
                "1. Insert key words\n" +
                "2. Provide your own target websites for data extraction\n" +
                "3. Provide specific words that might form any particular impression from the web\n" +
                "4. Find out how much your words are trending right now on the Web\n" +
                "5. Perform impression mining with given impression words list\n" +
                "6. Have a look at the default target websites\n" +
                "7. Extract results of impression mining to json file\n" +
                "8. Pause application\n" +
                "9. Resume application\n" +
                "10.Exit application\n"
        );
    }

}
