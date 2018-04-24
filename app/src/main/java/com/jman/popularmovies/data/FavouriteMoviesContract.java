package com.jman.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Justin on 24/04/2018.
 *
 * defines db model: table name and names of columns
 */

public class FavouriteMoviesContract {

    private FavouriteMoviesContract() {}

    public static final class FavouriteMovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourites";

        public static final String COLUMN_NAME_MOVIE_ID = "movieId";

        public static final String COLUMN_NAME_TITLE = "title";


    }
}
