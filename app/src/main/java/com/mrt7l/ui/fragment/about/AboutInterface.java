package com.mrt7l.ui.fragment.about;


import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;

public interface AboutInterface {
    void onResponse(boolean isSuccess, AboutAppResponse contactUsModel);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
    void onFaqResponse(boolean isSuccess,FaqResponse faqResponse);
}
