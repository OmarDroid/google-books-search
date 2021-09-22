package com.trainingkotlin.androidjavagooglebooks.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trainingkotlin.androidjavagooglebooks.R;
import com.trainingkotlin.androidjavagooglebooks.adapters.BookSearchResultAdapter;
import com.trainingkotlin.androidjavagooglebooks.viewmodels.BookSearchViewModel;
import com.trainingkotlin.androidjavagooglebooks.viewmodels.SavedStateViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class BookSearchFragment extends Fragment implements View.OnClickListener {

    private BookSearchViewModel bookSearchViewModel;
    private BookSearchResultAdapter bookSearchResultAdapter;
    private TextInputEditText authorInputEt, bookInputEt;
    private Button btnSearch;
    private SavedStateViewModel mSavedStateViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the ViewModel
        mSavedStateViewModel = new ViewModelProvider(this).get(SavedStateViewModel.class);

        bookSearchViewModel = new ViewModelProvider(this).get(BookSearchViewModel.class);
        bookSearchViewModel.init();
        bookSearchResultAdapter = new BookSearchResultAdapter();
        if (mSavedStateViewModel.getResults() == null) {
            bookSearchViewModel.getVolumeResponseLiveData().observe(this, volumeResponse -> {
                if (volumeResponse != null) {
                    mSavedStateViewModel.saveResults(volumeResponse.getItems());
                    bookSearchResultAdapter.setResults(volumeResponse.getItems());
                }
            });
        } else {
            bookSearchResultAdapter.setResults(mSavedStateViewModel.getResults());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_search, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookSearchResultAdapter);

        //Setting rest of UI elements
        bookInputEt = view.findViewById(R.id.textInputEditTextBookTitleSearch);
        authorInputEt = view.findViewById(R.id.textInputEditTextBookAuthorSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                performSearch();
                break;
        }
    }

    private void performSearch() {
        String author = authorInputEt.getEditableText().toString();
        String book = bookInputEt.getEditableText().toString();
        bookSearchViewModel.searchBook(book, author);

        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}