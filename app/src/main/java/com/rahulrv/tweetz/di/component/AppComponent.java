package com.rahulrv.tweetz.di.component;

import com.rahulrv.tweetz.di.module.AppModule;
import com.rahulrv.tweetz.di.module.TwitterModule;
import com.rahulrv.tweetz.ui.activities.MainActivity;
import com.rahulrv.tweetz.ui.activities.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *
 */
@Singleton
@Component(modules = {AppModule.class, TwitterModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(SearchActivity activity);
}
