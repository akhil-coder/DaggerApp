package com.appface.akhil.daggerapp.ui.main.scanner;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.appface.akhil.daggerapp.BaseActivity;
import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.StockRepository;
import com.appface.akhil.daggerapp.model.StockResponse;
import com.appface.akhil.daggerapp.ui.main.availablestocks.StockViewModel;
import com.appface.akhil.daggerapp.viewmodelproviderfactory.ViewModelProviderFactory;
import com.google.zxing.Result;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "ScannerActivity";
    private static final int REQUEST_CAMERA = 1;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private StockViewModel viewModel;
    public static int TIMER_VALUE;

    @Inject
    StockRepository stockRepository;

    @Inject
    ViewModelProviderFactory providerFactory;

    private ZXingScannerView scannerView;
    private ZXingScannerView zScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TIMER_VALUE = loadSharedPreferences();

        setContentView(R.layout.activity_scanner);
        zScanner = (ZXingScannerView) findViewById(R.id.zxscan);

        scannerView = new ZXingScannerView(this);
        zScanner.addView(scannerView);

        viewModel = ViewModelProviders.of(this, providerFactory).get(StockViewModel.class);
        checkPermissions();
        observeDialogBox();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermissions();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions",
                                        (dialog, which) -> requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA));
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(ScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        final Context context = this;
        Log.d(TAG, "handleResult: Inside");
        final String scanResult = result.getText();
        try {
            long barcode = Long.parseLong(scanResult);
            viewModel.checkStockOnline(barcode);
        } catch (Exception e) {
            Log.e(TAG, "handleResult: ", e);
        }
        runTimer();
    }

    private void displayNoStockDialog() {
        Dialog dialog = new Dialog(ScannerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_stock_not_found);
        dialog.show();
    }

    private void runTimer() {
        Completable
                .timer(TIMER_VALUE, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        cameraReset();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void cameraReset() {
        scannerView.resumeCameraPreview(this);
    }

    void observeDialogBox() {
        viewModel.dialogEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean)
                    displayNoStockDialog();
            }
        });
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions();
            }
        }
    }
}
