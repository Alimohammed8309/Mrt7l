package com.mrt7l.ui.fragment.contact;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.ContactUsModel;
import com.mrt7l.model.LoginResponse;
import com.mrt7l.ui.activity.LoginInterface;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactUsViewModel extends ViewModel {
    private WeakReference<ContactInterface> mNavigator;
    private AboutAppResponse aboutAppResponse;
    private ContactUsModel contactUsModel;
    public void init(Activity activity , ContactInterface contactInterface) {
        contactUsModel = ContactUsModel.getInstance();
        aboutAppResponse = AboutAppResponse.getInstance();
        setNavigator(contactInterface);
    }
    public void contact(String name,String mobile,String notes,String email){
        Observable<ContactUsModel> request = RetrofitProvider.getClient().create(ApiClient.class)
                .contactUs(name,email,mobile,notes).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<ContactUsModel>() {
            @Override
            public void onNext(@NonNull ContactUsModel contactUsModel) {
                getNavigator().onResponse(contactUsModel.getMrt7al().isSuccess(),contactUsModel);
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


    private ContactInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(ContactInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}