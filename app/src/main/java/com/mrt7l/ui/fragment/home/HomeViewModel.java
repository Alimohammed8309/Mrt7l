package com.mrt7l.ui.fragment.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.mrt7l.MyApplication;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.search_trips.AllCompaniesResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class    HomeViewModel extends ViewModel {
    private final LiveData<String> progressLoadStatus = new MutableLiveData<>();
    public HomeResponse homeResponse;
    RegisterCollectionResponse registerCollectionResponse;
    AboutAppResponse aboutAppResponse;
     private WeakReference<HomeInterface> mNavigator;
       public void init(HomeInterface navigator){
        setNavigator(navigator);
           aboutAppResponse = AboutAppResponse.getInstance();
          homeResponse = HomeResponse.getInstance();
           registerCollectionResponse = RegisterCollectionResponse.getInstance();
           getCollection();
           getAboutUs();
    }

    public void getCollection(){
            Observable<RegisterCollectionResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                    .getRegisterCollection().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            request.subscribe(new DisposableObserver<RegisterCollectionResponse>() {
                @Override
                public void onNext(@NonNull RegisterCollectionResponse loginResponse) {
                    try{
                    if (loginResponse.getMrt7al().getSuccess()){
                        Gson gson = new Gson();
                        String model = gson.toJson(loginResponse,RegisterCollectionResponse.class);
                        new PreferenceHelper(MyApplication.getInstance().getApplicationContext())
                                .setGROUPID(model);
                        registerCollectionResponse.setMrt7al(loginResponse.getMrt7al());
                        getNavigator().onGetCollections(true,loginResponse);
                    } else {
                        getNavigator().onGetCollections(false,loginResponse);
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
//        }
    }




    public void getAboutUs(){
        Observable<AboutAppResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getAboutApp().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<AboutAppResponse>() {
            @Override
            public void onNext(@NonNull AboutAppResponse contactUsModel) {
                try{
                if (contactUsModel.getMrt7al().getSuccess()){
                    aboutAppResponse.setMrt7al(contactUsModel.getMrt7al());
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
    private String pag="1";
       boolean isLoaded = false;
    public void search(String date ,String appCheck,String page){
        pag = page;
        if (date == null){
            date = "";
        }
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<HomeResponse> observable = apiInterface.SearchHome(date,appCheck,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<HomeResponse>() {
            @Override
            public void onNext(@NonNull HomeResponse model) {
                try{
                if (!isLoaded){
                    isLoaded = true;

                }
//                if (pag.equals("1")){
                    homeResponse.setMrt7al(model.getMrt7al());

//                } else {
//                    homeResponse.getMrt7al().getData().addAll(model.getMrt7al().getData());
//                    homeResponse.getMrt7al().setPagination(model.getMrt7al().getPagination());
//                }
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

    private HomeInterface getNavigator() {
        return mNavigator.get();
    }

    void setNavigator(HomeInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }
}