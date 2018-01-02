package com.example.phant.rthchallenge;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.phant.rthchallenge.di.component.AppComponent;
import com.example.phant.rthchallenge.di.component.DaggerAppComponent;
import com.example.phant.rthchallenge.di.module.NetworkModule;

/**
 * Created by tungphan on 4/8/17.
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App appInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        initInjector();
    }

    public static App getInstance() {
        return appInstance;
    }

    private void initInjector() {
        this.appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((App) context.getApplicationContext()).appComponent;
    }

    public boolean isConnectToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
