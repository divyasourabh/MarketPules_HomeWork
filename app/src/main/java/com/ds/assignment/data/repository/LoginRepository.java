package com.ds.assignment.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ds.assignment.data.api.RetrofitAPIService;
import com.ds.assignment.data.api.RetrofitClient;
import com.ds.assignment.data.model.LoginModel;
import com.ds.assignment.data.model.LoginParams;
import com.ds.assignment.utils.LoaderState;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private MutableLiveData<LoaderState> mAPILoaderStateLivedata;
    private LoginModel loginModel;

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public LoginRepository() {
        this.mAPILoaderStateLivedata = new MutableLiveData<>();
    }

    public MutableLiveData<LoaderState> getAPILoaderStateLivedata() {
        return mAPILoaderStateLivedata;
    }

    public void loginAPI (Context context, LoginParams loginParams) {
        mAPILoaderStateLivedata.setValue(LoaderState.LOADING);
        RetrofitAPIService retrofitAPIService = RetrofitClient.getRetrofitInstance(context).create(RetrofitAPIService.class);
        HashMap<String,String>  map = new HashMap<>();
        map.put("username",loginParams.getUserName());
        map.put("password",loginParams.getPassword());
        Call<LoginModel> call = retrofitAPIService.login(map);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response != null) {
                    loginModel = response.body();
                    mAPILoaderStateLivedata.setValue(LoaderState.LOADING_FINISHED.setLoaderState(response.code(),response.message()));
                } else {
                    mAPILoaderStateLivedata.setValue(LoaderState.LOADING_FAILED);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                mAPILoaderStateLivedata.setValue(LoaderState.LOADING_FAILED);
            }
        });
    }
}
