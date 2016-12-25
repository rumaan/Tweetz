package com.rahulrv.tweetz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.rahulrv.tweetz.BuildConfig;
import com.rahulrv.tweetz.MyApplication;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToken();
    }

    public void getToken() {
        OkHttpClient client = new OkHttpClient();
        String bearerToken = BuildConfig.CONSUMER_KEY +
                ":" + BuildConfig.CONSUMER_SECRET;

        String base64BearerToken = "Basic " + Base64.encodeToString(bearerToken.getBytes(), Base64.NO_WRAP);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "grant_type=client_credentials");

        final Request request = new Request.Builder()
                .url(BuildConfig.AUTH_END_POINT)
                .post(requestBody)
                .header("Authorization", base64BearerToken)
                .header("Content-Encoding", "gzip")
                .header("User-Agent", "My Twitter App v1.0.23")
                .header("Content-type", "application/x-www-form-urlencoded;charset=UTF-8")
                .build();

        Observable.just(client.newCall(request))
                .subscribeOn(Schedulers.computation())
                .map(call -> {
                    String str = call.execute().body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    return "Bearer " + jsonObject.getString("access_token");
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    MyApplication.token = s;
                    startActivity(new Intent(this, MainActivity.class));
                });

    }

    @Override protected void onPause() {
        super.onPause();
        finish();
    }
}
