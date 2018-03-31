package com.jman.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Justin on 30/03/2018.
 *
 * THis class will be used to communicate with the Movie DB API servers
 * build the url and retrieve response form server
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();



    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private static final String IMAGE_SIZE = "w185";

    /* The format we want our API to return */
    private static final String format = "json";

    private static final String POPULAR_ENDPOINT = "/popular";

    private static final String TOPRATED_ENDPOINT = "/movie/top_rated";

    // PARAMETERS

    private static final String MOVIE_DB_URL
            = "http://api.themoviedb.org/3/discover/movie";

    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String SORT_BY_PARAM = "sort_by";

    private static final String INCLUDE_ADULT_PARAM = "include_adult";
    private static final String INCLUDE_VIDEO_PARAM = "include_video";

    // VALUES

    private static final String API_KEY = "dce614ea305d1843c149725adc966a83";
    private static final String LANGUAGE = "en";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_VOTE_COUNT = "vote_count.desc";
    private static final String FALSE = "false";
    private static final String TRUE = "true";


    /**
     * Builds the URL used to talk to the movies server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * .
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl() {

        // full url : http://api.themoviedb.org/3/discover/movie?api_key=dce614ea305d1843c149725adc966a83&language=en&sort_by=popularity.desc&include_adult=false&include_video=false


        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .appendQueryParameter(SORT_BY_PARAM, SORT_BY_POPULARITY)
                .appendQueryParameter(INCLUDE_ADULT_PARAM, FALSE)
                .appendQueryParameter(INCLUDE_VIDEO_PARAM, FALSE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /*
    * This method returns the entire JSON string from the HTTP response.
    *
    * */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // to read json from inputstream
        BufferedReader reader = null;

        // to hold the final read-in json string
        String moviesJSON = null;
        try {
            InputStream in = urlConnection.getInputStream();

            // used to hold the read JSON and will grow dynamically in size
            StringBuffer buffer = new StringBuffer();


            reader = new BufferedReader(new InputStreamReader(in));

            // used to hold a read-in line
            String line;

            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            moviesJSON = buffer.toString();



        } catch (IOException e) {
            System.out.println("The JSON could not be read, please check stack trace");
            e.printStackTrace();
        }

        finally {
            if(urlConnection != null) {
                // if connection is still open, disconnect
                urlConnection.disconnect();
            }

            if(reader != null) {
                // if reader is still open, try closing it
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing reader, check stack trace");
                    e.printStackTrace();
                }
            }
        }

        // return the full and final JsonString as a String
        return moviesJSON;
    }

}