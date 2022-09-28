package com.mrt7l.ui.activity;


import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.RegisterCollectionResponse;

public interface ProfileInterface {
    void  onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse);
    void onResponse(boolean isSuccess, EditProfileResponse registerResponse);
    void handleError(Throwable t);
    void onGetProfileData(boolean isSuccess, ProfileResponse profileResponse);
    
}
