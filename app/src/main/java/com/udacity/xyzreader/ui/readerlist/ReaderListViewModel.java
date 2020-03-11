package com.udacity.xyzreader.ui.readerlist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.xyzreader.ReaderApplication;
import com.udacity.xyzreader.common.Event;
import com.udacity.xyzreader.data.model.Reader;
import com.udacity.xyzreader.data.repository.ReaderRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReaderListViewModel extends ViewModel {

    private static final String TAG = ReaderListViewModel.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();
    MutableLiveData<List<Reader>> readers = new MutableLiveData<>();
    MutableLiveData<Event<Integer>> reader = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> loading = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> error = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public ReaderListViewModel() {
        loadData();
    }

    public void loadData() {
        if (isOnline()) loadFromNetwork();
        else loadFromDatabase();
    }

    private void loadFromNetwork() {
        loading.setValue(new Event<>(true));

        disposable.add(
                ReaderRepository.loadFromNetwork()
                        .subscribeOn(Schedulers.io())
                        .subscribe(items -> {
                            readers.postValue(items);
                            loading.postValue(new Event<>(false));

                            ReaderRepository.saveReadersIntoDB(items);

                        }, error -> {
                            this.error.postValue(new Event<>(true));
                            loading.postValue(new Event<>(false));

                            Log.d(TAG, error.getMessage());
                        })
        );
    }

    private void loadFromDatabase() {
        loading.setValue(new Event<>(true));

        disposable.add(
                ReaderRepository.loadFromDatabase()
                        .subscribeOn(Schedulers.io())
                        .subscribe(items -> {
                            readers.postValue(items);
                            loading.postValue(new Event<>(false));

                        }, error -> {
                            this.error.postValue(new Event<>(true));
                            loading.postValue(new Event<>(false));

                            Log.d(TAG, error.getMessage());
                        })
        );
    }

    public void onItemSelected(Event<Integer> event) {
        reader.setValue(event);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                ReaderApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        Network network = cm.getActiveNetwork();
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);

        if (capabilities == null) return false;

        return
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
    }
}
