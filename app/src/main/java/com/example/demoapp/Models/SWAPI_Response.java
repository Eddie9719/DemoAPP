package com.example.demoapp.Models;

import java.util.ArrayList;

public class SWAPI_Response {
    public ArrayList<People> getResults() {
        return results;
    }

    public void setResults(ArrayList<People> results) {
        this.results = results;
    }

    private ArrayList<People> results ;


}
