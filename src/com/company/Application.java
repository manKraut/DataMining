package com.company;
import com.company.command.LoadImpressionWordsLists;
import com.company.db.DatabaseService;
import com.company.io.Menu;
import com.company.io.WriteToJSONFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application extends AppController {


    private static final int POPULATE_KEYWORDS = 1;
    private static final int POPULATE_TARGET_URLS = 2;
    private static final int LOAD_IMPRESSION_WORDS = 3;
    private static final int HITS_ON_SITE = 4;
    private static final int IMPRESSION_RESULTS = 5;
    private static final int PLOT_DEF_URLS = 6;
    private static final int WRITE_TO_JSON_FILE = 7;
    private static final int PAUSE_THREADS = 8;
    private static final int RESUME_THREADS = 9;
    private static final int EXIT_APP = 10;
    private static Map<Integer, com.company.command.Command> commandLookup;

    public static void main(String [] args) {

        //Initiate Application
        Application application = new Application();
        application.init();
        application.run();
        Menu.menu();

        Scanner in = new Scanner(System.in);

        while(application.running) {

            String temp;
            System.out.println("Please provide an option from the main menu: ");
            temp = in.nextLine();
            application.removeTerminatedThreads();
            if (temp.matches("\\d+")){
                Integer choice = Integer.parseInt(temp);
                commandLookup.getOrDefault(choice, () ->
                    System.out.println("Please provide a valid Option...")
                ).execute();
            } else {
                System.out.println("Please provide a valid Option...");
            }
        }
    }

    private void init() {
        QueryData queryData = new QueryData();
        DatabaseService.initializeDatabase(queryData);
        commandLookup = new HashMap<>();
        DatabaseService dbService = new DatabaseService();
        commandLookup.put(POPULATE_KEYWORDS, new com.company.command.PopulateKeyWords(queryData, dbService));
        commandLookup.put(POPULATE_TARGET_URLS, new com.company.command.PopulateTargetURLs(queryData));
        commandLookup.put(LOAD_IMPRESSION_WORDS, new LoadImpressionWordsLists(queryData));
        commandLookup.put(HITS_ON_SITE, new com.company.command.HitsOnSite(queryData, threads, dbService));
        commandLookup.put(IMPRESSION_RESULTS, new com.company.command.ImpressionResults(queryData, dbService, threads));
        commandLookup.put(PLOT_DEF_URLS, new com.company.command.PlotDefaultURLs());
        commandLookup.put(WRITE_TO_JSON_FILE, new WriteToJSONFile(queryData, "MiningResults.json"));
        commandLookup.put(PAUSE_THREADS, new com.company.command.PauseThreads(threads));
        commandLookup.put(RESUME_THREADS, new com.company.command.ResumeThreads(threads));
        commandLookup.put(EXIT_APP, new com.company.command.ExitApp(this));
    }

}
