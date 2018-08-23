package com.example.chaitanya.realmdemo.EventBus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 14/8/18,10:50 AM.
 * For : ISS 24/7, Pune.
 */
public class RxBus {

    private static RxBus rxBusInstance;

    public static RxBus getRxBusInstant() {

            if(rxBusInstance == null){
                rxBusInstance = new RxBus();
            }

        return rxBusInstance;
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o){
        bus.onNext(o);
    }

    public Observable<Object> toObservable(){
        return bus;
    }
}
