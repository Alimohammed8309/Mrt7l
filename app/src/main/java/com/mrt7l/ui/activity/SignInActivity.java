package com.mrt7l.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.ConfirmPhoneDialogBinding;
import com.mrt7l.databinding.ForgetPasswordDialogBinding;
import com.mrt7l.databinding.ResetPasswordDialogBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import com.onesignal.OneSignal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;

public class SignInActivity extends BaseActivity implements View.OnClickListener, LoginInterface {
    /*variable declaration*/
    private TextView mBtnContinue;
    private EditText mEdMobileNumber,passwordEt;
     private SignInViewModel viewModel;
    private TextView regNow,forgetPassword,skipLogin;
    private LinearLayout wholeview;
    private CheckBox rememberMe;
    private String dd = null;
    private ProgressBar loginProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        viewModel.init(this,this);
        initLayouts();
        initializeListeners();
        if (getIntent() != null){
            if(getIntent().getStringExtra("phone") != null){
                String phone = getIntent().getStringExtra("phone");
                mEdMobileNumber.setText(phone);
                passwordEt.requestFocus();
            }
        }
    }

    /* init layout */
    private void initLayouts() {
        mEdMobileNumber = findViewById(R.id.edMobileNumber);
        mBtnContinue = findViewById(R.id.btnContinue);
         loginProgress = findViewById(R.id.loginProgress);
        wholeview = findViewById(R.id.wholeView);
        passwordEt = findViewById(R.id.passwordEt);
        regNow = findViewById(R.id.regNow);
        rememberMe = findViewById(R.id.rememberMe);
        forgetPassword = findViewById(R.id.forgetPassword);
        skipLogin = findViewById(R.id.skipLogin);
    }

    /* initialize listener */

    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners() {

        mBtnContinue.setOnClickListener(this);
//        mIvFacebook.setOnClickListener(this);
//        mIvGoogle.setOnClickListener(this);
        mBtnContinue.setStateListAnimator(null);
        regNow.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        skipLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
            finish();
        });
        mEdMobileNumber.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //                       if (validate()) {
                //                           viewModel.login(mEdMobileNumber.getText().toString(),passwordEt.getText().toString());
                //                    startActivity(VerificationActivity.class);
                //                       }
                return actionId == EditorInfo.IME_ACTION_DONE;
            }

        });
    }

    /* validation */
    private boolean validate() {
        boolean flag = true;
        if (TextUtils.isEmpty(mEdMobileNumber.getText())) {
            flag = false;
            showToast(getString(R.string.msg_mobile_number));
        } else  if (TextUtils.isEmpty(passwordEt.getText())) {
            flag = false;
            showToast(getString(R.string.passwore_required));
        }
        return flag;
    }

    /* onClick listener */
    @Override
    public void onClick(View v) {
//         if (v == mIvFacebook) {
//
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(getString(R.string.text_facebooklink)));
//            startActivity(i);
//
//        } else if (v == mIvGoogle) {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(getString(R.string.text_googlelink)));
//            startActivity(i);
//        } else
            if (v == regNow){
             Intent in = new Intent(this,PhoneActivity.class);
             startActivity(in);
             finish();
         } else if (v == forgetPassword){
             forgetPasswordDialog(this);
         } else if (v == mBtnContinue){

                if (validate()){
                 loginProgress.setVisibility(View.VISIBLE);
                 mBtnContinue.setVisibility(View.GONE);
                 DialogsHelper.disable( wholeview,false);

//                 if (mEdMobileNumber.getText().toString().length() > 9){
                     viewModel.login( mEdMobileNumber.getText().toString(),
                             passwordEt.getText().toString(),new PreferenceHelper(this).getDeviceToken());
//                  }
//                 else {
//                     viewModel.login("0"+ mEdMobileNumber.getText().toString()
//                             ,passwordEt.getText().toString(),new PreferenceHelper(this).getDeviceToken());
//                 }

             }

            }
    }
    private EditText codeView;
    private CountryCodePicker codePicker;
    Dialog forgetPasswordDialog,resetPasswordDialog,cofirmCodeDialog;
    private void forgetPasswordDialog( Activity activity) {
        forgetPasswordDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        forgetPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        forgetPasswordDialog.setCancelable(true);
        ForgetPasswordDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.forget_password_dialog, null, false);
        codePicker = registerErrorDialogBinding.codePicker;
        registerErrorDialogBinding.send.setOnClickListener(view -> {
            if (!registerErrorDialogBinding.phoneEt.getText().toString().isEmpty()){
                phoneNumber = registerErrorDialogBinding.phoneEt.getText().toString();
                  DialogsHelper.showProgressDialog(SignInActivity.this,getString(R.string.loading_data));
                String code = codePicker.getSelectedCountryCode();
                String phone =  phoneNumber;
                phoneNumber = phone;
                isForgetPassRequest = true;
                viewModel.forgetPassword(phone);
            } else {
               DialogsHelper.showTextError(registerErrorDialogBinding.phoneEt,this);
            }
        });
        forgetPasswordDialog.setContentView(registerErrorDialogBinding.getRoot());
        forgetPasswordDialog.show();
    }
    boolean isForgetPassRequest = false,isSendingMessage = false;
    Map<String ,String> tokenMap = new HashMap<>();
    private void resetPasswordDialog( Activity activity,String code) {
        resetPasswordDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        resetPasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        resetPasswordDialog.setCancelable(true);
        ResetPasswordDialogBinding resetPasswordDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.reset_password_dialog, null, false);
        codeView = resetPasswordDialogBinding.codeEt;
                resetPasswordDialogBinding.send.setOnClickListener(view -> {
              if (resetPasswordDialogBinding.passwordEt.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(resetPasswordDialogBinding.passwordEt, this);
            } else   if (resetPasswordDialogBinding.rePassword.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(resetPasswordDialogBinding.rePassword, this);
            } else{
//                Date date = new Date(System.currentTimeMillis() + 300000);
//                         Map<String,Object> header = new HashMap<>();
//                        header.put("alg","HS256");
//                        header.put("type","JWT");
//                     byte[] secretKey = ("mrt7al.mob." + phoneNumber + "mrt7al.mob.").getBytes(StandardCharsets.UTF_8);
//                    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//                     Key key = new SecretKeySpec(secretKey, signatureAlgorithm.getJcaName());
//                     String compactJws = Jwts.builder().setHeader(header).claim("mob",phoneNumber).claim("exp",
//                            String.valueOf(System.currentTimeMillis() + 300000)).setExpiration(date)
//                            .signWith(key,SignatureAlgorithm.HS256)
//                            .compact() ;
//
//                Log.v("vToken",compactJws);
                DialogsHelper.showProgressDialog(SignInActivity.this,getString(R.string.loading_data));
                viewModel.resetPassword( code,resetPasswordDialogBinding.passwordEt.getText().toString(),
                        resetPasswordDialogBinding.rePassword.getText().toString()
                        );
            }
        });

        resetPasswordDialog.setContentView(resetPasswordDialogBinding.getRoot());
        resetPasswordDialog.show();
    }

    private void confirmCodeDialog( Activity activity) {
        cofirmCodeDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        cofirmCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cofirmCodeDialog.setCancelable(true);
        ConfirmPhoneDialogBinding resetPasswordDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.confirm_phone_dialog, null, false);
