package com.mrt7l.ui.fragment.passengers;


public interface PassengersInterface {
     void onResponse(boolean isSuccess, PassengersResponse homeResponse);
    void handleError(Throwable t);
    void onPassengerDeleted(boolean success,String message);
}
