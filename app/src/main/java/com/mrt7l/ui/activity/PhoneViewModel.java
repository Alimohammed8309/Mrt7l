package com.mrt7l.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PhoneViewModel extends ViewModel {

    private WeakReference<PhoneInterface> mNavigator;
    private RegisterCollectionResponse collectionResponse;
     public void init(Activity activity ,PhoneInterface loginInterface) {
         collectionResponse = RegisterCollectionResponse.getInstance();
         setNavigator(loginInterface);

     }

    public void checkPhone(String phone){

        Observable<CheckUserResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .checkIFUserExists(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<CheckUserResponse>() {
            @Override
            public void onNext(@NonNull CheckUserResponse checkUserResponse) {
                getNavigator().onResponse(checkUserResponse.getMrt7al().getSuccess(),checkUserResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    a.printStackTrace();
                }
                }



            @Override
            public void onComplete() {

            }
        });
    }
    public void sendMessage(String mobile,String code){
        Observable<SendRegisterMessageResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .sendRegisterMessage(mobile,code ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<SendRegisterMessageResponse>() {
            @Override
            public void onNext(@NonNull SendRegisterMessageResponse loginResponse) {
                try{
                    getNavigator().onMessageSent(loginResponse);
                } catch (NullPointerException | IllegalStateException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    a.printStackTrace();
                }
                }


            @Override
            public void onComplete() {

            }
        });
    }
    private PhoneInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(PhoneInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
