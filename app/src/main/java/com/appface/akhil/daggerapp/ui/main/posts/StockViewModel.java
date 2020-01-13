package com.appface.akhil.daggerapp.ui.main.posts;

import android.app.Application;
import android.util.Log;

import com.appface.akhil.daggerapp.SessionManager;
import com.appface.akhil.daggerapp.model.Stock;
import com.appface.akhil.daggerapp.model.StockRepository;
import com.appface.akhil.daggerapp.model.StockResponse;
import com.appface.akhil.daggerapp.models.Post;
import com.appface.akhil.daggerapp.network.main.MainApi;
import com.appface.akhil.daggerapp.ui.main.Resource;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class StockViewModel extends AndroidViewModel {

    private static final String TAG = "StockViewModel";
    private StockRepository repository;
    private MediatorLiveData<Resource<List<Stock>>> stockList;
    private final SessionManager sessionManager;
    private final MainApi mainApi;

    @Inject
    Retrofit retrofit;

    @Inject
    public StockViewModel(@NonNull Application application, MainApi mainApi, SessionManager sessionManager) {
        super(application);
        repository = new StockRepository(application);
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
    }

    public MediatorLiveData<Resource<List<Stock>>> observePosts() {
        if (stockList == null) {
            stockList = new MediatorLiveData<>();
            stockList.setValue(Resource.loading((List<Stock>) null));

            final LiveData<Resource<List<Stock>>> source = LiveDataReactiveStreams.fromPublisher(
                    getAllStocks().map(new Function<List<Stock>, Resource<List<Stock>>>() {
                        @Override
                        public Resource<List<Stock>> apply(List<Stock> stocks) throws Exception {
                            if (stocks.size() > 0) {
                                if (stocks.get(0).getId() == -1) {
                                    return Resource.error("Something went wrong", null);
                                }
                            }
                            return Resource.success(stocks);
                        }
                    }).subscribeOn(Schedulers.io())

            );

            stockList.addSource(source, new Observer<Resource<List<Stock>>>() {
                @Override
                public void onChanged(Resource<List<Stock>> listResource) {
                    stockList.setValue(listResource);
                    stockList.removeSource(source);
                }
            });
        }
        return stockList;
    }

    public void checkStockOnline(long barcode) {

        mainApi.retrieveproduct(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<StockResponse>() {
                    @Override
                    public void onSuccess(StockResponse stockResponse) {
                        if(stockResponse.getStatusCode().equals("200"))
                        insertStockReactivly(stockResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    private void insertStockReactivly(StockResponse stockResponse) {
        repository.insert(stockResponse.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Entry Saved");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }


    public Completable insert(Stock stock) {
        return repository.insert(stock);
    }

    public Completable delete(Stock stock) {
        return repository.delete(stock);
    }

    public Completable deleteAllStocks() {
        return repository.deleteAllStocks();
    }

    public Flowable<List<Stock>> getAllStocks() {
        return repository.getAllStocks();
    }
}
