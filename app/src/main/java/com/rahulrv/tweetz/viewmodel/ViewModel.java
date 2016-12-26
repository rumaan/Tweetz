package com.rahulrv.tweetz.viewmodel;

/**
 *
 *
 */

public interface ViewModel<V extends IView> {

    void attach(V view);

    void detach();
}
