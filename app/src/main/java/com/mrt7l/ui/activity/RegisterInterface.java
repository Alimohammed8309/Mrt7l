package com.mrt7l.ui.activity;


import com.mrt7l.model.LoginResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;

public interface RegisterInterface {
    void  onGetCollections(boolean isSuccess,RegisterCollectionResponse registerCollectionResponse);
    void onResponse(boolean isSuccess, RegisterResponse registerResponse);
    void handleError(Throwable t);
}
