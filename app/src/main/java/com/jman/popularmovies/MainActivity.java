package com.jman.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jman.popularmovies.utilities.JsonUtils;
import com.jman.popularmovies.utilities.MoviesApiService;
import com.jman.popularmovies.utilities.MoviesApiServiceGenerator;
import com.jman.popularmovies.utilities.MyMovieDatabase;
import com.jman.popularmovies.utilities.NetworkUtils;



import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

   private GridView gridView;
   private MoviesAdapter adapter = null;

   private MoviesApiService moviesApiClientService;

   // the list of movie json objects represented as Java Movie objects
   private List<MovieResults.Movie> movies;

    // default sort option
    public static String SORT_BY_OPTION = "popular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.movie_posters_gridView);


        // load movie data to UI
        try {
            loadMovieData(SORT_BY_OPTION);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make sure to get the details of the right movie
                MovieResults.Movie movie = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, Activity_detail.class);
                MovieResults.Movie movieDetail = new MovieResults.Movie(
                        movie.getPosterPath(),
                        movie.getOverview(),
                        movie.getReleaseDate(),
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPopularity(),
                        movie.getVoteAverage());

                // pass movie object with movie info to the Activity_detail intent
                intent.putExtra("movieDetails", movieDetail);
                startActivity(intent);
            }
        });

    }


    public void loadMovieData(String sortOrder) throws TimeoutException {
//        new FetchMoviesTask().execute();
        Call<MovieResults> networkRequest;
        try {

            // instantiate/create implementation of the MoviesApiService interface
            moviesApiClientService = MoviesApiServiceGenerator.createService();

            // check if device has network connection
            if(!isOnline()) {
                Toast.makeText(
                        MainActivity.this,
                        "No internet connection",
                        Toast.LENGTH_LONG).show();
                throw new TimeoutException("Connect timeout: no network connection");
            } else {
                // make the network request to connect to the server
                networkRequest = moviesApiClientService.getMoviesJson(sortOrder, MyMovieDatabase.API_KEY);

                networkRequest.enqueue(new Callback<MovieResults>() {
                    @Override
                    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                        if(response.isSuccessful()) {

                            // assign a new list of movie objects mapped from json objects to the parsed response
                            // so we can work with it
                            movies = response.body().getListOfMovies();

                            Log.d(TAG, "Number of Movies Received: " + movies.size());

                            // if adapter is null, instantiate and set it to gridView
                            if(adapter == null) {
                                // init adapter and attach to gridView
                                adapter = new MoviesAdapter(MainActivity.this, movies);
                                gridView.setAdapter(adapter);

                            } else {
                                // else update the UI
                                adapter.updateUI(movies);

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<MovieResults> call, Throwable t) {
                        Toast.makeText(
                                MainActivity.this,
                                "network request failed",
                                Toast.LENGTH_LONG).show();
                        t.getStackTrace();
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


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
            try {
                loadMovieData(SORT_BY_OPTION);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

        // show top ratedmovies
        if (menuItemThatWasSelected == R.id.sort_top_rated) {
            sortBy = "top_rated";

            if(!sortBy.equals(SORT_BY_OPTION)) {
                SORT_BY_OPTION = "top_rated";
                adapter.clear();
                try {
                    loadMovieData(SORT_BY_OPTION);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            } else {
                // else if its equal, do nothing
            }
        }

        // show movies by popularity
        if (menuItemThatWasSelected == R.id.sort_popularity) {
            sortBy = "popular";

            if(!sortBy.equals(SORT_BY_OPTION)) {
                SORT_BY_OPTION = "popular";
                adapter.clear();
                try {
                    loadMovieData(SORT_BY_OPTION);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
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

//    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
//
//        String moviesJSONResponse;
//        ArrayList<Movie> arrayListOfMovies;
//
//        @Override
//        protected ArrayList<Movie> doInBackground(String... strings) {
//
//            URL moviesDataRequestUrl = null;
//
//
//            // check for network connection
//            if(isOnline()) {
//                try {
//                    // create url to query API
//                    moviesDataRequestUrl = NetworkUtils.buildUrl();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            try {
//                // receive JSON response
//                moviesJSONResponse = NetworkUtils.getResponseFromHttpUrl(moviesDataRequestUrl);
//
//                // parse JSON into a movie object and
//                // place in array list of movie objects
//                arrayListOfMovies = JsonUtils.parseMovieJson(moviesJSONResponse);
//                return arrayListOfMovies;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//
//        } //end of method
//
//        @Override
//        protected void onPostExecute(ArrayList<MovieResults.Movie> movies) {
//
//            // tell the adapter to update the GridView UI with movie posters
//            adapter.updateUI(movies);
//        }
//    }
}
