package com.jman.popularmovies;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jman.popularmovies.data.FavouriteMoviesContract;
import com.squareup.picasso.Picasso;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = Activity_detail.class.getSimpleName();

    private SQLiteDatabase mDb;
    private FavouritesAdapter mAdapter;

    ImageView moviePoster;
    TextView title;
    TextView releaseDate;
    TextView overview;
    TextView popularity;
    TextView voteAverage;

    // To hold the Movie parcel
    MovieResults.Movie movie;

    private final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the parcelable from the bundle (this.getIntent().getExtras())
        movie = this.getIntent().getExtras().getParcelable("movieDetails");

        moviePoster = findViewById(R.id.movie_detailView_poster);

        title = findViewById(R.id.movie_title);
        releaseDate = findViewById(R.id.movie_release_date);
        overview = findViewById(R.id.movie_overview);
        popularity = findViewById(R.id.movie_popularity);
        voteAverage = findViewById(R.id.movie_vote_average);

        // put the poster of the movie in the imageView

        Picasso
                .with(this)
                .load(MOVIE_POSTER_BASE_URL + movie.getPosterPath())
                .fit()
                .into(moviePoster);

        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
        popularity.setText(movie.getPopularity());
        voteAverage.setText(movie.getVoteAverage());

        if(savedInstanceState != null) {
            movie = savedInstanceState.getParcelable("movieDetails");
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

    private long addNewGuest(String movieId, String movieTitle) {

        ContentValues cv = new ContentValues();

        cv.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID, movieId);
        cv.put(FavouriteMoviesContract.FavouriteMovieEntry.COLUMN_NAME_TITLE, movieTitle);

        return mDb.insert(FavouriteMoviesContract.FavouriteMovieEntry.TABLE_NAME, null, cv);
    }
}
