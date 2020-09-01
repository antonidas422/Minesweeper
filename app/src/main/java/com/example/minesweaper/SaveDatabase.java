package com.example.minesweaper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Board.class}, version = 5, exportSchema = false)
public abstract class SaveDatabase extends RoomDatabase {

    public abstract SaveDao wordDao();
    private static SaveDatabase INSTANCE;

    static SaveDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SaveDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
