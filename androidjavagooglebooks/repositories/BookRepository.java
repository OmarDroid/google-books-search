package com.trainingkotlin.androidjavagooglebooks.repositories;

import androidx.lifecycle.MutableLiveData;

import com.trainingkotlin.androidjavagooglebooks.api.BookSearchService;
import com.trainingkotlin.androidjavagooglebooks.models.VolumeResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {
    private static String BASE_URL = "https://www.googleapis.com/";
    public static String API_KEY = "AIzaSyBbvDZAEHk-Gh4HUSWHz_v8-Rl54HunwlM";
    private BookSearchService bookSearchService;
    private MutableLiveData<VolumeResponse> volumeResponseMutableLiveData;

    public BookRepository() {
        volumeResponseMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        bookSearchService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookSearchService.class);
    }

    public void searchBook(String keyword, String author, String apiKey) {
        bookSearchService.searchBook(keyword, author, apiKey)
                .enqueue(new Callback<VolumeResponse>() {
                    @Override
                    public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                        if (response.body() != null) {
                            volumeResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolumeResponse> call, Throwable t) {
                        volumeResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public MutableLiveData<VolumeResponse> getVolumeResponseMutableLiveData() {
        return volumeResponseMutableLiveData;
    }
}
