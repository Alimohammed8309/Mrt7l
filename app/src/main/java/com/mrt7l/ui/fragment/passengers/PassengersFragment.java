package com.mrt7l.ui.fragment.passengers;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.PassengersFragmentBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
import com.mrt7l.ui.fragment.reservation.PassengerModel;
import com.mrt7l.ui.fragment.reservation.ReservationPreviewModel;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;

public class PassengersFragment extends Fragment implements PassengersInterface, AddPassengerBottomDialog.ChoosePassengerDialogInterface, HandlePassengersInterface {

    private PassengersViewModel mViewModel;
    private LinearLayoutManager layoutManager;
    private int userid;
    public static PassengersFragment newInstance() {
        return new PassengersFragment();
    }
    private PassengersFragmentBinding binding;
    private PassengersResponse passengersResponse;
    private String token;
    private boolean isTrip = false;
    private Receiver passengersReciver = new Receiver();
    private int tripPosition;
     private SearchTripsResponse.Mrt7alBean.DataBean searchBean;
     private CompanyDetailsResponse.Mrt7alBean.DataBean detailsBean;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

                binding = DataBindingUtil.inflate(inflater, R.layout.passengers_fragment, container, false);
        binding.ivNotification.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(
                    R.id.action_notifications
            );
        });
        if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
            binding.noData.setVisibility(View.VISIBLE);
            binding.passengerRecycler.setVisibility(View.GONE);
        } else {

            layoutManager = new LinearLayoutManager(requireActivity());
            if (getArguments() != null) {
                if (getArguments().getString("model") != null) {
                    binding.reserveNow.setVisibility(View.VISIBLE);
                    isTrip = true;
                    String m = getArguments().getString("model");
                    Gson gson = new Gson();
                    if (Objects.equals(getArguments().getString("page"), "search")) {
                        searchBean = gson.fromJson(m, SearchTripsResponse.Mrt7alBean.DataBean.class);
                    } else if (Objects.equals(getArguments().getString("page"), "details")) {
                        tripPosition = Integer.parseInt(requireArguments().getString("pos"));
                        detailsBean = gson.fromJson(m, CompanyDetailsResponse.Mrt7alBean.DataBean.class);
                    }
                }
            }
            binding.reserveNow.setOnClickListener(view -> {

                ReservationPreviewModel previewModel = ReservationPreviewModel.getInstance();
                previewModel.getPassengerModels().clear();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).isSelected()) {
                        PassengerModel model = new PassengerModel();
                        model.setId(dataBeans.get(i).getId());
                        model.setPhone(dataBeans.get(i).getMobile());
                        model.setPassportNumber(dataBeans.get(i).getPassport_id());
                        model.setName(dataBeans.get(i).getFull_name());
                        model.setGender(dataBeans.get(i).getGender());
                        model.setPassengerType(dataBeans.get(i).getPassangerType());
                        previewModel.getPassengerModels().add(model);
                    }
                }

                if (searchBean != null) {
                    previewModel.setFrom(searchBean.getFrom_city().getName());
                    previewModel.setCompanyID(searchBean.getCompany_id());
                    previewModel.setTo(searchBean.getTo_city().getName());
                    previewModel.setPricePerPerson(searchBean.getPrice());
                    previewModel.setTax(Integer.parseInt(searchBean.getTax()));
                    previewModel.setTripDate(searchBean.getDatetime_from());
                    previewModel.setTripID(searchBean.getId());
                    previewModel.setBankAccountNumber(searchBean.getCompany().getBank_account());
                    previewModel.setCompanyAddress(searchBean.getCompany().getAddress());
                    previewModel.setCompanyImage(searchBean.getCompany().getLogo_pic());
                    previewModel.setCompanyName(searchBean.getCompany().getName());
                    previewModel.setAvailableCount(searchBean.getAvailable_count());
                    previewModel.setCompanyCity(searchBean.getCompany().getAddress());
//                    previewModel.setCompanyCity(searchBean.getCompany().getUser().getCity());
                } else if (detailsBean != null) {
                    previewModel.setFrom(detailsBean.getTrip_dates().get(tripPosition).getFrom_city().getName());
                    previewModel.setTo(detailsBean.getTrip_dates().get(tripPosition).getTo_city().getName());
                    previewModel.setCompanyID(detailsBean.getTrip_dates().get(tripPosition).getCompany_id());
                    previewModel.setPricePerPerson(detailsBean.getTrip_dates().get(tripPosition).getPrice());
//                    previewModel.setTax(detailsBean.getTrip_dates().get(tripPosition).gett());
                    previewModel.setTripDate(detailsBean.getTrip_dates().get(tripPosition).getDatetime_from());
                    previewModel.setTripID(detailsBean.getTrip_dates().get(tripPosition).getId());
                    previewModel.setBankAccountNumber(detailsBean.getBank_account());
                    previewModel.setCompanyAddress(detailsBean.getAddress());
                    previewModel.setCompanyImage(detailsBean.getLogo_pic());
                    previewModel.setCompanyName(detailsBean.getName());
                    previewModel.setAvailableCount(detailsBean.getTrip_dates().get(tripPosition).getAvailable_count());
                    previewModel.setCompanyCity(detailsBean.getUser().getCity().getName());
                }
                Gson gson = new Gson();
                String model = gson.toJson(previewModel, ReservationPreviewModel.class);
                Bundle bundle = new Bundle();
                bundle.putString("pModel", model);
                Navigation.findNavController(requireActivity(), R.id.main_fragment)
                        .navigate(R.id.action_passengersFragment_to_reserveForMeFragment, bundle);
            });
            binding.addPassengerBtn.setOnClickListener(view -> {
                if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
                    DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment);
                    navController.navigate(R.id.action_passengersFragment_to_addPassengerFragment);
                }
            });
            passengersResponse = PassengersResponse.getInstance();

            mViewModel = new ViewModelProvider(this).get(PassengersViewModel.class);
            mViewModel.init(this);
            token = new PreferenceHelper(requireActivity()).getTOKEN();
            userid = new PreferenceHelper(requireActivity()).getUSERID();
            if (passengersResponse.getMrt7al() == null) {
//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
//                mViewModel.getPassengers(page, token);
                binding.mainProgress.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                new PreferenceHelper(requireActivity()).setReloadProfile(true);
            } else {
                dataBeans = passengersResponse.getMrt7al().getData();
                if (dataBeans.size() > 0) {
                    adapter = new Passengers_adapter(requireActivity(),  dataBeans, this, isTrip);
                    binding.passengerRecycler.setLayoutManager(layoutManager);
                    binding.passengerRecycler.setAdapter(adapter);
                    binding.noData.setVisibility(View.GONE);
                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.passengerRecycler.setVisibility(View.GONE);
                }
            }
        }
        return binding.getRoot();
    }

   private int page = 1;
    @Override
    public void onResume() {
        super.onResume();
        ContextCompat.registerReceiver(requireActivity(), passengersReciver, new IntentFilter("searchFilter"), ContextCompat.RECEIVER_NOT_EXPORTED);

        if (new PreferenceHelper(requireActivity()).isReloadProfile()){
             binding.mainProgress.setVisibility(View.VISIBLE);
             binding.passengerRecycler.setVisibility(View.GONE);
             if(mViewModel == null) {
                 mViewModel = new ViewModelProvider(this).get(PassengersViewModel.class);
                 mViewModel.init(this);
             }
            mViewModel.getPassengers(page, token);
            new PreferenceHelper(requireActivity()).setReloadProfile(false);
        }
    }

    Passengers_adapter adapter;
    private ArrayList<PassengersResponse.Mrt7alBean.DataBean> dataBeans;
    @Override
    public void onResponse(boolean isSuccess, PassengersResponse registerResponse) {
        binding.mainProgress.setVisibility(View.GONE);
        try{
        if (isSuccess) {
            dataBeans = new ArrayList<>();
            dataBeans = registerResponse.getMrt7al().getData();
            if (dataBeans.size() > 0) {
                adapter = new Passengers_adapter(requireActivity(),   dataBeans, this, isTrip);
                binding.passengerRecycler.setLayoutManager(layoutManager);
                binding.passengerRecycler.setAdapter(adapter);
                binding.passengerRecycler.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
            } else {
                binding.noData.setVisibility(View.VISIBLE);
                binding.passengerRecycler.setVisibility(View.GONE);
            }
        }
        }catch (IllegalStateException e){
            //e.printStackTrace();
        }
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        try{
        binding.mainProgress.setVisibility(View.GONE);
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else if (code == 404) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
        binding.noData.setVisibility(View.VISIBLE);
        binding.passengerRecycler.setVisibility(View.GONE);
        }catch (IllegalStateException e){
            //e.printStackTrace();
        }
    }
    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && !methodName.isEmpty()) {
                if (methodName.equals("passengerAdded")) {
                    mViewModel.getPassengers(page, token);
                }
            }
        }
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(passengersReciver);
        super.onPause();

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(passengersReciver);
        super.onDestroy();

    }

    @Override
    public void onPassengerDeleted(boolean success,String message) {
        DialogsHelper.removeSimpleProgressDialog();
        try{
        if (success){
            dataBeans.remove(PassengerToDelete);
            adapter.notifyDataSetChanged();
            if (dataBeans.size()  == 0){
                binding.noData.setVisibility(View.VISIBLE);
                binding.passengerRecycler.setVisibility(View.GONE);
            }
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        } else {
            DialogsHelper.showErrorDialog(message,requireActivity());
        }
    }catch (IllegalStateException e){
        //e.printStackTrace();
    }
    }

    @Override
    public void onNewPassenger() {

    }

    @Override
    public void onPastPassenger() {

    }

    @Override
    public void OnEdit(PassengersResponse.Mrt7alBean.DataBean registerResponse) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.main_fragment);
        Gson gson = new Gson();
        String model = gson.toJson(registerResponse);
        Bundle bundle = new Bundle();
        bundle.putString("model",model);
        navController.navigate(R.id.action_passengersFragment_to_editPassengerFragment,bundle);
    }
    private int PassengerToDelete = 0;
    @Override
    public void OnDelete(PassengersResponse.Mrt7alBean.DataBean registerResponse,int pos) {
        DialogsHelper.showSimpleProgressDialog(getString(R.string.deleting),requireActivity());
        PassengerToDelete = pos;
        mViewModel.removePassenger(String.valueOf(registerResponse.getId()),token);
    }

    @Override
    public void onPassengerChecked(PassengersResponse.Mrt7alBean.DataBean dataBean) {

    }


}