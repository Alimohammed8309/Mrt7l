package com.mrt7l.ui.activity;


import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;

public interface PhoneInterface {
     void onResponse(boolean isSuccess, CheckUserResponse checkUserResponse);
    void handleError(Throwable t);
    void onMessageSent(SendRegisterMessageResponse sendRegisterMessageResponse);
}
