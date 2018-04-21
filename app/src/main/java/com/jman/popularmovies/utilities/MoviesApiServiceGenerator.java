package com.jman.popularmovies.utilities;

import com.jman.popularmovies.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Justin on 05/04/2018.
 */

public class MoviesApiServiceGenerator {


    public static MoviesApiService createService() {
        
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(MoviesApiService.MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return builder.create(MoviesApiService.class);
    }


}
