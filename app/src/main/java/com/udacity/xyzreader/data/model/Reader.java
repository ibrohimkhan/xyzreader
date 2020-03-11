package com.udacity.xyzreader.data.model;

import java.io.Serializable;

public class Reader implements Serializable {
    public final long id;
    public final String title;
    public final String author;
    public final String body;
    public final String thumb;
    public final String photo;
    public final double aspectRatio;
    public final String publishedDate;

    public Reader(long id, String title, String author, String body, String thumb, String photo, double aspectRatio, String publishedDate) {
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
