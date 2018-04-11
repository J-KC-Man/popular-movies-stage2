package com.jman.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Justin on 31/03/2018.
 *
 * This class instaniates Movie objects that hold information about a Movie extracted from the parsed movieJSON
 *
 * This info had been put inside a Movie object in JsonUtils
 */

public class Movie implements Parcelable {

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
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
