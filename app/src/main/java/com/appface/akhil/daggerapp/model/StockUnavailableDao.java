package com.appface.akhil.daggerapp.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface StockUnavailableDao {

    @Insert
    Completable insert(StockUnavailable stock);

    @Delete
    Completable delete(StockUnavailable stock);

    @Query("DELETE FROM stock_table_unavailable")
    Completable deleteAllLocalStockInfo();

    @Query("SELECT * FROM stock_table_unavailable")
    Flowable<List<StockUnavailable>> getAllStocks();
}
