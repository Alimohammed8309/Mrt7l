package com.mrt7l.ui.fragment.stations;


import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;

public interface StationsInterface {
    void onResponse(boolean isSuccess, StationsResponse contactUsModel);
    void handleError(Throwable t);
    void onSearchResult();
 }
