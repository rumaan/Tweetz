package com.rahulrv.tweetz.ui.activities;

import android.app.SharedElementCallback;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.R;
import com.rahulrv.tweetz.Utils.ImeUtils;
import com.rahulrv.tweetz.Utils.TransitionUtils;
import com.rahulrv.tweetz.api.TwitterApi;
import com.rahulrv.tweetz.ui.transitions.CircularReveal;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    @Inject TwitterApi twitterApi;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTransitions();
        findViewById(R.id.searchback).setOnClickListener(view -> finishAfterTransition());
        searchView = (SearchView) findViewById(R.id.search_view);
        ((MyApplication) getApplication()).getComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        twitterApi.searchTweets("Main Applicaatoin").subscribeOn(Schedulers.computation()).subscribe(searchResponse -> {
            Log.d("ss", "dd");
        });
    }

    @Override
    protected void onPause() {
        // needed to suppress the default window animation when closing the activity
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        // focus the search view once the enter transition finishes
        searchView.requestFocus();
        ImeUtils.showIme(searchView);
    }

    private void setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(
                    List<String> sharedElementNames,
                    List<View> sharedElements,
                    List<View> sharedElementSnapshots) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    View searchIcon = sharedElements.get(0);
                    if (searchIcon.getId() != R.id.searchback) return;
                    int centerX = (searchIcon.getLeft() + searchIcon.getRight()) / 2;
                    CircularReveal hideResults = (CircularReveal) TransitionUtils.findTransition(
                            (TransitionSet) getWindow().getReturnTransition(),
                            CircularReveal.class, R.id.results_container);
                    if (hideResults != null) {
                        hideResults.setCenter(new Point(centerX, 0));
                    }
                }
            }
        });
    }
}
