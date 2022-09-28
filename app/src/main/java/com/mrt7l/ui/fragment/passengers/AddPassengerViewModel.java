package com.mrt7l.ui.fragment.passengers;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.mrt7l.MyApplication;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;
import com.mrt7l.ui.activity.RegisterInterface;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPassengerViewModel extends ViewModel {
    private WeakReference<AddPassengersInterface> mNavigator;
    private RegisterCollectionResponse collectionResponse;

    public void init(Activity activity, AddPassengersInterface loginInterface) {
        collectionResponse = RegisterCollectionResponse.getInstance();
        setNavigator(loginInterface);
        getCollection();
    }

    public void getCollection() {
        String collectionModel = new PreferenceHelper(MyApplication.getInstance().getApplicationContext()).getGROUPID();
        if (collectionModel != null) {
            Gson gson = new Gson();
            RegisterCollectionResponse collectionResponse;
            collectionResponse = gson.fromJson(collectionModel, RegisterCollectionResponse.class);
            getNavigator().onGetCollections(true, collectionResponse);

        } else {
            Observable<RegisterCollectionResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                    .getRegisterCollection().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            request.subscribe(new DisposableObserver<RegisterCollectionResponse>() {
                @Override
                public void onNext(@NonNull RegisterCollectionResponse loginResponse) {
                    try {
                        if (loginResponse.getMrt7al().getSuccess()) {
                            Gson gson = new Gson();
                            String model = gson.toJson(loginResponse, RegisterCollectionResponse.class);
                            new PreferenceHelper(MyApplication.getInstance().getApplicationContext()).setGROUPID(model);
                            getNavigator().onGetCollections(true, loginResponse);
                        }
//                        else {
////                            getNavigator().onGetCollections(false, loginResponse);
//                        }
                    } catch (NullPointerException a) {
                        a.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    try {
                        getNavigator().handleError(e);
                    } catch (NullPointerException a) {
                        a.printStackTrace();
                    }
                }

                @Override
                public void onComplete() {

                }
            });

        }

    }

    public void AddPassenger(String token, RequestBody firstName,
                             RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                             RequestBody phone,
                             MultipartBody.Part image) {

        Observable<AddPassengerResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .AddPassenger("Bearer " + token, firstName, nationality, gender, birthday, passportNumber
                        , phone, image).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<AddPassengerResponse>() {
            @Override
            public void onNext(@NonNull AddPassengerResponse loginResponse) {
                try {
                    getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(), loginResponse);
                } catch (NullPointerException a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void AddPassenger(String token, RequestBody firstName,
                             RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                             RequestBody phone) {

        Observable<AddPassengerResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .AddPassenger("Bearer " + token, firstName, nationality, gender, birthday, passportNumber
                        , phone).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<AddPassengerResponse>() {
            @Override
            public void onNext(@NonNull AddPassengerResponse loginResponse) {
                try {
                    getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(), loginResponse);
                } catch (NullPointerException a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException a) {
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private AddPassengersInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(AddPassengersInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}