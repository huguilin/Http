package com.enjoy.http.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface WeatherApiInterface {

    @FormUrlEncoded
    @POST("/v3/weather/weatherInfo")
    Observable<Weather> postWeather(@Field("city") String city);

    @GET("/v3/weather/weatherInfo")
    Observable<Weather> getWeather(@Query("city") String city);
}
