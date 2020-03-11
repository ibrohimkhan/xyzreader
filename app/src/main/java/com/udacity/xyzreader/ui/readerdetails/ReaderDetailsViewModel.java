package com.udacity.xyzreader.ui.readerdetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.xyzreader.common.Event;
import com.udacity.xyzreader.data.model.Reader;
import com.udacity.xyzreader.data.repository.ReaderRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReaderDetailsViewModel extends ViewModel {

    private static final String TAG = ReaderDetailsViewModel.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<List<Reader>> readers = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error = new MutableLiveData<>();

    public ReaderDetailsViewModel() {
        loadFromDatabase();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    private void loadFromDatabase() {
        disposable.add(
                ReaderRepository.loadFromDatabase()
                        .subscribeOn(Schedulers.io())
                        .subscribe(items -> {
                            readers.postValue(items);

                        }, error -> {
                            this.error.postValue(new Event<>(true));
                            Log.d(TAG, error.getMessage());
                        })
        );
    }
}
