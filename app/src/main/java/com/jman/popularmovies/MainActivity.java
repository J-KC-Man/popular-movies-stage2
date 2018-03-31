package com.jman.popularmovies;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jman.popularmovies.utilities.JsonUtils;
import com.jman.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import static com.jman.popularmovies.utilities.JsonUtils.parseSandwichJson;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMoviesTask().execute();
        // loadMoviePosterData()
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        String moviesJSONResponse;
        ArrayList<Movie> arrayListOfMovies;

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {

            URL moviesDataRequestUrl = NetworkUtils.buildUrl();

            try {
                moviesJSONResponse = NetworkUtils.getResponseFromHttpUrl(moviesDataRequestUrl);

                arrayListOfMovies = JsonUtils.parseSandwichJson(moviesJSONResponse);
                return arrayListOfMovies;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }




        } //end of method
    }
}
