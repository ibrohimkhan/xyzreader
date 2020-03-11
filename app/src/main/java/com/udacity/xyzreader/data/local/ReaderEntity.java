package com.udacity.xyzreader.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "readers")
public class ReaderEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "reader_id")
    public final long readerId;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "author")
    public final String author;

    @ColumnInfo(name = "body")
    public final String body;

    @ColumnInfo(name = "thumb")
    public final String thumb;

    @ColumnInfo(name = "photo")
    public final String photo;

    @ColumnInfo(name = "aspect_ratio")
    public final double aspectRatio;

    @ColumnInfo(name = "published_date")
    public final String publishedDate;

    public ReaderEntity(int id, long readerId, String title, String author, String body, String thumb, String photo, double aspectRatio, String publishedDate) {
        this.id = id;
        this.readerId = readerId;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.aspectRatio = aspectRatio;
        this.publishedDate = publishedDate;
    }

    @Ignore
    public ReaderEntity(long readerId, String title, String author, String body, String thumb, String photo, double aspectRatio, String publishedDate) {
        this.readerId = readerId;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.aspectRatio = aspectRatio;
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }
}
