package com.ds.assignment.ui.main.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ds.assignment.data.model.LoginModel;
import com.ds.assignment.data.model.LoginParams;
import com.ds.assignment.data.repository.LoginRepository;
import com.ds.assignment.ui.main.view.activities.CustomerServiceActivity;
import com.ds.assignment.utils.LoaderState;
import com.ds.assignment.utils.NetworkHelper;
import com.ds.assignment.utils.SharedPreferencesUtils;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;
    private NetworkHelper networkHelper;

    private MutableLiveData<LoaderState> mAPILoaderStateLivedata;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
        mAPILoaderStateLivedata = loginRepository.getAPILoaderStateLivedata();
//        loginModel = loginRepository.getLoginModel();
        networkHelper = new NetworkHelper(getApplication());
    }

    public LiveData<LoaderState> getAPILoaderStateLivedata() {
        return mAPILoaderStateLivedata;
    }

    public void login (Context context, LoginParams loginParams) {
        if (networkHelper.isNetworkConnected()) {
            loginRepository.loginAPI(context,loginParams);
        } else {
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAuthToken (Context context) {
        if (loginRepository != null && loginRepository.getLoginModel() != null) {
            SharedPreferencesUtils.putToken(context,loginRepository.getLoginModel().getAuthToken());
        }
    }

    public void startCustomerServiceActivity(Context context) {
        Intent intent = new Intent(context,CustomerServiceActivity.class);
        context.startActivity(intent);
    }
}
