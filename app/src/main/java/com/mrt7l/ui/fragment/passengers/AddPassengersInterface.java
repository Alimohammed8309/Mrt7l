package com.mrt7l.ui.fragment.passengers;


import com.mrt7l.model.RegisterCollectionResponse;

public interface AddPassengersInterface {
    void onGetCollections(boolean isSuccess, RegisterCollectionResponse registerCollectionResponse);
    void onResponse(boolean isSuccess, AddPassengerResponse registerResponse);
    void handleError(Throwable t);
}
