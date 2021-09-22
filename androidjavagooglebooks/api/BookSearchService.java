package com.trainingkotlin.androidjavagooglebooks.api;

import com.trainingkotlin.androidjavagooglebooks.models.VolumeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookSearchService {
    @GET("books/v1/volumes")
    Call<VolumeResponse> searchBook(
            @Query("q") String query,
            @Query("inauthor") String author,
            @Query("key") String apiKey
    );

}
