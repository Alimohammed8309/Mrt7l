package com.mrt7l.ui.fragment.passengers;


public interface HandlePassengersInterface {
     void OnEdit(  PassengersResponse.Mrt7alBean.DataBean registerResponse);
    void OnDelete(PassengersResponse.Mrt7alBean.DataBean registerResponse,int pos);
    void onPassengerChecked(PassengersResponse.Mrt7alBean.DataBean dataBean);

}
