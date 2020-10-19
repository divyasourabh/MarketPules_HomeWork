package com.ds.assignment.data.repository

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.ds.assignment.utils.LoaderState
import com.ds.assignment.data.model.LoginModel
import com.ds.assignment.data.api.RetrofitAPIService
import com.ds.assignment.data.model.LoginParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class LoginRepository @Inject constructor() {

    val aPILoaderStateLivedata: MutableLiveData<LoaderState>

    init {
        aPILoaderStateLivedata = MutableLiveData()
    }

    var loginModel: LoginModel? = null

    @Inject
    lateinit var retrofitAPIService: RetrofitAPIService

    fun loginAPI(loginParams: LoginParams) {
        aPILoaderStateLivedata.value = LoaderState.LOADING
        val map = HashMap<String, String?>()
        map["username"] = loginParams.userName
        map["password"] = loginParams.password
        val call = retrofitAPIService.login(map)
        call.enqueue(object : Callback<LoginModel?> {
            override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
                if (response != null) {
                    loginModel = response.body()
                    aPILoaderStateLivedata.setValue(
                        LoaderState.LOADING_FINISHED.setLoaderState(
                            response.code(),
                            response.message()
                        )
                    )
                } else {
                    aPILoaderStateLivedata.setValue(LoaderState.LOADING_FAILED)
                }
            }

            override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
                aPILoaderStateLivedata.value = LoaderState.LOADING_FAILED
            }
        })
    }
}