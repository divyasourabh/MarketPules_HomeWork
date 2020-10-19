package com.ds.assignment.ui.main.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ds.assignment.data.model.LoginParams
import com.ds.assignment.data.repository.LoginRepository
import com.ds.assignment.ui.main.view.activities.CustomerServiceActivity
import com.ds.assignment.utils.LoaderState
import com.ds.assignment.utils.NetworkHelper
import com.ds.assignment.utils.SharedPreferencesUtils

class LoginCSViewModel  @ViewModelInject constructor(private val loginRepository: LoginRepository?, private val networkHelper: NetworkHelper) : ViewModel() {

    private val mAPILoaderStateLivedata: MutableLiveData<LoaderState>? = loginRepository?.apiLoaderStateLivedata

    val aPILoaderStateLivedata: LiveData<LoaderState>?
        get() = mAPILoaderStateLivedata

    fun login(context: Context?, loginParams: LoginParams?) {
        if (networkHelper.isNetworkConnected()) {
            loginRepository!!.loginAPI(context, loginParams)
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateAuthToken(context: Context?) {
        if (loginRepository != null && loginRepository.loginModel != null) {
            SharedPreferencesUtils.putToken(context, loginRepository.loginModel.authToken)
        }
    }

    fun startCustomerServiceActivity(context: Context) {
        val intent = Intent(context, CustomerServiceActivity::class.java)
        context.startActivity(intent)
    }
}
