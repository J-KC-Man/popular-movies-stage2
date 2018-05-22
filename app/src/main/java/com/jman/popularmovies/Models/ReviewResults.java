package com.jman.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Justin on 20/05/2018.
 */

public class ReviewResults {

    /* the arraylist to map to the results json array that should have inside it movie objects */
    @SerializedName("results")
    private ArrayList<Review> listOfReviews = new ArrayList<>();

    public ArrayList<Review> getListOfReviews() {
        return listOfReviews;
    }
}
