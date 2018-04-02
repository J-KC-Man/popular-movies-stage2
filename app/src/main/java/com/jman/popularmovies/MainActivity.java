package com.jman.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jman.popularmovies.utilities.JsonUtils;
import com.jman.popularmovies.utilities.NetworkUtils;



import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

   private GridView gridView;
   private MoviesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.movie_posters_gridView);

        // init adapter and attach to gridView
        adapter = new MoviesAdapter(this, JsonUtils.getMoviesArrayList());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make sure to get the details of the right movie
                Movie movie = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, Activity_detail.class);
                Movie movieDetail = new Movie(
                        movie.getPoster_path(),
                        movie.getOverview(),
                        movie.getRelease_date(),
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPopularity(),
                        movie.getVote_average());

                // pass movie object with movie info to the Activity_detail intent
                intent.putExtra("movieDetails", movieDetail);
                startActivity(intent);
            }
        });

        // load movie data to UI
        loadMovieData();

    }

    public void loadMovieData() {
        new FetchMoviesTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true; // give the go ahead to inflate it on the layout
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        String sortBy;
        // default sort is by popularity

        if (menuItemThatWasSelected == R.id.refresh) {
            adapter.clear();
            loadMovieData();
        }

        if (menuItemThatWasSelected == R.id.sort_top_rated) {
            sortBy = "vote_count.desc";

            if(!sortBy.equals(NetworkUtils.SORT_BY_OPTION)) {
                NetworkUtils.SORT_BY_OPTION = "vote_count.desc";
                adapter.clear();
                loadMovieData();
            } else {
                // else if its equal, do nothing
            }
        }

        if (menuItemThatWasSelected == R.id.sort_popularity) {
            sortBy = "popularity.desc";

            if(!sortBy.equals(NetworkUtils.SORT_BY_OPTION)) {
                NetworkUtils.SORT_BY_OPTION = "popularity.desc";
                adapter.clear();
                loadMovieData();
            } else {
                // else if its equal, do nothing
            }
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * The following method checks for network connection
    * The code in this method is from Stack Overflow
     *
     * source: https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     *
     * author: gar
     * profile: https://stackoverflow.com/users/485695/gar
    *
    * */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        String moviesJSONResponse;
        ArrayList<Movie> arrayListOfMovies;

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {

            URL moviesDataRequestUrl = null;


            // check for network connection
            if(isOnline()) {
                try {
                    // create url to query API
                    moviesDataRequestUrl = NetworkUtils.buildUrl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                // receive JSON response
                moviesJSONResponse = NetworkUtils.getResponseFromHttpUrl(moviesDataRequestUrl);

                // parse JSON into a movie object and
                // place in array list of movie objects
                arrayListOfMovies = JsonUtils.parseMovieJson(moviesJSONResponse);
                return arrayListOfMovies;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } //end of method

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {

            // tell the adapter to update the GridView UI with movie posters
            adapter.updateUI(movies);
        }
    }
}
