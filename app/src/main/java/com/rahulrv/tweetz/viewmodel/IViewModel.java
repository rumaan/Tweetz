package com.rahulrv.tweetz.viewmodel;

/**
 *
 *
 */

public interface IViewModel<V extends IView> {

    void attach(V view);

    void detach();
}
