package com.rahulrv.tweetz;

import com.rahulrv.tweetz.api.TwitterApi;
import com.rahulrv.tweetz.model.trends.TrendsItem;
import com.rahulrv.tweetz.viewmodel.MainActivityView;
import com.rahulrv.tweetz.viewmodel.MainActivityViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    @Mock MainActivityView mainActivityView;
    @Mock TwitterApi twitterApi;

    MainActivityViewModel mainActivityViewModel;

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        mainActivityViewModel = new MainActivityViewModel(twitterApi);
    }

    @Test public void loadTrends() {
        List<TrendsItem> countryList = new ArrayList<>();

        doReturn(Observable.just(countryList)).when(twitterApi).getTrends("sss");

        mainActivityViewModel.fetchTrends("sss");
        verify(mainActivityView,times(1)).load(countryList);
    }

    @Test public void error_loadTrends() {
        doReturn(Observable.error(new RuntimeException("Error getting trends"))).when(twitterApi).getTrends("sss");
        mainActivityViewModel.fetchTrends("sss");
        verify(mainActivityView,times(1)).error(new Throwable());
    }
}
