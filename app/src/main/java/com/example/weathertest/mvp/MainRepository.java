package com.example.weathertest.mvp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.weathertest.mvp.Adapter.Cities;
import com.example.weathertest.mvp.DB.MySQLiteHelper;
import com.example.weathertest.mvp.api.MessagesApi;
import com.example.weathertest.mvp.pojo.Wap;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainRepository implements MainContract.Repository {

    private static SharedPreferences sPref;
    private static final String TAG2 = "myLogs";

    private final MessagesApi mMessagesApi;

    public MainRepository(MessagesApi messagesApi) {
        mMessagesApi = messagesApi;
    }

    @Override
    public Single<List<com.example.weathertest.mvp.pojo.List>> loadMessage() {
        return mMessagesApi.messages2()
                .map(Wap::getList)
                .doOnError(throwable -> Log.d(TAG2, "MainRepository.loadMessage() thrown", throwable))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void loadMessage2(@NonNull List<com.example.weathertest.mvp.pojo.List> cities, Context context) {
        MySQLiteHelper db = new MySQLiteHelper(context);

        Thread t1 = new Thread() {
            public void run() {

                db.deleteTable();
                for (int i = 0; i < cities.size(); i++) {
                   //  Log.d(TAG2, "db.getAll_7"+cities.get(i).getMain().getTemp());
                    db.addCities(new Cities(cities.get(i).getName(), cities.get(i).getWeather().get(0).getIcon(), cities.get(i).getMain().getTemp(), cities.get(i).getMain().getFeelsLike(), cities.get(i).getWeather().get(0).getDescription(), cities.get(i).getMain().getPressure(), cities.get(i).getMain().getHumidity(), cities.get(i).getWind().getSpeed(), i));
                }

                db.close();
            }


        };
        t1.start();
    }


}

