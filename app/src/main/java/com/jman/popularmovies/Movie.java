package com.jman.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Justin on 31/03/2018.
 *
 * This class instaniates Movie objects that hold information about a Movie extracted from the parsed movieJSON
 *
 * This info had been put inside a Movie object in JsonUtils
 */

public class Movie implements Parcelable {

   private String poster_path;
   private String overview;
   private String release_date;
   private String id;
   private String title;
   private String popularity;
   private String vote_average;

   public Movie(String poster_path, String overview, String release_date, String id, String title, String popularity, String vote_average) {
       this.poster_path = poster_path;
       this.overview = overview;
       this.release_date = release_date;
       this.id = id;
       this.title = title;
       this.popularity = popularity;
       this.vote_average = vote_average;
   }

    public String getPoster_path() {
        return this.poster_path;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getRelease_date() {
        return this.release_date;
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

    public String getVote_average() {
        return this.vote_average;
    }

    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readString();
        title = in.readString();
        popularity = in.readString();
        vote_average = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(popularity);
        dest.writeString(vote_average);
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
