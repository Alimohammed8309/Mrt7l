package com.mrt7l.ui.fragment.passengers;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
import java.lang.ref.WeakReference;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PassengersViewModel extends ViewModel {
    private final LiveData<String> progressLoadStatus = new MutableLiveData<>();
    public PassengersResponse passengersResponse;
     private WeakReference<PassengersInterface> mNavigator;
     private int  page=1;
     public void init(PassengersInterface navigator){
        setNavigator(navigator);
        passengersResponse = PassengersResponse.getInstance();
    }

    public PassengersViewModel() {

    }

    public void getPassengers(int userid,String token){
         ApiClient apiInterface =   RetrofitProvider.getClient().create(ApiClient.class);
        Observable<PassengersResponse> observable = apiInterface.getPassengers("Bearer "+
                token,String.valueOf(userid),1000)
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
                getNavigator().onResponse(model.getMrt7al().getSuccess(),model);
                } catch (NullPointerException a){
                    a.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
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

    private PassengersInterface getNavigator() {
        return mNavigator.get();
    }

    void setNavigator(PassengersInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }
}