package com.mrt7l.ui.activity;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;
 import java.lang.ref.WeakReference;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends ViewModel {

    private WeakReference<LoginInterface> mNavigator;
    private LoginResponse loginResponse;
     public void init(Activity activity ,LoginInterface loginInterface) {
        loginResponse = LoginResponse.getInstance();
         setNavigator(loginInterface);
     }

    public void login(String phone,String passwrod,String deviceToken){
        Observable<LoginResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .Login(phone,passwrod,deviceToken).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(@NonNull LoginResponse loginResponse) {
                getNavigator().onResponse(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {
                    getNavigator().handleError(e);
                } catch (NullPointerException | IllegalStateException a){
                    e.printStackTrace();
                }            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void forgetPassword(String phone){
        Observable<ForgetPasswordResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .forgetPassword(phone).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ForgetPasswordResponse>() {
            @Override
            public void onNext(@NonNull ForgetPasswordResponse loginResponse) {
                getNavigator().onForgetPassword(loginResponse.getMrt7al().isSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void resetPassword(String smsCode,String password,String repeatPassword){
        Observable<ChangePasswordResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .resetPassword(password,repeatPassword,smsCode).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ChangePasswordResponse>() {
            @Override
            public void onNext(@NonNull ChangePasswordResponse loginResponse) {
                getNavigator().onPasswordRestored(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(Throwable e) {

                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void confirmCode(String smsCode){
        Observable<ConfirmCodeResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .confirmCode(smsCode).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ConfirmCodeResponse>() {
            @Override
            public void onNext(@NonNull ConfirmCodeResponse loginResponse) {
                getNavigator().onCodeConfirmed(loginResponse.getMrt7al().getSuccess(),loginResponse,smsCode);
            }

            @Override
            public void onError(Throwable e) {

                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void sendMessage(String mobile){
        Observable<SendMessageResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .sendMessage(mobile ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<SendMessageResponse>() {
            @Override
            public void onNext(@NonNull SendMessageResponse loginResponse) {
                getNavigator().onMessageSent(loginResponse.getMrt7al().getSuccess(),loginResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                getNavigator().handleError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }



    private LoginInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(LoginInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
