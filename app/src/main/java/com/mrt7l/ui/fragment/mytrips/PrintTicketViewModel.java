package com.mrt7l.ui.fragment.mytrips;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PrintTicketViewModel extends ViewModel {
    private WeakReference<PrintTicketInterface> mNavigator;

    public void init(PrintTicketInterface contactInterface) {
        setNavigator(contactInterface);

    }

    public void requestFile(String token,int tripID){
        Observable<RequestPdfResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .requestPdf("Bearer " + token,String.valueOf(tripID))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<RequestPdfResponse>() {
            @Override
            public void onNext(@NonNull RequestPdfResponse requestPdfResponse) {
                if (requestPdfResponse.getMrt7al().getSuccess()){
                     getNavigator().onFileRequested(true,requestPdfResponse.getMrt7al().getMsg(),
                             requestPdfResponse.getMrt7al().getData().getPdfLink());
                } else {
                    getNavigator().onFileRequested(false,requestPdfResponse.getMrt7al().getMsg(),"");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private PrintTicketInterface getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(PrintTicketInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}