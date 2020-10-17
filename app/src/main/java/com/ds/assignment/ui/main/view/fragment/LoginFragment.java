package com.ds.assignment.ui.main.view.fragment;

import android.annotation.SuppressLint;
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

import com.ds.assignment.R;
import com.ds.assignment.data.model.LoginParams;
import com.ds.assignment.databinding.FragmentLoginBinding;
import com.ds.assignment.ui.base.BaseFragment;
import com.ds.assignment.ui.main.viewmodel.LoginViewModel;
import com.ds.assignment.utils.LoaderState;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginFragment extends BaseFragment implements LoginContract {

    private LoginViewModel mLoginViewModel;
    FragmentLoginBinding fragmentLoginBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentLoginBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_login, container, false);
        fragmentLoginBinding.setViewModel(mLoginViewModel);
        return fragmentLoginBinding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handleLoginOnClickEvent()
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view1 -> {
                if (!TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPassword())) {
                    mLoginViewModel.login(mContext, new LoginParams("divyasourabh@gmail.com","moc.liamg@hbaruosayvid"));
                }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginViewModel.getAPILoaderStateLivedata().observe(getViewLifecycleOwner(), new Observer<LoaderState>() {
            @Override
            public void onChanged(LoaderState loaderState) {
                if (loaderState == LoaderState.LOADING) {
                    showProgressDialog(R.string.logging);
                } else if (loaderState == LoaderState.LOADING_FINISHED) {
                    dismissProgressDialog();
                    if (loaderState.getCode() != 200) {
                        fragmentLoginBinding.tvError.setText(loaderState.getMessage());
                    } else {
                        mLoginViewModel.startCustomerServiceActivity(mContext);
                        mLoginViewModel.updateAuthToken(mContext);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }
                } else {
                    dismissProgressDialog();
                    fragmentLoginBinding.tvError.setText("Try Again!!!");
                }
            }
        });
    }

    @Override
    public String getUserName() {
        return fragmentLoginBinding.etUsername.getText() != null ? fragmentLoginBinding.etUsername.getText().toString() : null;
    }

    @Override
    public String getPassword() {
        return fragmentLoginBinding.etPassword.getText() != null ? fragmentLoginBinding.etPassword.getText().toString() : null;
    }

    private Observable<View> handleLoginOnClickEvent() {
        return Observable.create(emitter -> fragmentLoginBinding.btnLogin.setOnClickListener(emitter::onNext));
    }
}