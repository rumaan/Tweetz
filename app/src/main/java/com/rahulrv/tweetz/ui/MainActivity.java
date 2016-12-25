package com.rahulrv.tweetz.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.R;
import com.rahulrv.tweetz.api.TwitterApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

/**
 * Main launcher activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    @Inject TwitterApi twitterApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        ((MyApplication) getApplication()).getComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        twitterApi.searchTweets("Main Applicaatoin").subscribeOn(Schedulers.computation()).subscribe(searchResponse -> {
            Log.d("ss","dd");
        });
    }
}
