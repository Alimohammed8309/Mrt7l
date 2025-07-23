package com.mrt7l.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.mrt7l.R;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText codeView,passwordEt,rePassword;
    private TextView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_dialog);
        codeView = findViewById(R.id.codeEt);
        send = findViewById(R.id.send);
        passwordEt = findViewById(R.id.passwordEt);
        rePassword = findViewById(R.id.rePassword);
        send.setOnClickListener(view -> {
            if (codeView.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(codeView, this);
            } else   if (passwordEt.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(passwordEt, this);
            } else   if (rePassword.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(rePassword, this);
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
                DialogsHelper.showProgressDialog(ResetPasswordActivity.this,getString(R.string.loading_data));
                resetPassword(codeView.getText().toString(),
                        passwordEt.getText().toString(),
                        rePassword.getText().toString()
                );
            }
        });

    }
    public void resetPassword(String smsCode,String password,String repeatPassword){

        Observable<ChangePasswordResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .resetPassword(password,repeatPassword,smsCode).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ChangePasswordResponse>() {
            @Override
            public void onNext(@NonNull ChangePasswordResponse confirmPasswordResponse) {
                if (confirmPasswordResponse.getMrt7al().getSuccess()){
                    Toast.makeText(ResetPasswordActivity.this, confirmPasswordResponse.getMrt7al().getMsg(), Toast.LENGTH_LONG).show();
                    DialogsHelper.removeProgressDialog();
                    finish();
                } else {
                    DialogsHelper.removeProgressDialog();
                    Toast.makeText(ResetPasswordActivity.this,
                            confirmPasswordResponse.getMrt7al().getMsg(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onError(Throwable e) {
                try {
                if (ConnectivityReceiver.isConnected()){
                    Toast.makeText(ResetPasswordActivity.this, "الكود غير صحيح", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                }
                    } catch (NullPointerException | IllegalStateException a){
                        //e.printStackTrace();
                    }
            }

            @Override
            public void onComplete() {

            }
        });
    }

}