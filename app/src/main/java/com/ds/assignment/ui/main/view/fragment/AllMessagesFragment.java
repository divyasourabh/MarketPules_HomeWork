package com.ds.assignment.ui.main.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ds.assignment.R;
import com.ds.assignment.databinding.FragmentAllMessagesBinding;
import com.ds.assignment.ui.base.BaseFragment;
import com.ds.assignment.ui.main.adapter.AllMessageListAdapter;
import com.ds.assignment.ui.main.adapter.ThreadClickListener;
import com.ds.assignment.ui.main.viewmodel.CustomerServiceViewModel;
import com.ds.assignment.utils.LoaderState;

public class AllMessagesFragment extends BaseFragment {

    private Context mContext;
    private CustomerServiceViewModel mCustomerServiceViewModel;
    private FragmentAllMessagesBinding fragmentAllMessagesBinding;
    private AllMessageListAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerServiceViewModel = ViewModelProviders.of(getActivity()).get(CustomerServiceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAllMessagesBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_all_messages, container, false);

        return fragmentAllMessagesBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomerServiceViewModel.getPageTitleLivedata().setValue(getResources().getString(R.string.list_page));

        mCustomerServiceViewModel.getAllMessageApiLoaderStateLivedata().observe(getViewLifecycleOwner(), new Observer<LoaderState>() {
            @Override
            public void onChanged(LoaderState loaderState) {
                if (loaderState == LoaderState.LOADING) {
                    showProgressDialog(R.string.fetching_message);
                } else if (loaderState == LoaderState.LOADING_FINISHED) {
                    dismissProgressDialog();
                    updateMessageList();
                } else {
                    dismissProgressDialog();
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCustomerServiceViewModel.getAllMessage(mContext);
        fragmentAllMessagesBinding.rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new AllMessageListAdapter(mContext, mCustomerServiceViewModel.consolidatedList);
        fragmentAllMessagesBinding.rvList.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        fragmentAllMessagesBinding.rvList.setHasFixedSize(true);
        fragmentAllMessagesBinding.rvList.addItemDecoration(dividerItemDecoration);
        fragmentAllMessagesBinding.rvList.setAdapter(adapter);
        adapter.setThreadClickListener(new ThreadClickListener() {
            @Override
            public void onThreadClick(int threadId) {
                mCustomerServiceViewModel.openIndividualThreadFragment(mContext,threadId);
            }
        });
    }

    private void updateMessageList(){
        mCustomerServiceViewModel.groupDataIntoHashMap();
        adapter.setList(mCustomerServiceViewModel.consolidatedList);
        adapter.notifyDataSetChanged();
    }
}