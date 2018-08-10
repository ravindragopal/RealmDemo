package com.example.chaitanya.realmdemo.RxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chaitanya.realmdemo.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxTest extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);


        Observable<String> animalsObservable = getAnimalObservable();

        Observer<String> animalObserver = getAnimalObserver();

        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalObserver);
    }

    public Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("@", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d("@", "Name : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("@", "onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("@", "onComplete");
            }
        };
    }

    public Observable<String> getAnimalObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }
}
