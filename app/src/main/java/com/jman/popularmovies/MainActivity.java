package com.jman.popularmovies;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jman.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMoviesTask().execute();
        // loadMoviePosterData()
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            URL moviesDataRequestUrl = NetworkUtils.buildUrl();

            try {
                String moviesJSONResponse = NetworkUtils.getResponseFromHttpUrl(moviesDataRequestUrl);

                return moviesJSONResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } //end of method
    }
}
