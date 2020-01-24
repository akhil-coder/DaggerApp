package com.appface.akhil.daggerapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Stock.class, StockUnavailable.class}, version = 6)
public abstract class StockDatabase extends RoomDatabase {

    private static StockDatabase instance;
    public abstract StockDao stockdao();
    public abstract StockUnavailableDao stockUnavailableDao();

    public static synchronized StockDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), StockDatabase.class, "Stock_database")
                        .fallbackToDestructiveMigration()
                        .build();
        }
        return instance;
    }
}
