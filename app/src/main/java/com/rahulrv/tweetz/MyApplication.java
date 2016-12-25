package com.rahulrv.tweetz;

import android.app.Application;
import android.util.Base64;

import com.rahulrv.tweetz.di.component.AppComponent;
import com.rahulrv.tweetz.di.component.DaggerAppComponent;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 *
 */
public class MyApplication extends Application {

    private AppComponent component;
    public static String token = "";

    @Override public void onCreate() {
        super.onCreate();
        try {
            getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        component = DaggerAppComponent.create();
    }

    public AppComponent getComponent() {
        return component;
    }

    public void getToken() throws IOException {
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
                .map(new Function<Call, String>() {
                    @Override public String apply(Call call) throws Exception {
                        String str = call.execute().body().string();
                        JSONObject jsonObject = new JSONObject(str);
                        return "Bearer " + jsonObject.getString("access_token");
                    }
                })
                .subscribe(new Consumer<String>() {
            @Override public void accept(String s) throws Exception {
                token = s;
            }
        });

    }
}
