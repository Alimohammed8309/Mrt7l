package com.mrt7l.ui.fragment.favourite;


import com.mrt7l.model.ContactUsModel;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;

public interface FavouriteInterface {
    void onResponse(boolean isSuccess, MyFavouriteResponse contactUsModel);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
}
