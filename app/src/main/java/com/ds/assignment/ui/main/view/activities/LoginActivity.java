package com.ds.assignment.ui.main.view.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.ds.assignment.R;
import com.ds.assignment.databinding.ActivityLoginBinding;
import com.ds.assignment.ui.base.BaseActivity;
import com.ds.assignment.ui.main.view.fragment.LoginCSFragment;
import com.ds.assignment.ui.main.view.fragment.LoginFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        attachFragment(new LoginCSFragment(),activityLoginBinding.loginContainer.getId(),null);
    }
}