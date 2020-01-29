package com.appface.akhil.daggerapp.ui.splashscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.appface.akhil.daggerapp.BaseActivity;
import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.ui.auth.AuthActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenActivity extends BaseActivity {

    private static final String TAG = "SplashScreenActivity";
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressbar = findViewById(R.id.progressBarSplashScreen);
//        startAsyncTask();
        splashScreenRxJava();
    }

    private void startAsyncTask() {
        SplashScreenAsyncTask task = new SplashScreenAsyncTask(this);
        task.execute(3);
    }

    private class SplashScreenAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<SplashScreenActivity> splashScreenActivityWeakReference;

        public SplashScreenAsyncTask(SplashScreenActivity splashScreenActivity) {
            this.splashScreenActivityWeakReference = new WeakReference<SplashScreenActivity>(splashScreenActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SplashScreenActivity splashScreenActivity = splashScreenActivityWeakReference.get();
            if (splashScreenActivity == null || splashScreenActivity.isFinishing()) {
                return;
            }
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressbar.setProgress(0);
            SplashScreenActivity splashScreenActivity = splashScreenActivityWeakReference.get();
            if (splashScreenActivity == null || splashScreenActivity.isFinishing()) {
                return;
            }
            progressbar.setVisibility(View.INVISIBLE);
            startAuthentication();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            SplashScreenActivity splashScreenActivity = splashScreenActivityWeakReference.get();
            if (splashScreenActivity == null || splashScreenActivity.isFinishing()) {
                return;
            }
            progressbar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finished";
        }
    }

    private void splashScreenRxJava() {

        Observable.intervalRange(0L, 100L, 10, 10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        progressbar.setVisibility(View.INVISIBLE);
                        startAuthentication();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long v) throws Exception {
                        progressbar.setProgress(v.intValue());
                    }
                });
    }

    private void startAuthentication() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

}
