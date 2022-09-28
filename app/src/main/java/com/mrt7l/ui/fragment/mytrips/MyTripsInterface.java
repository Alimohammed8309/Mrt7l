package com.mrt7l.ui.fragment.mytrips;


import com.mrt7l.model.changePasswordResponse;
 import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;

public interface MyTripsInterface {
    void onCurrentResponse(boolean isSuccess, CurrentOrdersResponse currentOrdersResponse);
    void onPastResponse(boolean isSuccess, PastOrdersResponse currentOrdersResponse);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
    void onCanceled(boolean isSuccess, CancelOrderResponse cancelOderResponse);
    void onTripRated(boolean success,String message);
}
