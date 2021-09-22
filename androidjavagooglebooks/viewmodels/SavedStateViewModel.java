package com.trainingkotlin.androidjavagooglebooks.viewmodels;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.trainingkotlin.androidjavagooglebooks.models.Volume;

import java.util.List;

public class SavedStateViewModel extends ViewModel {
    private static final String NAME_KEY = "results";
    private final SavedStateHandle mState;

    public SavedStateViewModel(SavedStateHandle mState) {
        this.mState = mState;
    }
    // Expose an immutable LiveData
    public List<Volume> getResults() {
        // getLiveData obtains an object that is associated with the key wrapped in a LiveData
        // so it can be observed for changes.
        return mState.get(NAME_KEY);
    }

    public void saveResults(List<Volume> results) {
        // Sets a new value for the object associated to the key. There's no need to set it
        // as a LiveData.
        mState.set(NAME_KEY, results);
    }
}
