package com.mrt7l.ui.fragment.reservation;

import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;

public interface ReservationInterface {

         void onResponse(boolean isSuccess, ReservationResponse homeResponse);
         void onConfirmedResponse(boolean isSucces,ReservationConfirmedResponse reservationConfirmedResponse);
        void handleError(String  t);
     void handlePayError(String  t);
    void onVerifyPromo(ReservationResponse verifyPromoResponse);
        void onGetPassengers(PassengersResponse passengersResponse);
        void onAddPassenger(AddPassengerResponse addPassengerResponse);
        void setCheckPassport(CheckPassportResponse checkPassportResponse);
        void onEditProfile(EditProfileResponse editProfileResponse);
        void onGetCollections(RegisterCollectionResponse registerCollectionResponse);
    void onCheckingPayStatus(boolean success,String message);
    void onPayRequested(boolean success, RequestPayModel requestPayModel);
}
