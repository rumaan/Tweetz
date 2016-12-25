package com.rahulrv.tweetz.api;

import com.rahulrv.tweetz.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor for Retrofit to add auth key to header
 */

public class RetrofitInterceptor implements Interceptor {
    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Authorization", MyApplication.token)
                .build();
        return chain.proceed(request);
    }
}
