package com.ds.assignment.ui.main.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ds.assignment.data.model.MessageModel;
import com.ds.assignment.data.repository.CustomerServiceRepository;
import com.ds.assignment.ui.main.view.activities.CustomerServiceActivity;
import com.ds.assignment.utils.LoaderState;
import com.ds.assignment.utils.MessageComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CustomerServiceViewModel extends AndroidViewModel {
    private MutableLiveData<String> pageTitleLivedata;
    private List<MessageModel> messageList;
    public ConcurrentHashMap<Integer, List<MessageModel>> groupedHashMap;
    public List<MessageModel> consolidatedList;
    private CustomerServiceRepository customerServiceRepository;

    public MutableLiveData<String> getPageTitleLivedata() {
        return pageTitleLivedata;
    }

    public LiveData<List<MessageModel>> getSelectedThreadMessageList() {
        return selectedThreadMessageList;
    }

    private MutableLiveData<List<MessageModel>> selectedThreadMessageList;
    private MutableLiveData<LoaderState> allMessageApiLoaderStateLivedata;
    private MutableLiveData<LoaderState> postMessageApiLoaderStateLivedata;

    public MutableLiveData<LoaderState> getAllMessageApiLoaderStateLivedata() {
        return allMessageApiLoaderStateLivedata;
    }

    public MutableLiveData<LoaderState> getPostMessageApiLoaderStateLivedata() {
        return postMessageApiLoaderStateLivedata;
    }

    public CustomerServiceViewModel(@NonNull Application application) {
        super(application);
        customerServiceRepository = new CustomerServiceRepository();
        allMessageApiLoaderStateLivedata = new MutableLiveData<>();
        postMessageApiLoaderStateLivedata = new MutableLiveData<>();
        selectedThreadMessageList = new MutableLiveData<>();
        pageTitleLivedata = new MutableLiveData<>();
    }

    public void getAllMessage(Context context) {
        customerServiceRepository.getMessages(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MessageModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Test123", "Success");
                        allMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING);
                    }

                    @Override
                    public void onSuccess(List<MessageModel> messageModelList) {
                        Log.d("Test123", "Success");
                        messageList = messageModelList;
                        allMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING_FINISHED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test123", "Error");
                        allMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING_FAILED);
                    }
                });
    }


    public void postMessage(Context context, String body, int threadId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("thread_id", threadId + "");
        map.put("body", body);
        customerServiceRepository.postMessages(context, map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MessageModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Test123", "Success");
                        postMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING);
                    }

                    @Override
                    public void onSuccess(MessageModel message) {
                        Log.d("Test123", "Success");
                        messageList.add(message);
                        setMessgaeList(threadId, message);
                        postMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING_FINISHED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test123", "Error");
                        postMessageApiLoaderStateLivedata.setValue(LoaderState.LOADING_FAILED);
                    }
                });
    }

    public void groupDataIntoHashMap() {
        if (messageList != null) {
            groupedHashMap = new ConcurrentHashMap<>();
            for (MessageModel message : messageList) {
                Integer threadIdkey = message.getThreadId();
                if (groupedHashMap.containsKey(threadIdkey)) {
                    groupedHashMap.get(threadIdkey).add(message);
                } else {
                    List<MessageModel> newThreadList = new ArrayList<>();
                    newThreadList.add(message);
                    groupedHashMap.put(threadIdkey, newThreadList);
                }
            }
        }

        getAllMessagePageList();
    }

    private void getAllMessagePageList() {

        consolidatedList = new ArrayList<>();
        for (Integer threadId : groupedHashMap.keySet()) {
            List<MessageModel> messageList = groupedHashMap.get(threadId);
            Collections.sort(messageList, new MessageComparator());
            MessageModel message = messageList.get(messageList.size() - 1);
            consolidatedList.add(message);

        }
    }

    public void openIndividualThreadFragment(Context context, int threadId) {
        ((CustomerServiceActivity) context).openIndividualFragment(threadId);
    }

    public void getMessgaeList(int threadId) {
        selectedThreadMessageList.setValue(groupedHashMap.get(threadId));
    }

    public void setMessgaeList(int threadId, MessageModel message) {
        if (groupedHashMap.containsKey(threadId)) {
            groupedHashMap.get(threadId).add(message);
        } else {
            List<MessageModel> newThreadList = new ArrayList<>();
            newThreadList.add(message);
            groupedHashMap.put(threadId, newThreadList);
        }
        selectedThreadMessageList.setValue(groupedHashMap.get(threadId));
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}