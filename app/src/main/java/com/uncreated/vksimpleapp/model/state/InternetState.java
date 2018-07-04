package com.uncreated.vksimpleapp.model.state;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class InternetState {

    public boolean isInternetTurnOn() {
        //TODO: check internet state
        return true;
    }

    public Observable<Boolean> isOnline(int timeout) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Socket socket = new Socket();
            SocketAddress sockAddress = new InetSocketAddress("8.8.8.8", 53);

            socket.connect(sockAddress, timeout);
            socket.close();

            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }
}
