package com.mrt7l.ui.fragment.home;

import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.adapter.HomeBusAdapter;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;

public interface HomeInterface {

         void onResponse(boolean isSuccess, HomeResponse homeResponse);
        void handleError(Throwable t);
        void onFavouriteAd(boolean success,String message);
        void onGetCollections(boolean success, RegisterCollectionResponse registerCollectionResponse);

}
