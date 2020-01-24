package com.appface.akhil.daggerapp.model;

import android.app.Application;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class StockRepository {

    private StockDao stockDao;
    private StockUnavailableDao stockUnavailableDao;
    private Flowable<List<Stock>> allStocks;
    private Flowable<List<StockUnavailable>> allStocksUnavailable;
    private Flowable<List<Category>> allCategories;
    private Flowable<List<Stock>> allCategoryEntires;

    public StockRepository(Application application) {
        StockDatabase stockDatabase = StockDatabase.getInstance(application);

        stockDao = stockDatabase.stockdao();
        stockUnavailableDao = stockDatabase.stockUnavailableDao();

        allStocks = stockDao.getAllStocks();
        allStocksUnavailable = stockUnavailableDao.getAllStocks();
        allCategories = stockDao.getAllCategories();
    }

    public Completable insert(final Stock stock) {
        return stockDao.insert(stock);
    }

    public Completable delete(Stock stock) {
        return stockDao.delete(stock);
    }

    public Completable deleteAllStocks() {
        return stockDao.deleteAllLocalStockInfo();
    }

    public Flowable<List<Stock>> getAllStocks() {
        return allStocks;
    }

    public Flowable<List<Category>> getAllCategories() {
        return allCategories;
    }

    public Flowable<List<Stock>> getselectedCategoryEntries(String brandName) {return stockDao.getCategoryEntires(brandName);}

    public Completable insertUnavailable(final StockUnavailable stock) {
        return stockUnavailableDao.insert(stock);
    }

    public Flowable<List<StockUnavailable>> getAllUnavailableStocks() {
        return allStocksUnavailable;
    }

}
