package com.rahulrv.tweetz.viewmodel;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.api.TwitterApi;
import com.rahulrv.tweetz.model.trends.TrendsResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 */

public class MainActivityViewModel extends BaseViewModel<MainActivityView, TrendsResponse> {

    @Inject TwitterApi twitterApi;

    public MainActivityViewModel() {
        MyApplication.getComponent().inject(this);
    }

    @Override public void attach(MainActivityView view) {
        super.attach(view);
    }

    public void updateData() {
        twitterApi.getTrends("2487956")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(trendsResponses -> trendsResponses.get(0))
                .subscribe(this);
    }

    @Override public void onNext(TrendsResponse value) {
        view.load(value.trends());
    }

}
