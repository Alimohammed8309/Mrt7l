package com.mrt7l.ui.activity;


import com.mrt7l.model.LoginResponse;

public interface LoginInterface {
    void onResponse(boolean isSuccess, LoginResponse loginResponse);

    void handleError(Throwable t);

    void onForgetPassword(boolean isSuccess, ForgetPasswordResponse forgetPasswordResponse);

    void onPasswordRestored(boolean isSuccess, ChangePasswordResponse confirmPasswordResponse);

    void onMessageSent(boolean isSuccess, SendMessageResponse confirmPasswordResponse);
    void onCodeConfirmed(boolean isSuccess, ConfirmCodeResponse confirmPasswordResponse,String smsCode);

}