package com.example.chaitanya.realmdemo.RxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chaitanya.realmdemo.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxTest extends AppCompatActivity {


    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);

        Observable<String> animalsObservable = getAnimalObservable();

        Observer<String> animalObserver = getAnimalObserver();

        DisposableObserver<String> animalObserver1 = getAnimalObserver1();

        /*animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalObserver);*/
        animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("b");
                    }
                })
                .subscribeWith(animalObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    public Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("@", "onSubscribe");
                disposable = d;
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

    public DisposableObserver<String> getAnimalObserver1() {
        return new DisposableObserver<String>() {
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
}
