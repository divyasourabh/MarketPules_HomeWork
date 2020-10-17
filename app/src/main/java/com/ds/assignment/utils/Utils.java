package com.ds.assignment.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.ds.assignment.R;

public class Utils {

    public static ProgressDialog getLoadingDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog));
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;

    }
}
