package com.jman.popularmovies.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Justin on 30/03/2018.
 */

public class JsonUtils {


    public static void parseSandwichJson(String moviesJSON) {

        try {

            /**
             * This master json object has all the movie data in 20 JSON objects,
             * we need to extract all of them an put them into an arraylist
             * so they can be manipulated and displayed on the GridView home page
             * and DetailsActivity*/
            // root json object: instantiate a parser that helps to get the values inside JSON object
            JSONObject moviesRootObj = new JSONObject(moviesJSON);

            // All the data is sitting inside a JSON Array, we'll use this to extract the movie details
            final String RESULTS = "results";
            JSONArray movieDataArray = moviesRootObj.getJSONArray(RESULTS);

            final String POSTER_PATH = "poster_path";



        } catch(JSONException e) {
            e.printStackTrace();
            Log.e("JsonUtils", "The parsing didn't work");
        }
    }
}
