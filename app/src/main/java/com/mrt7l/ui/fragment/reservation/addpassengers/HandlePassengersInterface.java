package com.mrt7l.ui.fragment.reservation.addpassengers;


import com.mrt7l.ui.fragment.passengers.PassengersResponse;

public interface HandlePassengersInterface {
     void OnEdit(  AddPassengersFragment.DataBean registerResponse);
    void OnDelete(AddPassengersFragment.DataBean registerResponse,int pos);
    void onPassengerChecked(AddPassengersFragment.DataBean dataBean);

    void OnEdit(  PassengersResponse.Mrt7alBean.DataBean registerResponse);
}
