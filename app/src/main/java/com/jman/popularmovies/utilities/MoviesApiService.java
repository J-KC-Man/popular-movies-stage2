package com.jman.popularmovies.utilities;

import com.jman.popularmovies.Movie;
import com.jman.popularmovies.MovieResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Justin on 05/04/2018.
 *
 */

public interface MoviesApiService {

      String MOVIE_DB_URL = "http://api.themoviedb.org/3/";


    // full url : http://api.themoviedb.org/3/discover/movie?api_key=<API_KEY>&language=en&sort_by=popularity.desc&include_adult=false&include_video=false

    /*
    * other half of the request execution mechanism:
    *
    * Converters turn bytes to objects,
    * Call adapters deal makes the network request and
    * handles request execution (sync/async, threading) using the Call object.
    * Separate call objects hold the request and response as a MovieResults object.
    * This object includes Java code that Gson looks for
    * to map the json properties to java properties. This includes list and field names.
    *
    * */
    @GET("movie/{sort_order}")
    Call<MovieResults> getMoviesJson (
            @Path("sort_order") String sortOrder,
            @Query("api_key") String key
    );


}
