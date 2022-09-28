package com.mrt7l.ui.fragment.favourite;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.mrt7l.ui.fragment.home.AddToFavouriteResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FavouriteViewModel extends ViewModel {
    private MyFavouriteResponse favouriteResponse;
    private WeakReference<FavouriteInterface> mNavigator;
     public void init(FavouriteInterface contactInterface,String token) {
         setNavigator(contactInterface);
         favouriteResponse = MyFavouriteResponse.getInstance();
        getFavourite(token);
    }
    public void getFavourite(String token){
        Observable<MyFavouriteResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getMyFavourite("Bearer " + token).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<MyFavouriteResponse>() {
            @Override
            public void onNext(@NonNull MyFavouriteResponse contactUsModel) {
                try{
                favouriteResponse.setMrt7al(contactUsModel.getMrt7al());
                getNavigator().onResponse(contactUsModel.getMrt7al().isSuccess(),contactUsModel);
                } catch (NullPointerException a){
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
    public void addToFavourite(String token,String companyid,String status){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<AddToFavouriteResponse> observable = apiInterface.AddToFavourite(
                token,companyid,status)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<AddToFavouriteResponse>() {
            @Override
            public void onNext(@NonNull AddToFavouriteResponse model) {
                try{
                getNavigator().onFavouriteAd(model.getMrt7al().isSuccess());
                } catch (NullPointerException a){
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


    private FavouriteInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(FavouriteInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}