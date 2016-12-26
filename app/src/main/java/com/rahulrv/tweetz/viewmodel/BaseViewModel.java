package com.rahulrv.tweetz.viewmodel;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 *
 */

public class BaseViewModel<T extends IView, B> implements Observer<B> {

    protected CompositeDisposable compositeDisposable;
    T view;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public void attach(T view) {
        this.view = view;
    }

    public void detach() {
        compositeDisposable.clear();
    }

    @Override public void onSubscribe(Disposable d) {

    }

    @Override public void onNext(B value) {

    }

    @Override public void onError(Throwable e) {
        view.error();
    }

    @Override public void onComplete() {

    }
}
