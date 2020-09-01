package com.example.minesweaper;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SaveViewModel extends AndroidViewModel {

    private SaveRepository mRepository;

    private LiveData<List<Board>> mAllWords;

    public SaveViewModel (Application application) {
        super(application);
        mRepository = new SaveRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Board>> getAllWords() { return mAllWords; }

    LiveData<Integer> getMaxId() { return mRepository.getMaxId();}

    public void insert(Board board) { mRepository.insert(board); }

    public void deleteAll(Integer id) {mRepository.deleteAll(id);}
}
