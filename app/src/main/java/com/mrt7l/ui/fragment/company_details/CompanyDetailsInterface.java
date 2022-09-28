package com.mrt7l.ui.fragment.company_details;

import com.mrt7l.model.changePasswordResponse;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;

public interface CompanyDetailsInterface {

         void onResponse(boolean isSuccess, CompanyDetailsResponse homeResponse);
        void handleError(Throwable t);
        void onFavouriteAd(boolean success, String message);
        void onSearchTrips(boolean success, SearchTripsResponse searchTripsResponse);


}
