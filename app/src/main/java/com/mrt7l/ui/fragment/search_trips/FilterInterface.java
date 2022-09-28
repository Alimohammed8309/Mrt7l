package com.mrt7l.ui.fragment.search_trips;

public interface FilterInterface {

    void onFilterDataRetrieved(String from_price,String toPrice,String isDirect,
                               String busType,int companyId);
}
