package com.udacity.xyzreader.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReaderResponse {
    @Expose
    @SerializedName("id")
    public final long id;

    @Expose
    @SerializedName("title")
    public final String title;

    @Expose
    @SerializedName("author")
    public final String author;

    @Expose
    @SerializedName("body")
    public final String body;

    @Expose
    @SerializedName("thumb")
    public final String thumb;

    @Expose
    @SerializedName("photo")
    public final String photo;

    @Expose
    @SerializedName("aspect_ratio")
    public final double aspectRatio;

    @Expose
    @SerializedName("published_date")
    public final String publishedDate;

    public ReaderResponse(long id, String title, String author, String body, String thumb, String photo, double aspectRatio, String publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.aspectRatio = aspectRatio;
        this.publishedDate = publishedDate;
    }
}
