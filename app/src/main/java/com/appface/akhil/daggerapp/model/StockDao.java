package com.appface.akhil.daggerapp.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface StockDao {

    @Insert
    Completable insert(Stock stock);

    @Delete
    Completable delete(Stock stock);

    @Query("DELETE FROM stock_table")
    Completable deleteAllLocalStockInfo();

    @Query("SELECT * FROM stock_table")
    Flowable<List<Stock>> getAllStocks();
}
