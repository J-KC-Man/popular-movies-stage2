package com.jman.popularmovies.utilities;

import android.util.Log;

import com.jman.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Justin on 30/03/2018.
 */

public class JsonUtils {

    private static ArrayList<Movie> moviesArrayList = new ArrayList<>();


    public static ArrayList<Movie> parseSandwichJson(String moviesJSON) {

        try {

            /**
             * This master json object has all the Movie data in 20 JSON objects,
             * we need to extract all of them an put them into an arraylist
             * so they can be manipulated and displayed on the GridView home page
             * and DetailsActivity*/
            // root json object: instantiate a parser that helps to get the values inside JSON object
            JSONObject moviesRootObj = new JSONObject(moviesJSON);

            // All the data is sitting inside a JSON Array, we'll use this to extract the Movie details
            final String RESULTS = "results";
            JSONArray movieDataArray = moviesRootObj.getJSONArray(RESULTS);

            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String RELEASE_DATE = "release_date";
            final String ID = "id";
            final String TITLE = "title";
            final String POPULARITY = "popularity";
            final String VOTE_AVERAGE = "vote_average";


            for (int i = 0; i < movieDataArray.length(); i++) {

                // get the json object inside the master json object to start extracting details
                JSONObject movieObject = movieDataArray.getJSONObject(i);
                String movie_poster = "http://image.tmdb.org/t/p/w185/" + movieObject.optString(POSTER_PATH);
                String overView = movieObject.optString(OVERVIEW);

                String releaseDate = movieObject.optString(RELEASE_DATE);
                String id = movieObject.optString(ID);
                String movieTitle = movieObject.optString(TITLE);

                String popularity = movieObject.optString(POPULARITY);
                String voteAverage = movieObject.optString(VOTE_AVERAGE);

                moviesArrayList.add(new Movie(movie_poster,overView, releaseDate, id, movieTitle, popularity, voteAverage));
            }

            return moviesArrayList;

        } catch(JSONException e) {
            e.printStackTrace();
            Log.e("JsonUtils", "The parsing didn't work");
        }

        return null;
    }
}
