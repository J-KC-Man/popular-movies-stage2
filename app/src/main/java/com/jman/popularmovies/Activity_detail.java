package com.jman.popularmovies;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jman.popularmovies.Models.Review;
import com.jman.popularmovies.Models.ReviewResults;
import com.jman.popularmovies.Models.Trailer;
import com.jman.popularmovies.Models.TrailerResults;
import com.jman.popularmovies.adapters.MovieDetailRecyclerViewAdapter;
import com.jman.popularmovies.data.FavouriteMoviesContract;
import com.jman.popularmovies.utilities.MoviesApiService;
import com.jman.popularmovies.utilities.MoviesApiServiceGenerator;
import com.jman.popularmovies.utilities.MyMovieDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = Activity_detail.class.getSimpleName();

    /* the adapter*/
    private MovieDetailRecyclerViewAdapter mAdapter;

    /* RV */
    private RecyclerView movieDetailRecyclerView;

    // To hold the Movie parcel
    private MovieResults.Movie movie;

    // retrofit URL endpoint declarations
    private MoviesApiService moviesApiClientService;

    // the list of review json objects represented as Java Movie objects
    private ArrayList<Review> reviews = new ArrayList<>();


    // the list of review json objects represented as Java Movie objects
    private ArrayList<Trailer> trailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the parcelable from the bundle (this.getIntent().getExtras())
        movie = this.getIntent().getExtras().getParcelable("movieDetails");

        movieDetailRecyclerView = this.findViewById(R.id.movie_detail_view);
        movieDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MovieDetailRecyclerViewAdapter(movie, reviews, trailers, this);

        // divider line at bottom of review view
        movieDetailRecyclerView.addItemDecoration(new DividerItemDecoration(Activity_detail.this, DividerItemDecoration.VERTICAL));

        // connect adapter and views to recycler view
        movieDetailRecyclerView.setAdapter(mAdapter);


        if(savedInstanceState != null) {
            movie = savedInstanceState.getParcelable("movieDetails");
        }

        // load movie reviews and trailers to UI
        try {
            loadReviewData(movie.getId());
            loadTrailerData(movie.getId());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movieDetails", movie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movie = savedInstanceState.getParcelable("movieDetails");
    }

    /**
     * This method is called when user clicks on the Add to favourites button
     *
     * @param view The calling view (button)
     */
    public void addToFavouritesList(View view) {

        // get the id and title of movie
       String movieId = movie.getId();
       String movieTitle = movie.getTitle();

       // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID, movieId);
        contentValues.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_TITLE, movieTitle);

        Uri uri = getContentResolver().insert(FavouriteMoviesContract.FavouriteMovieEntry.CONTENT_URI, contentValues);

        // if URI exists, show uri in toast
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

    }


    public void loadReviewData(String movieId) throws TimeoutException {
        Call<ReviewResults> networkRequest;
        try {

            // instantiate/create implementation of the MoviesApiService interface
            moviesApiClientService = MoviesApiServiceGenerator.createService();

            // check if device has network connection
            if(!isOnline()) {
                Toast.makeText(
                        Activity_detail.this,
                        "No internet connection",
                        Toast.LENGTH_LONG).show();
                throw new TimeoutException("Connect timeout: no network connection");
            } else {
                // make the network request to connect to the server
                networkRequest = moviesApiClientService.getReviewsJson(movieId, MyMovieDatabase.API_KEY);

                networkRequest.enqueue(new Callback<ReviewResults>() {
                    @Override
                    public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {

                        if(response.isSuccessful()) {

                            // assign a new list of movie objects mapped from json objects to the parsed response
                            // so we can work with it
                            reviews = response.body().getListOfReviews();

                            if (mAdapter == null) {
                                // init adapter and attach to gridView
                                mAdapter = new MovieDetailRecyclerViewAdapter(movie, reviews, trailers, Activity_detail.this);
                                movieDetailRecyclerView.setAdapter(mAdapter);
                            } else {
                                mAdapter.updateReviewsUI(reviews);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResults> call, Throwable t) {
                        Toast.makeText(
                                Activity_detail.this,
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

    public void loadTrailerData(String movieId) throws TimeoutException {
        Call<TrailerResults> networkRequest;
        try {

            // instantiate/create implementation of the MoviesApiService interface
            moviesApiClientService = MoviesApiServiceGenerator.createService();

            // check if device has network connection
            if(!isOnline()) {
                Toast.makeText(
                        Activity_detail.this,
                        "No internet connection",
                        Toast.LENGTH_LONG).show();
                throw new TimeoutException("Connect timeout: no network connection");
            } else {
                // make the network request to connect to the server
                networkRequest = moviesApiClientService.getTrailersJson(movieId, MyMovieDatabase.API_KEY);

                networkRequest.enqueue(new Callback<TrailerResults>() {
                    @Override
                    public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {

                        if(response.isSuccessful()) {

                            // assign a new list of movie objects mapped from json objects to the parsed response
                            // so we can work with it
                            trailers = response.body().getListOfTrailers();


                            if (mAdapter == null) {
                                // init adapter and attach to gridView
                                mAdapter = new MovieDetailRecyclerViewAdapter(movie, reviews, trailers, Activity_detail.this);
                                movieDetailRecyclerView.setAdapter(mAdapter);
                            } else {
                                mAdapter.updateTrailersUI(trailers);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<TrailerResults> call, Throwable t) {
                        Toast.makeText(
                                Activity_detail.this,
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
} // end of class
