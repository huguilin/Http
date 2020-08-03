package com.enjoy.http;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.enjoy.http.api.WeatherApiInterface;
import com.enjoy.http.api.Weather;
import com.enjoy.http.databinding.ActivityMainBinding;
import com.enjoy.network.AmapWeatherApi;
import com.enjoy.network.base.NetworkApi;
import com.enjoy.network.environment.EnvironmentActivity;
import com.enjoy.network.errorhandler.ExceptionHandle;
import com.enjoy.network.observer.BaseObserver;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkApi.chooseEnvironment(MainActivity.this);
            }
        });

        mainBinding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmapWeatherApi.getService(WeatherApiInterface.class)
                        .postWeather("长沙")
                        .compose(AmapWeatherApi.getInstance().applySchedulers(new BaseObserver<Weather>() {

                            @Override
                            public void onSuccess(Weather weather) {
                                Log.i(TAG, "onSuccess : " + weather);
                            }

                            @Override
                            public void onFailure(ExceptionHandle.ResponeThrowable e) {
                                e.printStackTrace();
                                Log.i(TAG, "onFailure : " + e.message);
                            }
                        }));

            }
        });
    }

}
