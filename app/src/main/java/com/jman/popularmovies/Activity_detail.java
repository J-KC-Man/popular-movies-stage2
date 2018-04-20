package com.jman.popularmovies;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = Activity_detail.class.getSimpleName();

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

    }
}
