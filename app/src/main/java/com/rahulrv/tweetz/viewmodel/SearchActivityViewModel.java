package com.rahulrv.tweetz.viewmodel;

import com.rahulrv.tweetz.api.TwitterApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 *
 */

public class SearchActivityViewModel extends BaseViewModel<SearchActivityView> {

    TwitterApi twitterApi;

    public SearchActivityViewModel(TwitterApi twitterApi) {
        this.twitterApi = twitterApi;
    }

    public void searchTweets(String searchString) {
        twitterApi.searchTweets(searchString)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResponse -> {
                    view.showSearchResult(searchResponse.items());
                });
    }

}
