package com.mrt7l.ui.activity;

import static com.mrt7l.helpers.DialogsHelper.disable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.helpers.CheckEntries;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.VerifyRecapchaProvider;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PhoneActivity extends AppCompatActivity implements PhoneInterface {
    private String  phone,phoneNumber;
    private PinEntryEditText pinEntry;
    private EditText phoneEt;
    private Button btnContinue;
    private LinearLayout llMobile;
    private CountryCodePicker  codePicker;
    private TextView timer;
    private TextView resend,pageTitle;
    private TextView mobileText,skipLogin,btnConfirm;
    private LinearLayout timerLayout,editLayout;
    private PhoneViewModel viewModel;
    private ProgressBar loginProgress;
    private LinearLayout wholeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        phoneEt = findViewById(R.id.edMobileNumber);
        pinEntry = findViewById(R.id.txt_pin_entry);
        pageTitle = findViewById(R.id.pageTitle);
        llMobile = findViewById(R.id.llMobile);
        codePicker = findViewById(R.id.codePicker);
        btnConfirm = findViewById(R.id.btnConfirm);
        editLayout = findViewById(R.id.editLayout);
        TextView editMobile = findViewById(R.id.editBtn);
        skipLogin = findViewById(R.id.skipLogin);
        mobileText= findViewById(R.id.mobileText);
        wholeView = findViewById(R.id.wholeView);
        loginProgress = findViewById(R.id.loginProgress);
        viewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        viewModel.init(this,this);

        editMobile.setOnClickListener(v -> {
            editLayout.setVisibility(View.GONE);
            llMobile.setVisibility(View.VISIBLE);
            pinEntry.setVisibility(View.GONE);
            timerLayout.setVisibility(View.GONE);
            btnContinue.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.GONE);
            skipLogin.setVisibility(View.VISIBLE);
            pageTitle.setText(getString(R.string.enter_phone));
            countDownTimer.cancel();
        });
        btnContinue = findViewById(R.id.btnContinue);
        timer = findViewById(R.id.timer);
        TextView login = findViewById(R.id.regNow);
        login.setOnClickListener(v -> {
            Intent in = new Intent(this,SignInActivity.class);
            startActivity(in);
            finish();
        });
        skipLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
            finish();
        });
        resend = findViewById(R.id.resend);
        timerLayout = findViewById(R.id.timerLayout);
        resend.setOnClickListener(view -> {
            loginProgress.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
            DialogsHelper.disable(wholeView,false);
            if (phoneEt.getText().toString().startsWith("7")){
                phoneNumber = "+967" +   phoneEt.getText().toString();
            } else {
                phoneNumber = "+" + 966 + phoneEt.getText().toString();
            }
            phone = phoneEt.getText().toString();
            loginProgress.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.VISIBLE);
            disable(wholeView,false);
            final int min = 100000;
            final int max = 999999;
            random = new Random().nextInt((max - min) + 1) + min;
            viewModel.sendMessage(phoneEt.getText().toString(),String.valueOf(random));


        });
        btnContinue.setOnClickListener(view -> {
            if (!checkPhone()) {
                Toast.makeText(this, getString(R.string.please_enter_phone), Toast.LENGTH_SHORT).show();
            } else {
                loginProgress.setVisibility(View.VISIBLE);
                btnContinue.setVisibility(View.GONE);
                disable(wholeView,false);
                final int min = 100000;
                final int max = 999999;
                random = new Random().nextInt((max - min) + 1) + min;
                viewModel.sendMessage(phoneEt.getText().toString(),String.valueOf(random));


            }
        });

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(str -> {
                if (random != 0) {
                    if (str.toString().equals(String.valueOf(random))) {
                        phone = phoneEt.getText().toString();
                        Intent in = new Intent(PhoneActivity.this, RegisterActivity.class);
                        in.putExtra("phone", phone);
                        startActivity(in);
                        finish();
                    }
//                    } else {
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,
//                                str.toString());
//                        signInWithPhoneAuthCredential(credential);
//                    }
                } else {
                    Toast.makeText(PhoneActivity.this, getString(R.string.wrongcode), Toast.LENGTH_SHORT).show();
                    pinEntry.setText(null);
                }
            });
        }


    }

    private boolean checkPhone() {
        String phone = phoneEt.getText().toString();
        return CheckEntries.isPhone(phone);
    }
    private final Handler handler = new Handler();
    private Runnable runnable;


    CountDownTimer countDownTimer;
    String mobileSent = "";
    private void countDownStart() {

        countDownTimer =  new CountDownTimer(5*60*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long Minutes = millisUntilFinished / (60 * 1000) % 60;
                long Seconds = millisUntilFinished / 1000 % 60;
                timer.setText(String.format(Locale.ENGLISH,"%02d", Seconds) + " : "
                        +String.format(Locale.ENGLISH,"%02d", Minutes));
            }

            public void onFinish() {
                timer.setText("00 : 00");
                resend.setEnabled(true);
            }

        }.start();


    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
    public void verifyRecaptcha(String secret,String response,String remoteip,String mobile){

        Observable<VerifyCaptchaResponse> request = VerifyRecapchaProvider
                .getClient().create(ApiClient.class)
                .verifyApi(secret,response,remoteip).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<VerifyCaptchaResponse>() {
            @Override
            public void onNext(@NonNull VerifyCaptchaResponse response1) {
                if (response1. getSuccess()){

                } else {

                }
            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {

            }
        });
    }

    int random=0;
    @Override
    public void onResponse(boolean isSuccess, CheckUserResponse checkUserResponse) {
        if (!isSuccess){
            if (phoneEt.getText().toString().startsWith("7")){
                phoneNumber = "+967" +   phoneEt.getText().toString();
            } else {
                phoneNumber = "+" + 966 + phoneEt.getText().toString();
            }
            if (!mobileSent.equals(phoneNumber)) {
                loginProgress.setVisibility(View.VISIBLE);
                pageTitle.setText(getString(R.string.verify));
                btnContinue.setVisibility(View.GONE);
                disable(wholeView,false);
                final int min = 100000;
                final int max = 999999;
                random = new Random().nextInt((max - min) + 1) + min;
                viewModel.sendMessage(phoneEt.getText().toString(),String.valueOf(random));
            }
            llMobile.setVisibility(View.GONE);
            mobileText.setText(phoneNumber);
            editLayout.setVisibility(View.VISIBLE);
            pinEntry.setVisibility(View.VISIBLE);
            timerLayout.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.VISIBLE);
            resend.setEnabled(false);
            countDownStart();
        } else {
            loginProgress.setVisibility(View.GONE);
            btnContinue.setVisibility(View.VISIBLE);
            disable(wholeView,true);
            Intent intent = new Intent(PhoneActivity.this,SignInActivity.class);
            intent.putExtra("phone",phoneEt.getText().toString());
            startActivity(intent);
            finish();

//                Toast.makeText(PhoneActivity.this, checkUserResponse.getMrt7al().getMsg()
//                        , Toast.LENGTH_SHORT).show();
        }
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        loginProgress.setVisibility(View.GONE);
        btnContinue.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.GONE);
        disable(wholeView,true);
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), this);
                } else if (code == 400) {

                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException | IllegalStateException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        if (errorResponse.getMrt7al().isRegisterd()){
                            loginProgress.setVisibility(View.GONE);
                            btnContinue.setVisibility(View.VISIBLE);
                            btnConfirm.setVisibility(View.GONE);
                            disable(wholeView,true);
                            Intent intent = new Intent(PhoneActivity.this,SignInActivity.class);
                            intent.putExtra("phone",phoneEt.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            if (errorResponse.getMrt7al().getMsgUrl() != null) {
                                DialogsHelper.showSuspensionDialog(errorResponse.getMrt7al().getMsgUrl(),this);
                            } else {
                                Toast.makeText(this, errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
//                        else {
//                            Toast.makeText(this, "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
//                        }
                }
            }
        } else {
            Toast.makeText(this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMessageSent(SendRegisterMessageResponse sendRegisterMessageResponse) {
        if (!sendRegisterMessageResponse.getMrt7al().isRegisterd()){
            phoneNumber = phoneEt.getText().toString();
            if (phoneNumber.startsWith("0")){
                phoneNumber = phoneNumber.replaceFirst("0","");
            }
            if (phoneEt.getText().toString().startsWith("7")){
                phoneNumber = "+967" +   phoneEt.getText().toString();
            } else {
                phoneNumber = "+" + 966 + phoneEt.getText().toString();
            }
            pageTitle.setText(getString(R.string.verify));
            llMobile.setVisibility(View.GONE);
            mobileText.setText(phoneNumber);
            editLayout.setVisibility(View.VISIBLE);
            pinEntry.setVisibility(View.VISIBLE);
            timerLayout.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.VISIBLE);
            skipLogin.setVisibility(View.GONE);
            resend.setEnabled(false);
            countDownStart();
            loginProgress.setVisibility(View.GONE);
            btnContinue.setVisibility(View.GONE);
            pageTitle.setText(getString(R.string.verify));
            disable(wholeView,true);
            Toast.makeText(PhoneActivity.this, getString(R.string.code_sent), Toast.LENGTH_LONG).show();
        }
//            else {
//
//                Toast.makeText(PhoneActivity.this,sendRegisterMessageResponse
//                        .getMrt7al().getMsg(), Toast.LENGTH_LONG).show();
//            }
    }
}