//        codeView = resetPasswordDialogBinding.codeEt;
        resetPasswordDialogBinding.btnContinue.setOnClickListener(view -> {
            if (Objects.requireNonNull(resetPasswordDialogBinding.txtPinEntry.getText()).toString().isEmpty()) {
                Toast.makeText(activity, getString(R.string.please_enter_code), Toast.LENGTH_SHORT).show();
            } else{
                DialogsHelper.showProgressDialog(SignInActivity.this,getString(R.string.loading_data));
                viewModel.confirmCode( resetPasswordDialogBinding.txtPinEntry.getText().toString()
                );
            }
        });

        cofirmCodeDialog.setContentView(resetPasswordDialogBinding.getRoot());
        cofirmCodeDialog.show();
    }


    String  phoneNumber, code;
//    FirebaseAuth mAuth;
//    private void sendVerificationCode(String mobile) {
//         mAuth = FirebaseAuth.getInstance();
//          PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(mobile)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            //Getting the code sent by SMS
//            code = phoneAuthCredential.getSmsCode();
//            Log.v("code" , "code"+ code);
//            //sometime the code is not detected automatically
//            //in this case the code will be null
//            //so user has to manually enter the code
//            if (code != null) {
//               codeView.setText(code);
//                //verifying the code
////                getCode(phone);
//            }
//        }
//
//        @Override
//        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
//            super.onCodeAutoRetrievalTimeOut(s);
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            DialogsHelper.removeProgressDialog();
//            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//             DialogsHelper.removeProgressDialog();
//            forgetPasswordDialog.cancel();
////            resetPasswordDialog(SignInActivity.this);
//            Intent intent = new Intent(SignInActivity.this,ResetPasswordActivity.class);
//            startActivity(intent);
//            Toast.makeText(SignInActivity.this, getString(R.string.code_sent), Toast.LENGTH_LONG).show();
//
//        }
//    };

    @Override
    public void onResponse(boolean isSuccess, LoginResponse loginResponse) {

        if (isSuccess){
            if (loginResponse.getMrt7al().getData().getUsername() != null){
//                if (rememberMe.isChecked()) {
                    new PreferenceHelper(this).setUSERID(loginResponse.getMrt7al().getData().getId());
//                      }
                loginResponse.getMrt7al().getData().setPassword(passwordEt.getText().toString());
                Gson gson = new Gson();
                String json = gson.toJson(loginResponse);
                new PreferenceHelper(this).setUSERNAME(json);
                new PreferenceHelper(this).setTOKEN(loginResponse.getMrt7al().getData().getToken());

                Intent intent = new Intent(this, DashboardActivity.class);
                    startActivity(intent);
                     finish();

            } else {
//                     NavController navController = Navigation.findNavController(getActivity(), R.id.reg_host_fragment);
//                    navController.navigate(R.id.action_confirmFragment_to_registerFragment);
         Toast.makeText(this, loginResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        loginProgress.setVisibility(View.GONE);
        mBtnContinue.setVisibility(View.VISIBLE);

    }
    private ErrorResponse errorResponse;

    @Override
    public void handleError(Throwable t) {
        DialogsHelper.removeProgressDialog();
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                ResponseBody body = ((HttpException) t).response().errorBody();
                Gson gson = new Gson();
                try {
                    assert body != null;
                    errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if (errorResponse.getMrt7al() != null) {
                    Toast.makeText(this, errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                }
            }
        } else
        Toast.makeText(this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();

        loginProgress.setVisibility(View.GONE);
        mBtnContinue.setVisibility(View.VISIBLE);
        DialogsHelper.disable( wholeview,true);


    }

    @Override
    public void onForgetPassword(boolean isSuccess, ForgetPasswordResponse forgetPasswordResponse) {
        isForgetPassRequest = false;
        if (forgetPasswordResponse.getMrt7al().isSuccess()) {
             viewModel.sendMessage(phoneNumber);
        } else {
            DialogsHelper.removeProgressDialog();
            DialogsHelper.showErrorDialog(forgetPasswordResponse.getMrt7al().getMsg(),SignInActivity.this);
        }
    }


    @Override
    public void onMessageSent(boolean isSuccess, SendMessageResponse sendMessageResponse) {
        if(isSuccess){
            DialogsHelper.removeProgressDialog();
            forgetPasswordDialog.cancel();
            confirmCodeDialog(SignInActivity.this);
//            Intent intent = new Intent(SignInActivity.this,ResetPasswordActivity.class);
//            startActivity(intent);
            Toast.makeText(SignInActivity.this, getString(R.string.code_sent), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SignInActivity.this, sendMessageResponse.getMrt7al().getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCodeConfirmed(boolean isSuccess, ConfirmCodeResponse confirmPasswordResponse,
                                String smsCode) {
        DialogsHelper.removeProgressDialog();
        if (isSuccess) {
            cofirmCodeDialog.cancel();
            resetPasswordDialog(SignInActivity.this,smsCode);
        } else {
            Toast.makeText(this, confirmPasswordResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPasswordRestored(boolean isSuccess, ChangePasswordResponse confirmPasswordResponse) {
        if(isSuccess){
            Toast.makeText(this, confirmPasswordResponse.getMrt7al().getMsg(), Toast.LENGTH_LONG).show();
            DialogsHelper.removeProgressDialog();
            resetPasswordDialog.cancel();
        } else {
            DialogsHelper.removeProgressDialog();
            Toast.makeText(this, confirmPasswordResponse.getMrt7al().getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
