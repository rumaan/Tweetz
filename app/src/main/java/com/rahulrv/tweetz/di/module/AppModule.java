package com.rahulrv.tweetz.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rahulrv.tweetz.BuildConfig;
import com.rahulrv.tweetz.api.RetrofitInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new RetrofitInterceptor()).build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.END_POINT)
                .client(okHttpClient)
                .build();
    }
}
