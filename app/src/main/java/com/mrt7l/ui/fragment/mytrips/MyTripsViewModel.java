package com.mrt7l.ui.fragment.mytrips;

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

public class MyTripsViewModel extends ViewModel {
    private WeakReference<MyTripsInterface> mNavigator;
    private CurrentOrdersResponse currentOrdersResponse;
    private PastOrdersResponse pastOrderResponse;
    public void init(MyTripsInterface contactInterface,String token,int currentOrdersPage,int pastOrdersPage) {
        currentOrdersResponse = CurrentOrdersResponse.getInstance();
        pastOrderResponse = PastOrdersResponse.getInstance();
        setNavigator(contactInterface);

    }
    public void getCurrentTrips(String token,int page){
        Observable<CurrentOrdersResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getCurrentTrips("Bearer " + token,String.valueOf(page))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<CurrentOrdersResponse>() {
            @Override
            public void onNext(@NonNull CurrentOrdersResponse contactUsModel) {
                try{
                if (contactUsModel.getMrt7al().getSuccess()){
                    currentOrdersResponse.setMrt7al(contactUsModel.getMrt7al());
                    getNavigator().onCurrentResponse(true,contactUsModel);
                } else {
                    getNavigator().onCurrentResponse(false,contactUsModel);
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

    public void getPastTrips(String token,int userId){
        Observable<PastOrdersResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getPastTrips("Bearer " + token,String.valueOf(userId)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<PastOrdersResponse>() {
            @Override
            public void onNext(@NonNull PastOrdersResponse contactUsModel) {
                try{
                if (contactUsModel.getMrt7al().getSuccess()){
                    pastOrderResponse.setMrt7al(contactUsModel.getMrt7al());
                    getNavigator().onPastResponse(true,contactUsModel);
                } else {
                    getNavigator().onPastResponse(false,contactUsModel);
                }
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                try {
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

    public void ratePastTrip(String token,String reservation_id,String comfort,String bus_status,
                             String other_service,String wc,String behavior,
                             String skills,String notes){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<RateTripResponse> observable = apiInterface.rateTrip
                (token,reservation_id,comfort,bus_status,other_service,wc,behavior,skills,notes)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<RateTripResponse>() {
            @Override
            public void onNext(@NonNull RateTripResponse model) {
                try{
                getNavigator().onTripRated(model.getMrt7al().getSuccess(),
                        model.getMrt7al().getMsg());
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
    public void CancelOrder(String token,String reservation_id,String  cancel_reason){
        ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<CancelOrderResponse> observable = apiInterface.cancelOrder(
                "Bearer " + token,reservation_id,cancel_reason)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new DisposableObserver<CancelOrderResponse>() {
            @Override
            public void onNext(@NonNull CancelOrderResponse model) {
                try{
                getNavigator().onCanceled(model.getMrt7al().getSuccess(),model);
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

    private MyTripsInterface getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(MyTripsInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}