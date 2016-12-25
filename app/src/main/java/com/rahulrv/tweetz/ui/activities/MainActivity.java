package com.rahulrv.tweetz.ui.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.R;
import com.rahulrv.tweetz.api.TwitterApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

/**
 * Main launcher activity of the app.
 */
public class MainActivity extends Activity {

    @Inject TwitterApi twitterApi;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        ((MyApplication) getApplication()).getComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        twitterApi.searchTweets("Main Applicaatoin").subscribeOn(Schedulers.computation()).subscribe(searchResponse -> {
            Log.d("ss", "dd");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                View searchMenuView = toolbar.findViewById(R.id.menu_search);
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, searchMenuView,
                        getString(R.string.transition_search_back)).toBundle();
                startActivityForResult(new Intent(this, SearchActivity.class), 0, options);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
