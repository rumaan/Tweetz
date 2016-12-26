package com.rahulrv.tweetz.viewmodel;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.api.TwitterApi;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for MainActivity
 */

public class MainActivityViewModel extends BaseViewModel<MainActivityView> {

    @Inject TwitterApi twitterApi;

    public MainActivityViewModel() {
        MyApplication.getComponent().inject(this);
    }

    public void fetchTrends(String locationId) {
        compositeDisposable.add(twitterApi.getTrends(locationId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(trendsResponses -> trendsResponses.get(0))
                .subscribe(trendsResponse -> {
                    view.load(trendsResponse.trends());
                }, throwable -> view.error(throwable)));
    }
}
