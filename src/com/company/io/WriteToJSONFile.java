package com.company.io;

import com.company.QueryData;
import com.company.command.Command;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WriteToJSONFile implements Command {
    private QueryData queryData;
    private String fileName;
    private Gson json = new Gson();

    public WriteToJSONFile(QueryData queryData, String fileName) {

        this.queryData = queryData;
        this.fileName = fileName;
    }

    public void toJsonFile() throws IOException, NoMiningResultsException {
        checkForEmptyResults();
        FileWriter fw = new FileWriter(fileName);
        System.out.println("Writing mining results to file: '"+fileName+"'");
        json.toJson(queryData.getMiningResults(), HashMap.class, fw);
    }

    private void checkForEmptyResults() throws NoMiningResultsException {
        if (queryData.getMiningResults().isEmpty()) throw new NoMiningResultsException("It seems like there are no results to export.");
    }

    @Override
    public void execute() {
        try {
            toJsonFile();
            //System.out.println(toJson());
        } catch (IOException e) {
            System.out.println("There was a problem writing the file.");
            //e.printStackTrace();
        } catch (NoMiningResultsException nre){
            System.out.println(nre.getMessage());
        }
    }

    private class NoMiningResultsException extends Exception{

        public NoMiningResultsException(String message) {
            super(message);
        }
    }
}
