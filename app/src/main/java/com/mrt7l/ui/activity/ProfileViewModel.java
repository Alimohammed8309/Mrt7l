package com.mrt7l.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.EditProfileResponse;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends ViewModel {

    private WeakReference<ProfileInterface> mNavigator;
    private RegisterCollectionResponse collectionResponse;

    public void init(Activity activity ,ProfileInterface loginInterface) {
        collectionResponse = RegisterCollectionResponse.getInstance();
        setNavigator(loginInterface);
        getCollection();

    }
    public String token = "",userId = "";
    public void getCollection(){
        getNavigator().onGetCollections(true,collectionResponse);
        getProfileData(token,userId);
//            Observable<RegisterCollectionResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
//                    .getRegisterCollection().subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread());
//            request.subscribe(new DisposableObserver<RegisterCollectionResponse>() {
//                @Override
//                public void onNext(RegisterCollectionResponse loginResponse) {
//                    if (loginResponse.getMrt7al().getSuccess()){
//                        Gson gson = new Gson();
//                        String model = gson.toJson(loginResponse,RegisterCollectionResponse.class);
//                        new PreferenceHelper(MyApplication.getInstance().getApplicationContext()).setGROUPID(model);
//                        getNavigator().onGetCollections(true,loginResponse);
//                    } else {
//                        getNavigator().onGetCollections(false,loginResponse);
//                    }
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                    getNavigator().handleError(e);
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            });
    }

    public void edit(String token,int userId,RequestBody fullname,
                     RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                     RequestBody password, RequestBody cityId,
                     MultipartBody.Part image){

        Observable<EditProfileResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .editProfile(token,String.valueOf(userId),fullname,nationality,gender,
                        birthday,passportNumber,password,password, cityId,
                        image).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<EditProfileResponse>() {
            @Override
            public void onNext(@NonNull EditProfileResponse loginResponse) {
                getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void getProfileData(String token,String id){

        Observable<ProfileResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getProfileData(token,id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ProfileResponse>() {
            @Override
            public void onNext(@NonNull ProfileResponse loginResponse) {
                getNavigator().onGetProfileData(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void edit(String token,int userId,RequestBody fullname,
                     RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                     RequestBody password, RequestBody cityId){

        Observable<EditProfileResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .editProfile(token,String.valueOf(userId),fullname,nationality,gender,
                        birthday,passportNumber,password,password, cityId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<EditProfileResponse>() {
            @Override
            public void onNext(@NonNull EditProfileResponse loginResponse) {
                getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private ProfileInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(ProfileInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
