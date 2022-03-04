package com.example.demoapp.Models;

import java.util.ArrayList;

public class SWAPI_Film_Response {
    private ArrayList<Film> results;

    public ArrayList<Film> getResults() {
        return results;
    }
    public void setFilms_results(ArrayList<Film> films_results) {
        this.results = results;
    }
}
