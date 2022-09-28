package com.mrt7l.ui.fragment.search_trips;


public interface SearchInterface {

         void onResponse(boolean isSuccess, SearchTripsResponse homeResponse);
        void handleError(Throwable t);
        void onFavouriteAd(boolean success, String message);
    void showProgress();


}
