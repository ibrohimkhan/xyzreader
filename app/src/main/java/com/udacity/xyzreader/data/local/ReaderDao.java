package com.udacity.xyzreader.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ReaderDao {

    @Query("select * from readers")
    Single<List<ReaderEntity>> select();

    @Insert
    void insert(List<ReaderEntity> entities);

    @Query("delete from readers")
    void delete();
}
