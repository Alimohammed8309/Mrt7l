package com.mrt7l.ui.fragment.passengers.edit_passenger;


import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;

public interface EditPassengersInterface {
    void onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse);
    void onResponse(boolean isSuccess, EditPassengersResponse registerResponse);
    void handleError(Throwable t);
}
