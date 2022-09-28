package com.mrt7l.ui.fragment.stations;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.ContactUsModel;
import com.mrt7l.ui.fragment.favourite.FavouriteInterface;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.mrt7l.ui.fragment.home.AddToFavouriteResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StationsViewModel extends ViewModel {
    private WeakReference<StationsInterface> mNavigator;
    private StationsResponse stationsResponse;
    public void init(StationsInterface contactInterface) {
        stationsResponse = StationsResponse.getInstance();
        setNavigator(contactInterface);
//        getStations(cityId,lat,lng);
    }

    public void getStations(String cityId,String lat,String lng){
        Observable<StationsResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getStations(cityId,lat,lng ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<StationsResponse>() {
            @Override
            public void onNext(@NonNull StationsResponse contactUsModel) {
                try{
                if (contactUsModel.getMrt7al().isSuccess()){
                    stationsResponse.setMrt7al(contactUsModel.getMrt7al());
                     getNavigator().onResponse(true,contactUsModel);
                } else {
                    getNavigator().onResponse(false,contactUsModel);
                }
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
    public void addToFavourite(String token,String companyid,String userId,String status){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<AddToFavouriteResponse> observable = apiInterface.AddToFavourite
                (token,companyid,status)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<AddToFavouriteResponse>() {
            @Override
            public void onNext(@NonNull AddToFavouriteResponse model) {
             }

            @Override
            public void onError(@NonNull Throwable e) {
//                try {
//                    assert ((HttpException)e).response().errorBody() != null;
//                    ResponseBody body = ((HttpException) e).response().errorBody();
//                    assert body != null;
//                    errorResponse= new Gson().fromJson(body.string(), ErrorResponse.class);
//                } catch (IOException | JsonSyntaxException | ClassCastException e1) {
//                    e1.printStackTrace();
//                }
                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private StationsInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(StationsInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}