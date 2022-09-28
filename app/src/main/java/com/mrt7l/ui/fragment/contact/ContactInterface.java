package com.mrt7l.ui.fragment.contact;


import com.mrt7l.model.ContactUsModel;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.ui.fragment.about.AboutAppResponse;

public interface ContactInterface {
    void onResponse(boolean isSuccess, ContactUsModel contactUsModel);
    void onAboutUsResponse(boolean isSuccess, AboutAppResponse aboutAppResponse);
    void handleError(Throwable t);
}
