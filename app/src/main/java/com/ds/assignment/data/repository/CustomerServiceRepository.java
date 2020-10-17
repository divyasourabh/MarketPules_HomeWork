package com.ds.assignment.data.repository;

import android.content.Context;

import com.ds.assignment.data.api.RetrofitAPIService;
import com.ds.assignment.data.api.RetrofitClient;
import com.ds.assignment.data.model.MessageModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CustomerServiceRepository {

    public Single<List<MessageModel>> getMessages (Context context) {
        RetrofitAPIService retrofitAPIService = RetrofitClient.getRetrofitInstance(context).create(RetrofitAPIService.class);
        return retrofitAPIService.getAllMessages()
                .flatMap(messageList -> {
                    return Single.just(Objects.requireNonNull(messageList));
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<MessageModel> postMessages (Context context, Map<String, String> messageParams) {
        RetrofitAPIService retrofitAPIService = RetrofitClient.getRetrofitInstance(context).create(RetrofitAPIService.class);
        return retrofitAPIService.postMessage(messageParams)
                .flatMap(messageList -> {
                    return Single.just(Objects.requireNonNull(messageList));
                })
                .subscribeOn(Schedulers.io());
    }
}
