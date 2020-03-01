package com.example.weathertest.mvp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.weathertest.mvp.pojo.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";


    private final MainContract.View mView;
    private final MainContract.Repository mRepository;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    private String message;


    public MainPresenter(MainContract.View mView,
                         MainContract.Repository repository) {
        this.mView = mView;
        mRepository = repository;
        Log.d(TAG, "Constructor");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe(boolean isFinishing) {
        mCompositeDisposable.clear();
    }


    @Override
    public void onButtonWasClicked() {
        Log.d(TAG, "onButtonWasClicked()");
        mCompositeDisposable.add(mRepository.loadMessage()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    // Здесь прогесс крутим
               mView.loadProgres();
                })
                .doFinally(() -> {
                    // Здесь вырубаем прогресс
                    mView.cancelProgres();
                })
                .subscribe(mView::showText, mView::onError));
    }

    @Override
    public void onButtonWasClicked2(@NonNull java.util.List<List> cities, Context context) {
        mRepository.loadMessage2(cities,context);
    }
}