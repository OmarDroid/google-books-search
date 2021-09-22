package com.trainingkotlin.androidjavagooglebooks.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trainingkotlin.androidjavagooglebooks.models.VolumeResponse;
import com.trainingkotlin.androidjavagooglebooks.repositories.BookRepository;

import org.jetbrains.annotations.NotNull;

import static com.trainingkotlin.androidjavagooglebooks.repositories.BookRepository.API_KEY;

public class BookSearchViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private LiveData<VolumeResponse> volumeResponseLiveData;

    public BookSearchViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init() {
        bookRepository = new BookRepository();
        volumeResponseLiveData = bookRepository.getVolumeResponseMutableLiveData();
        setVolumeResponseLiveData(volumeResponseLiveData);
    }

    public void searchBook( String keyword, String author){
        bookRepository.searchBook(keyword, author, API_KEY);
    }

    public LiveData<VolumeResponse> getVolumeResponseLiveData() {
        return volumeResponseLiveData;
    }

    public void setVolumeResponseLiveData(LiveData<VolumeResponse> volumeResponseLiveData) {
        this.volumeResponseLiveData = volumeResponseLiveData;
    }
}
