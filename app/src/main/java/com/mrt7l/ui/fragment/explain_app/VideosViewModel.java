package com.mrt7l.ui.fragment.explain_app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.about.AboutInterface;
import com.mrt7l.utils.retrofit.ApiClient;
import com.mrt7l.utils.retrofit.RetrofitProvider;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VideosViewModel extends ViewModel {
    private WeakReference<VideosInterface> mNavigator;
    private VideosResponse videosRespons;
    public void init(VideosInterface contactInterface) {
        videosRespons = VideosResponse.getInstance();
        setNavigator(contactInterface);
        getAboutUs();
    }
    public void getAboutUs(){
        Observable<VideosResponse> request = RetrofitProvider.getClient().create(ApiClient.class)
                .getVideosList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        request.subscribe(new DisposableObserver<VideosResponse>() {
            @Override
            public void onNext(@NonNull VideosResponse videosResponse) {
                try{
                if (videosResponse.getMrt7al().isSuccess()){
                    videosRespons.setMrt7al(videosResponse.getMrt7al());
                    getNavigator().onResponse(true,videosResponse);
                } else {
                    getNavigator().onResponse(false,videosResponse);
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


    private VideosInterface getNavigator() {
        return mNavigator.get();
    }

    private void setNavigator(VideosInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}