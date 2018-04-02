package com.jman.popularmovies;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.jman.popularmovies.utilities.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 31/03/2018.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {

    // for log debugging
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    List<Movie> postersOfMovies;


    public MoviesAdapter(Activity context, List<Movie> movies) {
        // initialize the ArrayAdapter's internal storage for the context and the list
        // Custom adapter so second argument is 0
        super(context, 0, movies);

    }

    public void updateUI(ArrayList<Movie> moviePosters) {
        this.postersOfMovies = moviePosters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movies = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        // find the ref to the imageview
        ImageView moviePosterView = convertView.findViewById(R.id.moviePoster);

        // put the poster of the movie in the imageView
        Picasso
                .with(getContext())
                .load(movies.getPoster_path())
                .fit()
                .into(moviePosterView);


        return moviePosterView;
    }
}
