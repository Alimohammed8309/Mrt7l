package com.mrt7l.ui.fragment.search_trips;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
import java.lang.ref.WeakReference;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchTripsViewModel extends ViewModel {

    public SearchTripsResponse tripsResponse;
    private WeakReference<SearchInterface> mNavigator;
    public void init(SearchInterface navigator){
        setNavigator(navigator);
        tripsResponse = SearchTripsResponse.getInstance();
    }

    private String pag="1";
    public void search(String from,String to,String date,String time,String count,
                       String bus_type,String is_direct,String from_price,String to_price,
                       String companyId,String page,String appcheck){
        Log.v("search :",from + " to" + to + " date" +date + "time "+time + " count"+count+ " bus_type"
        +bus_type + "is_direct " + is_direct+ "from_price "+from_price + " to_price" + to_price + " companyId" +companyId
                + "page " + page);
//        getNavigator().showProgress();
        pag = page;
        ApiClient apiInterface =  RetrofitProvider.getClient().create(ApiClient.class);
        Observable<SearchTripsResponse> observable = apiInterface.Search(page,
                from,date, to,time,count,bus_type,is_direct,from_price,to_price
                ,companyId,appcheck)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<SearchTripsResponse>() {
            @Override
            public void onNext(@NonNull SearchTripsResponse model) {
                try{
                if (pag.equals("1")){
                     tripsResponse.setMrt7al(model.getMrt7al());
                } else {
                    tripsResponse.getMrt7al().getData().addAll(model.getMrt7al().getData());
                }
                getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
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
    private SearchInterface getNavigator() {
        return mNavigator.get();
    }

    void setNavigator(SearchInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
 }