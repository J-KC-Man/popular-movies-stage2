package com.jman.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Justin on 22/05/2018.
 */

public class TrailerResults {

    /* the arraylist to map to the results json array that should have inside it movie objects */
    @SerializedName("results")
    private ArrayList<Trailer> listOfTrailers = new ArrayList<>();

    public ArrayList<Trailer> getListOfTrailers() {
        return listOfTrailers;
    }
}
