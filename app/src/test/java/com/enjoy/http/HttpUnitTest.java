package com.enjoy.http;

import com.enjoy.http.api.Weather;
import com.google.gson.Gson;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class HttpUnitTest {


    public interface WeatherApiInterface {

        @Multipart
        @POST("/v3/weather/weatherInfo")
        Observable<Weather> postWeather(@Part("city") String city, @Part("key") String key);

    }

    @Test
    public void testRetrofit() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl("https://restapi.amap.com/");
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        WeatherApiInterface api = retrofit.create(WeatherApiInterface.class);
        Observable<Weather> observable = api.postWeather("长沙市", "ae6c53e2186f33bbf240a12d80672d1b");
        observable.subscribe(new Consumer<Weather>() {
            @Override
            public void accept(Weather weather) throws Exception {
                System.out.println(new Gson().toJson(weather));
            }
        });


    }
}
