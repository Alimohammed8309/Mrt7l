package com.mrt7l.ui.fragment.about;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.model.ContactUsModel;
import com.mrt7l.ui.fragment.favourite.FavouriteInterface;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AboutViewModel extends ViewModel {
    private WeakReference<AboutInterface> mNavigator;
    private AboutAppResponse contactModel;
    private FaqResponse faqs;
    public void init(AboutInterface contactInterface) {
        contactModel = AboutAppResponse.getInstance();
        faqs = FaqResponse.getInstance();
        setNavigator(contactInterface);
        getFaq();

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
                    contactModel.setMrt7al(contactUsModel.getMrt7al());
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

    public void getFaq(){
        Observable<FaqResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getFaq().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<FaqResponse>() {
            @Override
            public void onNext(@NonNull FaqResponse faqResponse) {
                try{
                if (faqResponse.getMrt7al().getSuccess()){
                    faqs.setMrt7al(faqResponse.getMrt7al());
                    getNavigator().onFaqResponse(true,faqs);
                } else {
                    getNavigator().onFaqResponse(false,faqs);
                }
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
    private AboutInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(AboutInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}