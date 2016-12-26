package com.rahulrv.tweetz.ui.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.rahulrv.tweetz.viewmodel.BaseViewModel;
import com.rahulrv.tweetz.viewmodel.IView;

/**
 *
 *
 */

public class BaseActivity<B extends ViewDataBinding, T extends BaseViewModel> extends Activity implements IView {

    protected T viewModel;
    B binding;

    @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (viewModel == null) {
            throw new IllegalStateException("viewModel must not be null and should be injected via activityComponent().inject(this)");
        }
    }

    protected void bindView(int layout) {
        binding = DataBindingUtil.setContentView(this, layout);
    }

    @Override protected void onStop() {
        super.onStop();
        viewModel.detach();
    }

    @Override public void error() {

    }
}
