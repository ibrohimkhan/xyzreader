package com.udacity.xyzreader.data.remote;

import com.udacity.xyzreader.BuildConfig;
import com.udacity.xyzreader.data.remote.response.ReaderResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkService {

    @GET(BuildConfig.BASE_URL)
    Single<List<ReaderResponse>> loadReaders();
}
