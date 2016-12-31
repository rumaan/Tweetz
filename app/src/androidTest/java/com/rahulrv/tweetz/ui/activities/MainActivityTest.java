package com.rahulrv.tweetz.ui.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.rahulrv.tweetz.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;

/**
 *
 *
 */
public class MainActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> rule = new ActivityTestRule<>(SplashActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        rule.launchActivity(null);
    }

    @Test
    public void testLaunch() {
        Espresso.onView(ViewMatchers.withId(R.id.trends)).perform(RecyclerViewActions.scrollToPosition(2));
        Espresso.onView(ViewMatchers.withText(R.string.app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(click());
    }

}
