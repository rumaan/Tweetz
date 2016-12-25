package com.rahulrv.tweetz.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 *
 */

public interface TwitterApi {

    @GET("search/tweets.json")
    Call<List<Object>> searchTweets(@Query("q") String query);
}
