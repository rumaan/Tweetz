package com.rahulrv.tweetz.ui.activities;

import android.app.SharedElementCallback;
import android.graphics.Point;
import android.os.Bundle;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.rahulrv.tweetz.MyApplication;
import com.rahulrv.tweetz.R;
import com.rahulrv.tweetz.api.TwitterApi;
import com.rahulrv.tweetz.databinding.ActivitySearchBinding;
import com.rahulrv.tweetz.ui.transitions.CircularReveal;
import com.rahulrv.tweetz.utils.ImeUtils;
import com.rahulrv.tweetz.utils.TransitionUtils;
import com.rahulrv.tweetz.viewmodel.SearchActivityViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchActivityViewModel> {

    @Inject TwitterApi twitterApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView(R.layout.activity_search);
        setupTransitions();
        binding.searchback.setOnClickListener(view -> finishAfterTransition());
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String s) {
                twitterApi.searchTweets(s)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(searchResponse -> {
                            Log.d("ss", s);
                        });
                return true;
            }

            @Override public boolean onQueryTextChange(String s) {
                if (s.length() < 3) {
                    return false;
                }
                twitterApi.searchTweets(s)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(searchResponse -> {
                            Log.d("ss", s);
                        });
                return true;
            }
        });
        MyApplication.getComponent().inject(this);
        viewModel = new SearchActivityViewModel();
    }

    @Override protected void onStart() {
        super.onStart();
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
        binding.searchView.requestFocus();
        ImeUtils.showIme(binding.searchView);
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
