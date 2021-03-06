package com.jman.popularmovies.utilities;


import com.jman.popularmovies.Models.Review;
import com.jman.popularmovies.Models.ReviewResults;
import com.jman.popularmovies.Models.Trailer;
import com.jman.popularmovies.Models.TrailerResults;
import com.jman.popularmovies.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Justin on 05/04/2018.
 *
 */

public interface MoviesApiService {

      String MOVIE_DB_URL = "http://api.themoviedb.org/3/";


    // full url : http://api.themoviedb.org/3/discover/movie?api_key=<API_KEY>

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

    @GET("movie/{id}/reviews")
    Call<ReviewResults> getReviewsJson (
            @Path("id") String movieId,
            @Query("api_key") String key
    );

    @GET("movie/{id}/videos")
    Call<TrailerResults> getTrailersJson (
            @Path("id") String movieId,
            @Query("api_key") String key
    );
}
