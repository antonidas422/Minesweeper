package com.example.minesweaper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


    public class SaveRepository {

        private SaveDao mSaveDao;
        private LiveData<List<Board>> mAllWords;

        SaveRepository(Application application) {
            SaveDatabase db = SaveDatabase.getDatabase(application);
            mSaveDao = db.wordDao();
            mAllWords = mSaveDao.getAllWords();
        }

        LiveData<List<Board>> getAllWords() {
            return mAllWords;
        }
        LiveData<Integer> getMaxId() { return mSaveDao.geyLastCount(); }

        public void insert (Board board) {
            new insertAsyncTask(mSaveDao).execute(board);
        }
        public void deleteAll(Integer id)  {
            new deleteAllWordsAsyncTask(mSaveDao).execute(id);
        }

        private static class insertAsyncTask extends AsyncTask<Board, Void, Void> {

            private SaveDao mAsyncTaskDao;

            insertAsyncTask(SaveDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Board... params) {
                mAsyncTaskDao.insert(params[0]);
                return null;
            }
        }

        private static class deleteAllWordsAsyncTask extends AsyncTask<Integer, Void, Void> {
            private SaveDao mAsyncTaskDao;

            deleteAllWordsAsyncTask(SaveDao dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Integer... params) {
                mAsyncTaskDao.deleteAll(params[0]);
                return null;
            }
        }
    }

