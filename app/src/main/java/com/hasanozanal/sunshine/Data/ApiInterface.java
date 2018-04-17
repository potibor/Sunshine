package com.hasanozanal.sunshine.Data;

import com.hasanozanal.sunshine.Model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ozanal on 17.04.2018.
 */

public interface ApiInterface {

    @GET("weather")
    Call<List<Weather>> getWeather(@Query("lat") Double lat,@Query("lon") Double lon,@Query("APPID") String key,@Query("unit") String unit);
}
