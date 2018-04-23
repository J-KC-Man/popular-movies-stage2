package com.jman.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 06/04/2018.
 *
 * This is the model class to represent the structure of the Json response.
 * We are interested in the "results" json array and the json 'movie' objects inside of it. Eg:
 *
 * results: [ (MovieResults)
 *     {
 *         json object 1 (Movie)
 *     }
 *
 *     {
 *         Json object 2 (Movie)
 *     }
 *
 * ]
 */

public class MovieResults {

    /* the arraylist to map to the results json array that should have inside it movie objects */
    @SerializedName("results")
    private ArrayList<Movie> listOfMovies = new ArrayList<>();

    public ArrayList<Movie> getListOfMovies() {
        return listOfMovies;
    }


    /**
     * Created by Justin on 31/03/2018.
     *
     * This class instantiates Movie objects that hold information about a Movie extracted from the parsed movieJSON
     *
     * This info had been put inside a Movie object in JsonUtils
     */

    public static class Movie implements Parcelable {

        // the @SerializedName("posterPath") annotations help map the JSON response keys
        // to the fields that have been declared - this is for GSON

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("overview")
        private String overview;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("id")
        private String id;

        @SerializedName("original_title")
        private String title;

        @SerializedName("popularity")
        private String popularity;

        @SerializedName("vote_average")
        private String voteAverage;

        public Movie(String posterPath, String overview, String releaseDate, String id, String title, String popularity, String voteAverage) {
            this.posterPath = posterPath;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.id = id;
            this.title = title;
            this.popularity = popularity;
            this.voteAverage = voteAverage;
        }

        public String getPosterPath() {
            return this.posterPath;
        }

        public String getOverview() {
            return this.overview;
        }

        public String getReleaseDate() {
            return this.releaseDate;
        }

        public String getId() {
            return this.id;
        }

        public String getTitle() {

            return this.title;
        }

        public String getPopularity() {
            return this.popularity;
        }

        public String getVoteAverage() {
            return this.voteAverage;
        }

        protected Movie(Parcel in) {
            posterPath = in.readString();
            overview = in.readString();
            releaseDate = in.readString();
            id = in.readString();
            title = in.readString();
            popularity = in.readString();
            voteAverage = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(posterPath);
            dest.writeString(overview);
            dest.writeString(releaseDate);
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(popularity);
            dest.writeString(voteAverage);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<com.jman.popularmovies.MovieResults.Movie> CREATOR = new Parcelable.Creator<com.jman.popularmovies.MovieResults.Movie>() {
            @Override
            public com.jman.popularmovies.MovieResults.Movie createFromParcel(Parcel in) {
                return new com.jman.popularmovies.MovieResults.Movie(in);
            }

            @Override
            public com.jman.popularmovies.MovieResults.Movie[] newArray(int size) {
                return new com.jman.popularmovies.MovieResults.Movie[size];
            }
        };
    }

}
