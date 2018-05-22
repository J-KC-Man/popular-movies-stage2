package com.jman.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Justin on 20/05/2018.
 */

public class Review {

    // the @SerializedName("posterPath") annotations help map the JSON response keys
    // to the fields that have been declared - this is for GSON

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    public Review(String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
