package com.rahulrv.tweetz.api;

import com.rahulrv.tweetz.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 *
 */

public interface TwitterApi {

    @GET("search/tweets.json")
    Observable<SearchResponse> searchTweets(@Query("q") String query);
}
