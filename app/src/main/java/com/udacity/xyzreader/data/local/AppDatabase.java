package com.udacity.xyzreader.data.local;

import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.udacity.xyzreader.ReaderApplication;

@Database(entities = {ReaderEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "readers.db";

    private static volatile AppDatabase instance = null;

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    ReaderApplication.getContext(),
                    AppDatabase.class,
                    DB_NAME
            ).build();
        }

        Log.d(TAG, "Getting the database instance");
        return instance;
    }

    public abstract ReaderDao readerDao();
}
