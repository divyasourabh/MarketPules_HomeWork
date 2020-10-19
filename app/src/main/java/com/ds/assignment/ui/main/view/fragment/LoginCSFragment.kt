package com.ds.assignment.ui.main.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ds.assignment.R
import com.ds.assignment.data.model.LoginParams
import com.ds.assignment.databinding.FragmentLoginBinding
import com.ds.assignment.ui.base.BaseFragment
import com.ds.assignment.ui.main.viewmodel.LoginCSViewModel
import com.ds.assignment.utils.LoaderState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginCSFragment : BaseFragment(), LoginContract {

    private val mLoginViewModel: LoginCSViewModel by viewModels()

    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLoginBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_login, container, false)
        return fragmentLoginBinding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleLoginOnClickEvent()
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view1: View? ->
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                    mLoginViewModel.login(mContext, LoginParams("divyasourabh@gmail.com", "moc.liamg@hbaruosayvid"))
                } else {
                    Toast.makeText(context, "UserName or Password should be Not Empty", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        mLoginViewModel.aPILoaderStateLivedata?.observe(
            viewLifecycleOwner,
            Observer { loaderState ->
                if (loaderState == LoaderState.LOADING) {
                    showProgressDialog(R.string.logging)
                } else if (loaderState == LoaderState.LOADING_FINISHED) {
                    dismissProgressDialog()
                    if (loaderState.code != 200) {
                        fragmentLoginBinding.tvError.text = loaderState.message
                    } else {
                        mLoginViewModel.startCustomerServiceActivity(mContext)
                        mLoginViewModel.updateAuthToken(mContext)
                        if (activity != null) {
                            requireActivity().finish()
                        }
                    }
                } else {
                    dismissProgressDialog()
                    fragmentLoginBinding.tvError.text = "Try Again!!!"
                }
            })
    }

    override fun getUserName(): String? {
        return if (fragmentLoginBinding.etUsername.text != null) fragmentLoginBinding.etUsername.text?.toString() else ""
    }

    override fun getPassword(): String? {
        return if (fragmentLoginBinding.etPassword.text != null) fragmentLoginBinding.etPassword.text?.toString() else ""
    }

    private fun handleLoginOnClickEvent(): Observable<View> {
        return Observable.create { emitter: ObservableEmitter<View> ->
            fragmentLoginBinding.btnLogin.setOnClickListener { value: View ->
                emitter.onNext(
                    value
                )
            }
        }
    }
}