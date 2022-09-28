package com.mrt7l.ui.fragment.notifications;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
import java.lang.ref.WeakReference;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationsViewModel extends ViewModel {
    private WeakReference<NotificationsInterface> mNavigator;
    private NotificationsResponse notificationsResponse;
     public void init(NotificationsInterface contactInterface,String token,int page) {
        notificationsResponse = NotificationsResponse.getInstance();
        setNavigator(contactInterface);
        getNotifications(token,page);
    }

    public void getNotifications(String token,int page){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<NotificationsResponse> observable = apiInterface.notifyList(token,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<NotificationsResponse>() {
            @Override
            public void onNext(@NonNull NotificationsResponse model) {
                try{
                if (page ==1) {
                    notificationsResponse.setMrt7al(model.getMrt7al());
                } else {
                    notificationsResponse.getMrt7al().getData().addAll(model.getMrt7al().getData());
                }
                getNavigator().onResponse(model.getMrt7al().isSuccess(),notificationsResponse);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                getNavigator().handleError(e);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private NotificationsInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(NotificationsInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}