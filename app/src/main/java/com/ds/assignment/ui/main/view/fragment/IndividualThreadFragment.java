package com.ds.assignment.ui.main.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ds.assignment.R;
import com.ds.assignment.data.model.MessageModel;
import com.ds.assignment.databinding.FragmentIndividualThreadBinding;
import com.ds.assignment.ui.base.BaseFragment;
import com.ds.assignment.ui.main.adapter.IndividualMessagesAdapter;
import com.ds.assignment.ui.main.viewmodel.CustomerServiceViewModel;
import com.ds.assignment.utils.LoaderState;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

//@AndroidEntryPoint
public class IndividualThreadFragment extends BaseFragment {

    public static final String ARG_THREAD_ID_PARAM = "thread_Id";

    private CustomerServiceViewModel mCustomerServiceViewModel;
    private int threadId;
    private FragmentIndividualThreadBinding fragmentIndividualThreadBinding;
    private IndividualMessagesAdapter individualMessagesAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerServiceViewModel = ViewModelProviders.of(getActivity()).get(CustomerServiceViewModel.class);
        if (getArguments() != null) {
            threadId = getArguments().getInt(ARG_THREAD_ID_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentIndividualThreadBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_individual_thread, container, false);
        return fragmentIndividualThreadBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomerServiceViewModel.getPageTitleLivedata().setValue(getResources().getString(R.string.message_page) + " " + threadId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentIndividualThreadBinding.rvMessageList.setLayoutManager(new LinearLayoutManager(mContext));
        individualMessagesAdapter = new IndividualMessagesAdapter(mContext, null);
        fragmentIndividualThreadBinding.rvMessageList.setLayoutManager(new LinearLayoutManager(mContext));
        fragmentIndividualThreadBinding.rvMessageList.setHasFixedSize(true);
        fragmentIndividualThreadBinding.rvMessageList.setAdapter(individualMessagesAdapter);
        mCustomerServiceViewModel.getMessgaeList(threadId);

        mCustomerServiceViewModel.getPostMessageApiLoaderStateLivedata().observe(getViewLifecycleOwner(), new Observer<LoaderState>() {
            @Override
            public void onChanged(LoaderState loaderState) {
                if (loaderState == LoaderState.LOADING) {
                    showProgressDialog(R.string.Posting_message);
                } else if (loaderState == LoaderState.LOADING_FINISHED) {
                    dismissProgressDialog();
                } else {
                    dismissProgressDialog();
                }
            }
        });

        mCustomerServiceViewModel.getSelectedThreadMessageList().observe(getViewLifecycleOwner(), new Observer<List<MessageModel>>() {
            @Override
            public void onChanged(List<MessageModel> list) {
                individualMessagesAdapter.setMessageList(list);
                individualMessagesAdapter.notifyDataSetChanged();
                fragmentIndividualThreadBinding.etMessage.setText("");
                mCustomerServiceViewModel.hideKeyboard(mContext,fragmentIndividualThreadBinding.etMessage);
            }
        });
        fragmentIndividualThreadBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(fragmentIndividualThreadBinding.etMessage.getText())) {
                    String body = fragmentIndividualThreadBinding.etMessage.getText().toString();
                    mCustomerServiceViewModel.postMessage(mContext,body,threadId);
                }
            }
        });
    }
}