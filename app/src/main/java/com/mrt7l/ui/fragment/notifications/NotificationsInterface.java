package com.mrt7l.ui.fragment.notifications;


import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;

public interface NotificationsInterface {
    void onResponse(boolean isSuccess, NotificationsResponse contactUsModel);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
}
