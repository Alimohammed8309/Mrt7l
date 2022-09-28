package com.mrt7l.ui.fragment.notifications;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.NotificationsFragmentBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;

public class NotificationsFragment extends Fragment implements NotificationsInterface {

    private NotificationsViewModel mViewModel;
    private PreferenceHelper preferenceHelper;
    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }
    NotificationsFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.notifications_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        preferenceHelper = new PreferenceHelper(requireActivity());
         mViewModel.init(this,preferenceHelper.getTOKEN(),1);

        return binding.getRoot();
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onResponse(boolean isSuccess, NotificationsResponse contactUsModel) {
        binding.mainProgress.setVisibility(View.GONE);
        if (isSuccess){
            binding.rvNotification.setLayoutManager(new LinearLayoutManager(requireActivity()));
            NotificationsAdapter adapter = new NotificationsAdapter(requireActivity(),contactUsModel.getMrt7al().getData());
            binding.rvNotification.setAdapter(adapter);
            binding.noData.setVisibility(View.GONE);
            binding.rvNotification.setVisibility(View.VISIBLE);
        } else {
            binding.noData.setVisibility(View.VISIBLE);
            binding.rvNotification.setVisibility(View.GONE);
        }
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        binding.mainProgress.setVisibility(View.GONE);
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen()) {
                        if(!requireActivity().isFinishing())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                    }
                } else if (code == 404) {
                    try {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
//                        Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
        binding.noData.setVisibility(View.VISIBLE);
        binding.rvNotification.setVisibility(View.GONE);
    }

    @Override
    public void onFavouriteAd(boolean isSuccess) {

    }
}