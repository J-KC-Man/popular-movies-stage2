package com.jman.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jman.popularmovies.data.FavouriteMoviesContract.*;


/**
 * Created by Justin on 24/04/2018.
 *
 * Creates the database from the data model
 */

public class FavouriteMoviesDbHelper extends SQLiteOpenHelper {

    // specify the DB filename
    private static final String DATABASE_NAME = "favourites.db";

    // specify DB version
    private static final int DATABASE_VERSION = 1;

    public FavouriteMoviesDbHelper(Context context) {
        // the 3rd param is for a cursor factory
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
    * This method creates the table
    * */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouriteMovieEntry.TABLE_NAME + " (" +
                FavouriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteMovieEntry.COLUMN_NAME_MOVIE_ID + " TEXT NOT NULL, " +
                FavouriteMovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL" +
                "); ";

        // The line that creates the table
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
