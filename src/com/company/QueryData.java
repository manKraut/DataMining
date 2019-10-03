package com.company;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QueryData {

    private List<String>  keyWords         = new ArrayList<>();
    private List<String>  positiveImpress  = new ArrayList<>();
    private List<String>  negativeImpress  = new ArrayList<>();
    private List<String>  urls             = new ArrayList<>();
    private List<String>  urlsEndings      = new ArrayList<>();
    private List<String>  usersUrls        = new ArrayList<>();
    private Map<String, Map<String, Integer>> MiningResults = new ConcurrentHashMap<>();

    public List<String>          getKeyWords() { return keyWords;         }

    public List<String>   getPositiveImpress() { return positiveImpress;  }

    public List<String>   getNegativeImpress() { return negativeImpress;  }

    public List<String>              getUrls() { return urls;             }

    public List<String>       getUrlsEndings() { return urlsEndings;      }

    public List<String>         getUsersUrls() { return usersUrls;        }


    public Map<String, Map<String, Integer>> getMiningResults() {
        return MiningResults;
    }
}
