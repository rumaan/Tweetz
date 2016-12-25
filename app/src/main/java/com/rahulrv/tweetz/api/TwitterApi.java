package com.rahulrv.tweetz.api;

import com.rahulrv.tweetz.model.SearchResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 *
 */

public interface TwitterApi {

    @GET("search/tweets.json")
    Flowable<SearchResponse> searchTweets(@Query("q") String query);

    @GET("trends/place.json")
    Flowable<Object> getTrends(@Query("id") String placeId);
}
