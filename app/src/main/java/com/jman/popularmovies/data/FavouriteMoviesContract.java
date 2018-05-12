package com.jman.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Justin on 24/04/2018.
 *
 * defines db model: table name and names of columns
 */

public class FavouriteMoviesContract {

    /* create URI for the path of data in the SQLite db It should include:
    *   1) Content authority,
        2) Base content URI,
        3) Path(s) to the tasks directory
        4) Content URI for data in the TaskEntry class
    * */

    public static final String CONTENT_AUTHORITY = "com.jman.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // path to database table
    public static final String PATH_FAVOURITES = "favourites";

    private FavouriteMoviesContract() {}

    public static final class FavouriteMovieEntry implements BaseColumns {

        // the content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITES)
                .build();

        public static final String TABLE_NAME = "favourites";

        public static final String COLUMN_NAME_MOVIE_ID = "movieId";

        public static final String COLUMN_NAME_TITLE = "title";


    }
}
