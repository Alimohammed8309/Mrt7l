package com.mrt7l.ui.fragment.mytrips;


public interface PrintTicketInterface {
     void handleError(Throwable t);
    void onFileRequested(boolean isSuccess,String message,String url);
    void onFileDownloaded(boolean isSuccess);

}
