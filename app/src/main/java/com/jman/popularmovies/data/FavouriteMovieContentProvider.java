package com.jman.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.jman.popularmovies.data.FavouriteMoviesContract.FavouriteMovieEntry.TABLE_NAME;

/**
 * Created by Justin on 12/05/2018.
 */

public class FavouriteMovieContentProvider extends ContentProvider {

    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int FAVOURITES = 100; // match directories
    public static final int FAVOURITES_WITH_ID = 101; // match a single row

    //  static variable reference for Uri matcher
    public static final UriMatcher sUriMatcher = buildUriMatcher();


    // method associates URI's with their int match
    public static UriMatcher buildUriMatcher() {
        // tell it which URIs to recognise and the integer constants they'll match with
        // construct an empty matcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // match directory
        uriMatcher.addURI(FavouriteMoviesContract.CONTENT_AUTHORITY, FavouriteMoviesContract.PATH_FAVOURITES, FAVOURITES);


        // match single item
        uriMatcher.addURI(FavouriteMoviesContract.CONTENT_AUTHORITY, FavouriteMoviesContract.PATH_FAVOURITES + "/#", FAVOURITES_WITH_ID);

        return uriMatcher;
    }

    // Member variable for a TaskDbHelper that's initialized in the onCreate() method
    private FavouriteMoviesDbHelper mFavouriteMoviesDbHelper;

    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {

        Context context = getContext();
        mFavouriteMoviesDbHelper = new FavouriteMoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // get access to favourites db
        final SQLiteDatabase db = mFavouriteMoviesDbHelper.getWritableDatabase();

        //identify the match for the favourites directory
        int match = sUriMatcher.match(uri);

        Cursor returnCursor; // cursor to be returned

        switch (match) {
            case FAVOURITES:
                // Default case throws an UnsupportedOperationException
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // set notification URI on cursor so it will know about any changes to the URI
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // get access to favourites db
        final SQLiteDatabase db = mFavouriteMoviesDbHelper.getReadableDatabase();

        //identify the match for the favourites directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case FAVOURITES:
                // Inserting values into favourites table
                long id = db.insert(FavouriteMoviesContract.FavouriteMovieEntry.TABLE_NAME, null, values);
                // if insert was not successful the method will return -1
                if ( id > 0 ) { // if insert was successful
                    returnUri = ContentUris.withAppendedId(FavouriteMoviesContract.FavouriteMovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // notify content resolver if URI has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mFavouriteMoviesDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int favouriteMoviesDeleted; // starts as 0

        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case FAVOURITES_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);

                favouriteMoviesDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (favouriteMoviesDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return favouriteMoviesDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
