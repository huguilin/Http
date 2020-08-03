package com.enjoy.http;

import android.app.Application;

import com.enjoy.network.base.NetworkApi;


public class NetworkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new NetworkRequestInfo(this));
    }
}
