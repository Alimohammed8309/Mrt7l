package com.mrt7l.ui.fragment.reservation;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.MyApplication;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.passengers.AddPassengerResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.passengers.RemovePassengerResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ReserveForMeViewModel extends ViewModel {
    public ReservationResponse reservationResponse;
    public CheckPassportResponse checkPassportResponse;
    public ReservationConfirmedResponse reservationConfirmedResponse;
    private WeakReference<ReservationInterface> mNavigator;
    private ErrorResponse errorResponse;
    public  RequestPayModel requestPayModel;
    public void init(ReservationInterface navigator){
        setNavigator(navigator);
        reservationResponse = ReservationResponse.getInstance();
        passengersResponse =PassengersResponse.getInstance();
        reservationConfirmedResponse = ReservationConfirmedResponse.getInstance();
        checkPassportResponse= CheckPassportResponse.getInstance();
        getCollection();
     }
    public void getCollection() {
        String collectionModel = new PreferenceHelper(MyApplication.getInstance().getApplicationContext()).getGROUPID();
        if (collectionModel != null) {
            Gson gson = new Gson();
            RegisterCollectionResponse collectionResponse;
            collectionResponse = gson.fromJson(collectionModel, RegisterCollectionResponse.class);
            getNavigator().onGetCollections( collectionResponse);

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
                            getNavigator().onGetCollections( loginResponse);
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
//                        getNavigator().handleError(e);
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
    public void addTrip(String token, List<RequestBody> subIdsList,
                        RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                        RequestBody payMethod,RequestBody trip_date_id){

        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationResponse> observable = apiInterface.addTrip(
                token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationResponse>() {
            @Override
            public void onNext(@NonNull ReservationResponse model) {

                try{
                if (model.getMrt7al().getSuccess()){
                    reservationResponse.setMrt7al(model.getMrt7al());
                }
                getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void removePassenger(String id,String token){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<RemovePassengerResponse> observable = apiInterface.removePassenger(
                        "Bearer "+token,id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<RemovePassengerResponse>() {
            @Override
            public void onNext(@NonNull RemovePassengerResponse model) {
                try{
                    getNavigator().onPassengerDeleted(model.getMrt7al().isSuccess(),model.getMrt7al().getMsg());
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void addTripWithReset(String token, List<RequestBody> subIdsList,
                                 RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                                 RequestBody payMethod,RequestBody trip_date_id,RequestBody passportId,
                                 MultipartBody.Part resetImage,MultipartBody.Part passportImage){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationResponse> observable = apiInterface.addTripWithReset(
                token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger,passportId,resetImage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationResponse>() {
            @Override
            public void onNext(@NonNull ReservationResponse model) {
                try{
                if (model.getMrt7al().getSuccess()){
                    reservationResponse.setMrt7al(model.getMrt7al());
                }
                getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void addTripWithPassport(String token, List<RequestBody> subIdsList,
                                 RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                                 RequestBody payMethod,RequestBody trip_date_id,RequestBody passportId,
                                  MultipartBody.Part passportImage){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationResponse> observable = apiInterface.addTripWithPassport(
                token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger,passportId,passportImage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationResponse>() {
            @Override
            public void onNext(@NonNull ReservationResponse model) {
                try{
                    Log.e("","onNext");
                    if (model.getMrt7al().getSuccess()){
                        Log.e("","getSuccess");
                        reservationResponse.setMrt7al(model.getMrt7al());
                    }
                    getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    Log.e("","catch");
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    Log.e("","onError");
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void addTripConfirmed(String token, List<RequestBody> subIdsList,
                        RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                        RequestBody payMethod,RequestBody trip_date_id ){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationConfirmedResponse> observable = apiInterface.
                addTripConfirmed(token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationConfirmedResponse>() {
            @Override
            public void onNext(@NonNull ReservationConfirmedResponse model) {
                try{
                if (model.getMrt7al().getSuccess()){
                    reservationConfirmedResponse.setMrt7al(model.getMrt7al());
                }
                getNavigator().onConfirmedResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
                public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void requestPay(String token,String reservation_id){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<RequestPayModel> observable = apiInterface.requestPay(
                        token,reservation_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<RequestPayModel>() {
            @Override
            public void onNext(@NonNull RequestPayModel model) {
                try{
                    requestPayModel = model;
                    getNavigator().onPayRequested(model.getMrt7al().getSuccess(),
                            model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void checkPayStatus(String token,String chargeID){
        Log.v("chargeID" ,chargeID);
        Observable<CheckPayStatusModel> request = RetrofitProvider.getClient().create(ApiClient.class)
                .checkPayStatus("Bearer " + token,chargeID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<CheckPayStatusModel>() {
            @Override
            public void onNext(@NonNull CheckPayStatusModel checkPayStatusModel) {
                try{
                    if (checkPayStatusModel.getMrt7al().getSuccess()){
                        getNavigator().onCheckingPayStatus(true,checkPayStatusModel.getMrt7al().getMsg());
                    } else {
                        getNavigator().onCheckingPayStatus(false,checkPayStatusModel.getMrt7al().getMsg());
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handlePayError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handlePayError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void addTripWithResetConfirmed(String token, List<RequestBody> subIdsList,
                        RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                        RequestBody payMethod,RequestBody trip_date_id,MultipartBody.Part resetImage){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationConfirmedResponse> observable = apiInterface.
                addTripWithResetConfirmed(token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger,resetImage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationConfirmedResponse>() {
            @Override
            public void onNext(@NonNull ReservationConfirmedResponse model) {
                try{
                if (model.getMrt7al().getSuccess()){
                    reservationConfirmedResponse.setMrt7al(model.getMrt7al());
                }
                getNavigator().onConfirmedResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    private ReservationInterface getNavigator() {
        return mNavigator.get();
    }
    public void verifyPromo(String token, List<RequestBody> subIdsList,
                            RequestBody mainPassenger,RequestBody promo,RequestBody beforeConfirm,
                            RequestBody payMethod,RequestBody trip_date_id ){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<ReservationResponse> observable = apiInterface.addTrip(
                token,subIdsList,promo,payMethod,trip_date_id
                ,beforeConfirm,mainPassenger)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<ReservationResponse>() {
            @Override
            public void onNext(@NonNull ReservationResponse model) {
                try{
                    if (model.getMrt7al().getSuccess()){
                        reservationResponse.setMrt7al(model.getMrt7al());
                    }
                    getNavigator().onVerifyPromo( model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void checkPassport(String token){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<CheckPassportResponse> observable = apiInterface.checkPassport(
                token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<CheckPassportResponse>() {
            @Override
            public void onNext(@NonNull CheckPassportResponse model) {
                try{
                    if (model.getMrt7al().getSuccess()){
                        checkPassportResponse.setMrt7al(model.getMrt7al());

                    }
                    getNavigator().setCheckPassport( model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    void setNavigator(ReservationInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public void AddPassenger(String token, RequestBody firstName, RequestBody userid,
                             RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                             RequestBody phone,
                             MultipartBody.Part image){

        Observable<AddPassengerResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .AddPassenger("Bearer " + token,firstName,nationality,gender
                        ,birthday,passportNumber
                        ,phone,image).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<AddPassengerResponse>() {
            @Override
            public void onNext(@NonNull AddPassengerResponse addPassengerResponse) {
                try{
                 getNavigator().onAddPassenger(addPassengerResponse);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void AddPassenger(String token, RequestBody firstName, RequestBody userid,
                             RequestBody nationality, RequestBody gender, RequestBody birthday, RequestBody passportNumber,
                             RequestBody phone){

        Observable<AddPassengerResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .AddPassenger("Bearer " + token,firstName,nationality,gender
                        ,birthday,passportNumber
                        ,phone).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<AddPassengerResponse>() {
            @Override
            public void onNext(@NonNull AddPassengerResponse addPassengerResponse) {
                try{
                    getNavigator().onAddPassenger(addPassengerResponse);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        Gson gson = new Gson();
                        try {
                            assert body != null;
                            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if (errorResponse.getMrt7al() != null) {
                            getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                        } else {
                            getNavigator().handleError("خطأ بالبيانات");
                        }
                    }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    int page;
    PassengersResponse passengersResponse;
    public void getPassengers(int page,String token){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<PassengersResponse> observable = apiInterface.getPassengers("Bearer "+token,
                String.valueOf(page),1000)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<PassengersResponse>() {
            @Override
            public void onNext(@NonNull PassengersResponse model) {
                try{
                if (page==1) {
                    passengersResponse.setMrt7al(model.getMrt7al());
                } else {
                    passengersResponse.getMrt7al().getData().addAll(model.getMrt7al().getData());
                }
                getNavigator().onGetPassengers(model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try{
                if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        getNavigator().handleError(errorResponse.getMrt7al().getMsg());
                    } else {
                        getNavigator().handleError("خطأ بالبيانات");
                    }
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
    private MutableLiveData<Uri> passportUri = new MutableLiveData<>();
    private MutableLiveData<String> passportId= new MutableLiveData<>();
    private MutableLiveData<Boolean> isPassportSent =new MutableLiveData<>();
    public void setPassportUri (Uri uri){
        passportUri.setValue(uri);
    }
    public void setPassportId (String v){
        passportId.setValue(v);
    }
    public void setIsPassportSent (boolean isSent){
        isPassportSent.setValue(isSent);
    }
    public LiveData<Uri> getPassportImage(){
        return passportUri;
    }
    public LiveData<String> getPassportId(){
        return passportId;
    }
    public LiveData<Boolean> isPassportSentToServer(){
        return isPassportSent;
    }

}