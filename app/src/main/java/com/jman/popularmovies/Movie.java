package com.jman.popularmovies;

/**
 * Created by Justin on 31/03/2018.
 *
 * This class instaniates Movie objects that hold information about a Movie extracted from the parsed movieJSON
 *
 * This info had been put inside a Movie object in JsonUtils
 */

public class Movie {

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
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVote_average() {
        return vote_average;
    }
}
