package com.mrt7l.ui.activity;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.model.RegisterResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
import java.lang.ref.WeakReference;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterViewModel extends ViewModel {

    private WeakReference<RegisterInterface> mNavigator;
    private RegisterCollectionResponse collectionResponse;
     public void init(Activity activity ,RegisterInterface loginInterface) {
         collectionResponse = RegisterCollectionResponse.getInstance();
         setNavigator(loginInterface);
         getCollection();
     }
    public void getCollection(){

        Observable<RegisterCollectionResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getRegisterCollection().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<RegisterCollectionResponse>() {
            @Override
            public void onNext(@NonNull RegisterCollectionResponse loginResponse) {
                if (loginResponse.getMrt7al().getSuccess()){
                     getNavigator().onGetCollections(true,loginResponse);
                } else {
                    getNavigator().onGetCollections(false,loginResponse);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    //e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void register(RequestBody fullName,
                         RequestBody nationality, RequestBody gender, RequestBody birthday,
                         RequestBody passportNumber,
                         RequestBody phone, RequestBody password, RequestBody repassword,
                         RequestBody cityId,
                             MultipartBody.Part image,RequestBody deviceToken){

        Observable<RegisterResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .register(fullName,gender,birthday,password
                ,repassword,cityId,phone,deviceToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<RegisterResponse>() {
            @Override
            public void onNext(@NonNull RegisterResponse loginResponse) {
                try {
                    getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(), loginResponse);
                } catch (NullPointerException e){

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    //e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void register(RequestBody fullName,
                         RequestBody nationality, RequestBody gender, RequestBody birthday,
                         RequestBody passportNumber,
                         RequestBody phone, RequestBody password, RequestBody repassword,
                         RequestBody cityId,
                         RequestBody deviceToken){

        Observable<RegisterResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .register(fullName,gender,birthday,password
                        ,repassword,cityId,phone,deviceToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<RegisterResponse>() {
            @Override
            public void onNext(@NonNull RegisterResponse loginResponse) {
                getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    //e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private RegisterInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(RegisterInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
