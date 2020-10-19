package com.ds.assignment.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ds.assignment.data.api.ApiService
import com.ds.assignment.data.api.RetrofitAPIService
import com.ds.assignment.data.api.RetrofitClient
import com.ds.assignment.data.model.MessageModel
import com.ds.assignment.utils.LoaderState
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CustomerService @Inject constructor(private val apiService :ApiService) {

    lateinit var messageList : List<MessageModel>

    val aPILoaderStateLivedata: MutableLiveData<LoaderState>

    init {
        aPILoaderStateLivedata = MutableLiveData()
    }

    fun getMessages(context: Context?) : List<MessageModel> {
        val apiService = RetrofitClient.getRetrofitInstance(context).create(ApiService::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            messageList = apiService.getAllMessages()
        }
        return messageList;
    }
}