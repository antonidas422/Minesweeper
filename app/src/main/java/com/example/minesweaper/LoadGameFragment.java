package com.example.minesweaper;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadGameFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private LoadGameAdapter mAdapter;
    private SaveViewModel mSaveViewModel;

    public LoadGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_game, container, false);
        mSaveViewModel = ViewModelProviders.of(
                LoadGameFragment.this).get(SaveViewModel.class);



        mRecyclerView = v.findViewById(R.id.save_recycler);
        mAdapter = new LoadGameAdapter(this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        mRecyclerView.setLayoutManager(layoutManager);

        mSaveViewModel.getAllWords().observe(this, new Observer<List<Board>>() {
            @Override
            public void onChanged(@Nullable final List<Board> words) {
                // Update the cached copy of the words in the adapter.
                mAdapter.update(words);
            }
        });



        return v;
    }


}
