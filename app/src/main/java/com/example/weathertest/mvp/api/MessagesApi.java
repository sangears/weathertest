package com.example.weathertest.mvp.api;

import com.example.weathertest.mvp.pojo.Wap;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MessagesApi {
    @GET("box/city?bbox=36,54,41,59,100&APPID=6bccc2a5d69bf2fe14f0da6ab2c6fffd&lang=ru")
    //@GET("weather.json")

    Single<Wap> messages2();



}