package com.example.minesweaper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SaveDao {
    @Insert
    void insert(Board board);

    @Query("DELETE FROM save_table WHERE id = :id")
    void deleteAll(Integer id);

    @Query("SELECT * from save_table ORDER BY id")
    LiveData<List<Board>> getAllWords();

    @Query("SELECT MAX(id) FROM save_table")
    LiveData<Integer> geyLastCount();
}
