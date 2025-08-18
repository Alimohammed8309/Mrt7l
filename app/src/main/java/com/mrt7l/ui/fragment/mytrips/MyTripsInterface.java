package com.mrt7l.ui.fragment.mytrips;


import com.mrt7l.model.changePasswordResponse;
 import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.mrt7l.ui.fragment.reservation.RequestPayModel;

public interface MyTripsInterface {
    void onCurrentResponse(boolean isSuccess, CurrentOrdersResponse currentOrdersResponse);
    void onPastResponse(boolean isSuccess, PastOrdersResponse currentOrdersResponse);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
    void onCanceled(boolean isSuccess, CancelOrderResponse cancelOderResponse);
    void onTripRated(boolean success,String message);
    void handlePaymentError(String message);
    void onCheckingPayStatus(boolean success,String message);
    void onPayRequested(boolean success, RequestPayModel requestPayModel);

}
