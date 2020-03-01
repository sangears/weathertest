package com.example.weathertest.mvp;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;

public interface MainContract {
    interface View {

        void onError(@NonNull Throwable throwable);
        void loadProgres();
        void cancelProgres();
       // void showText(@NonNull List<City> cities);

        void showText(List<com.example.weathertest.mvp.pojo.List> lists);
    }

    interface Presenter {

        void subscribe();

        void unsubscribe(boolean isFinishing);

        void onButtonWasClicked();
        void onButtonWasClicked2(@NonNull List<com.example.weathertest.mvp.pojo.List> cities, Context context);
    }

    interface Repository {
        Single<List<com.example.weathertest.mvp.pojo.List>> loadMessage();


        void loadMessage2(@NonNull List<com.example.weathertest.mvp.pojo.List> cities,Context context);




    }
}