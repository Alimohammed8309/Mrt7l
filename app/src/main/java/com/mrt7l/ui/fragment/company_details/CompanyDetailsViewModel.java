package com.mrt7l.ui.fragment.company_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.changePasswordResponse;
import com.mrt7l.ui.fragment.home.AddToFavouriteResponse;
import com.mrt7l.ui.fragment.search_trips.SearchInterface;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CompanyDetailsViewModel extends ViewModel {
    public CompanyDetailsResponse detailsResponse;
    private WeakReference<CompanyDetailsInterface> mNavigator;
     private String CompanyId = "0";
    public void init(CompanyDetailsInterface navigator,String id,String token){
        setNavigator(navigator);
        detailsResponse = CompanyDetailsResponse.getInstance();
         if (detailsResponse.getMrt7al() != null){
            if (CompanyId.equals(id)){
                getNavigator().onResponse(detailsResponse.getMrt7al().getSuccess(),detailsResponse);
            } else {
                getData(token,id);
            }
        } else {
            getData(token,id);
        }

    }

     public void getData(String token ,String companyId ){
         ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<CompanyDetailsResponse> observable = apiInterface.getCompanyDetails
                ("Bearer " +token,companyId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<CompanyDetailsResponse>() {
            @Override
            public void onNext(@NonNull CompanyDetailsResponse model) {
                try{
                    detailsResponse.setMrt7al(model.getMrt7al());
                getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
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
                try{
                getNavigator().onFavouriteAd(model.getMrt7al().isSuccess(),model.getMrt7al().getData().getStatus());
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
    }    private String pag="1";

    public void search(String from,String to,String date,String time,String count,
                       String bus_type,String is_direct,String from_price,String to_price,
                       String companyId,String page){
//        getNavigator().showProgress();
        pag = page;
        ApiClient apiInterface =  RetrofitProvider.getClient().create(ApiClient.class);
        Observable<SearchTripsResponse> observable = apiInterface.SearchDetails(page,
                date,companyId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<SearchTripsResponse>() {
            @Override
            public void onNext(@NonNull SearchTripsResponse model) {
                try{
                    getNavigator().onSearchTrips(model.getMrt7al().getSuccess(),model);
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
    private CompanyDetailsInterface getNavigator() {
        return mNavigator.get();
    }

    void setNavigator(CompanyDetailsInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}