package com.mrt7l.ui.fragment.explain_app;


import com.mrt7l.ui.fragment.about.AboutAppResponse;

public interface VideosInterface {
    void onResponse(boolean isSuccess, VideosResponse videosResponse);
    void handleError(Throwable t);
    void onFavouriteAd(boolean isSuccess);
}
