package com.ds.assignment.ui.base;

import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dagger.hilt.android.AndroidEntryPoint;

//@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    public void attachFragment(Fragment fragment, int containerId, String addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment);
        if (!TextUtils.isEmpty(addToBackStack)) {
            ft.addToBackStack(addToBackStack);
        }
        ft.commitAllowingStateLoss();
    }
}
