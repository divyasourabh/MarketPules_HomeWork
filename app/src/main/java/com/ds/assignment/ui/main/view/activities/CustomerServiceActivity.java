package com.ds.assignment.ui.main.view.activities;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.ds.assignment.R;
import com.ds.assignment.ui.base.BaseActivity;
import com.ds.assignment.ui.main.view.fragment.AllMessagesFragmentDirections;
import com.ds.assignment.ui.main.viewmodel.CustomerServiceViewModel;

import dagger.hilt.android.AndroidEntryPoint;

//@AndroidEntryPoint
public class CustomerServiceActivity extends BaseActivity {

    private CustomerServiceViewModel mCustomerServiceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerServiceViewModel = ViewModelProviders.of(CustomerServiceActivity.this).get(CustomerServiceViewModel.class);
        mCustomerServiceViewModel.getPageTitleLivedata().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                getSupportActionBar().setTitle(title);
            }
        });

        setContentView(R.layout.activity_customer_service);
    }

    public void openIndividualFragment (int theadId) {
        NavDirections action = AllMessagesFragmentDirections.actionAllMessagesFragmentToIndividualThreadFragment(theadId);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(action);
    }
}