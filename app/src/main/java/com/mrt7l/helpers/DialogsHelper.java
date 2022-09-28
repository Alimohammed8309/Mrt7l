package com.mrt7l.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.mrt7l.R;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.MessegeExceededBinding;
import com.mrt7l.databinding.ProgressDialogBinding;
import com.mrt7l.databinding.RegisterErrorDialogBinding;
import com.mrt7l.ui.activity.RegisterActivity;
import com.mrt7l.ui.activity.SignInActivity;
import com.mrt7l.ui.fragment.about.AboutAppResponse;

import java.net.URLEncoder;
import java.util.ArrayList;


public class DialogsHelper {
    public class ErrorDialog extends AlertDialog {
        private int layout;
        private String message;
         protected ErrorDialog(@NonNull Context context, int themid, int layoutId, String message) {
            super(context,themid);
            layout = layoutId;
            this.message = message;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             setContentView(layout);
            setCancelable(false);
            TextView content = findViewById(R.id.content);
            if (content != null)
            content.setText(message);
            TextView ok = findViewById(R.id.ok_button);
            if (ok != null){
                ok.setOnClickListener(view -> this.cancel());
            }
            show();
        }
    }
    static ProgressDialog progressDialog;
    public static void showSimpleProgressDialog(String message,Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public static void removeSimpleProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }
    public static void ShowToast(String message, Activity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
      public static void showErrorDialog(String message, Activity activity) {
             final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            RegisterErrorDialogBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.register_error_dialog, null, false);
            registerErrorDialogBinding.content.setText(message);
            registerErrorDialogBinding.okButton.setOnClickListener(view -> dialog.cancel());
            dialog.setContentView(registerErrorDialogBinding.getRoot());
            dialog.show();
        }
     static Dialog loginDialog;
    public static void showLoginDialog(String message, Activity activity) {
        if(!activity.isFinishing()) {
            loginDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loginDialog.setCancelable(false);
            LoginerrorBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.loginerror, null, false);
            registerErrorDialogBinding.content.setText(message);
            registerErrorDialogBinding.okButton.setOnClickListener(v -> {
                Intent intent = new Intent(activity, SignInActivity.class);
                activity.startActivity(intent);
                activity.finish();
            });
            registerErrorDialogBinding.cancel.setOnClickListener(view -> loginDialog.cancel());
            loginDialog.setContentView(registerErrorDialogBinding.getRoot());
            loginDialog.show();
        }
    }
    static Dialog suspensionDialog;
    public static void showSuspensionDialog(String url, Activity activity) {
        if(!activity.isFinishing()) {
            suspensionDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            suspensionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            suspensionDialog.setCancelable(false);
            MessegeExceededBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.messege_exceeded, null, false);
//            registerErrorDialogBinding.content.setText(message);
            registerErrorDialogBinding.contactSupportLayout.setOnClickListener(v -> {
                PackageManager packageManager = activity.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
//                     i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
//                    if (i.resolveActivity(packageManager) != null) {
                        activity.startActivity(i);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            registerErrorDialogBinding.close.setOnClickListener(view -> suspensionDialog.cancel());
            suspensionDialog.setContentView(registerErrorDialogBinding.getRoot());
            suspensionDialog.show();
        }
    }
    public static boolean isLoginDialogOnScreen(){
        if (loginDialog != null){
            return loginDialog.isShowing();
        }
        return false;
    }
    static ArrayList<Integer> widgetsAddedToWatcher = new ArrayList<>();
    public static void showTextError(final TextView view, Activity activity ) {
        widgetsAddedToWatcher.add(view.getId());
        view.setBackground(ContextCompat.getDrawable(activity,R.drawable.field_error));
        scrollToView(view);
        if (!widgetsAddedToWatcher.contains(view.getId())) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    view.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_square));

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        view.removeTextChangedListener(this);
                        widgetsAddedToWatcher.remove(view.getId());
                    }
                }
            });
        }
    }
    public static void showTextError(final EditText view, Activity activity ) {
        widgetsAddedToWatcher.add(view.getId());
        view.setBackground(ContextCompat.getDrawable(activity,R.drawable.field_error));
        scrollToView(view);
        if (!widgetsAddedToWatcher.contains(view.getId())) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    view.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_square));

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        view.removeTextChangedListener(this);
                        widgetsAddedToWatcher.remove(view.getId());
                    }
                }
            });
        }
    }



    public static void scrollToView(View view) {
        final Rect rect = new Rect(0, -100, view.getWidth(), view.getHeight());
        view.requestRectangleOnScreen(rect, false);
    }
    public static AlertDialog getAlert(Activity activity, String title, String message, String okButton) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title).setMessage(message);
        dialog.setPositiveButton(okButton, null);
        return dialog.create();
    }


    private static Dialog dialog;
    public static void showProgressDialog(Activity activity, String title) {
         dialog = new Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar);
        ProgressDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.progress_dialog,null,false);
        binding.content.setText(title);
        Glide.with(activity).load(R.raw.ic_logo).into(binding.pbLoading);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void removeProgressDialog(){
        if (dialog != null && dialog.isShowing()){
            dialog.cancel();
        }
    }
    public static void showSpinnerError(RelativeLayout spinner , Activity activity) {
        spinner.setBackground(ContextCompat.getDrawable(activity,R.drawable.field_error));
        scrollToView(spinner);
    }

    public static void showSpinnerError(ConstraintLayout spinner , Activity activity) {
        spinner.setBackground(ContextCompat.getDrawable(activity,R.drawable.field_error));
        scrollToView(spinner);
    }

//    public static Snackbar getSnackBar(View view, String message) {
//        return Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

//    public static AlertDialog getSingleChoiceDialog(Activity activity, String title, final ISinglePickDialogDelegate mDelegate, int sourceArray, int selectedItemPosition, final int code) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle(title)
//                .setSingleChoiceItems(sourceArray, selectedItemPosition, (dialog, which) -> {
//                    mDelegate.Picked(code,which);
//                    dialog.cancel();
//                });
//
//        return builder.create();
//    }

    public static void disable(ViewGroup layout, boolean enabled) {
        layout.setEnabled(enabled);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child,enabled);
            } else {
                child.setEnabled(enabled);
            }
        }
    }
}
