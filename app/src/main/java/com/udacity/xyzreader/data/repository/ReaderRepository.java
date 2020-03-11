package com.udacity.xyzreader.data.repository;

import com.udacity.xyzreader.data.local.AppDatabase;
import com.udacity.xyzreader.data.local.ReaderEntity;
import com.udacity.xyzreader.data.model.Reader;
import com.udacity.xyzreader.data.remote.NetworkService;
import com.udacity.xyzreader.data.remote.Networking;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class ReaderRepository {

    private ReaderRepository() {}

    private static NetworkService service = Networking.createService(NetworkService.class);
    private static final AppDatabase database = AppDatabase.getInstance();

    public static Single<List<Reader>> loadFromNetwork() {
        return service.loadReaders()
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new Reader(
                        item.id,
                        item.title,
                        item.author,
                        item.body,
                        item.thumb,
                        item.photo,
                        item.aspectRatio,
                        item.publishedDate
                ))
                .toList();
    }

    public static Single<List<Reader>> loadFromDatabase() {
        return database.readerDao().select()
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new Reader(
                        item.readerId,
                        item.title,
                        item.author,
                        item.body,
                        item.thumb,
                        item.photo,
                        item.aspectRatio,
                        item.publishedDate
                ))
                .toList();
    }

    public static void saveReadersIntoDB(List<Reader> readers) {
        List<ReaderEntity> entities = Observable.fromIterable(readers)
                .map(item -> new ReaderEntity(
                        item.id,
                        item.title,
                        item.author,
                        item.body,
                        item.thumb,
                        item.photo,
                        item.aspectRatio,
                        item.publishedDate
                ))
                .toList()
                .blockingGet();

        if (entities != null && !entities.isEmpty()) {
            database.readerDao().delete();
            database.readerDao().insert(entities);
        }
    }
}
