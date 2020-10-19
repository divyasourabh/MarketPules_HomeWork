package com.ds.assignment.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.ds.assignment.utils.Utils;

import dagger.hilt.android.AndroidEntryPoint;

public class BaseFragment extends Fragment {

    protected Context mContext;
    private ProgressDialog loadingDialog;

    public void showProgressDialog(int stringId) {
        try {
            loadingDialog = Utils.getLoadingDialog(mContext, mContext.getString(stringId));
            loadingDialog.show();
        } catch (Exception e) {
            //Silent catch
        }
    }

    public void dismissProgressDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            //Silent catch
        }
    }
}