package com.mrt7l.ui.fragment.search_trips;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.SearchTripsFragmentBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.activity.SignInActivity;
import com.mrt7l.ui.adapter.ItemBusAdapter;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
import com.mrt7l.utils.Constants;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;

public class SearchTripsFragment extends Fragment implements SearchInterface,
        View.OnClickListener, ItemBusAdapter.ClickListener,FilterInterface{

    private SearchTripsViewModel mViewModel;

    public static SearchTripsFragment newInstance() {
        return new SearchTripsFragment();
    }
    SearchTripsFragmentBinding binding;
    private Calendar mDepartDateCalendar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.search_trips_fragment, container,
                false);
        binding.ivPrevious.setOnClickListener(view -> {
                 mDepartDateCalendar.add(Calendar.DATE, -1);
                binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER
                        .format(mDepartDateCalendar.getTime()));
        });
        binding.ivBack.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
        });
        mDepartDateCalendar = Calendar.getInstance();
        binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));

        binding.ivPrevious.setOnClickListener(v -> {
            binding.rvBus.setVisibility(View.GONE);
            binding.mainProgress.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
            mDepartDateCalendar.add(Calendar.DATE, -1);
             binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
            date = updateLabel();
            page =1;
                mViewModel.search(from, to, date, time, count, busType,
                        isDirect, from_price, toPrice,
                        companyID != null ? companyID : "", String.valueOf(page), "");

        }
            );



        binding.ivNext.setOnClickListener(view -> {
            binding.rvBus.setVisibility(View.GONE);
            binding.mainProgress.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
            mDepartDateCalendar.add(Calendar.DATE, 1);
            binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
            date = updateLabel();
            page =1;
            if(date.equals("")) {
                mViewModel.search(from, to, date, time, count, busType,
                        isDirect, from_price, toPrice,
                        companyID != null ? companyID : "", String.valueOf(page), "");

            } else {
                mViewModel.search(from, to, date, time, count, busType,
                        isDirect, from_price, toPrice,
                        companyID != null ? companyID : "", String.valueOf(page), "");
            }

        });

        response= SearchTripsResponse.getInstance();
        mViewModel = new ViewModelProvider(this).get(SearchTripsViewModel.class);
        mViewModel.init(this);
        if (response.getMrt7al() == null ){
            if (getArguments() != null){
                from = getArguments().getString("from");
                to = getArguments().getString("to");
                time = getArguments().getString("time");
                date = getArguments().getString("date");
                count = getArguments().getString("count");
                companyID = getArguments().getString("companyID");
                busType = getArguments().getString("busType");
                    mViewModel.search(from, to, date, time, count, busType,
                            isDirect, from_price, toPrice,
                            companyID != null ? companyID : "", String.valueOf(page), "");
            }
        } else if (response.getMrt7al().getData() != null){
//            if (getArguments() != null){
//                from = getArguments().getString("from");
//                to = getArguments().getString("to");
//                time = getArguments().getString("time");
//                date = getArguments().getString("date");
//                count = getArguments().getString("count");
//                companyID = getArguments().getString("companyID");
//                busType = getArguments().getString("busType");
////            DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));
//                    mViewModel.search(from, to, date, time, count, busType,
//                            isDirect, from_price, toPrice,
//                            companyID != null ? companyID : "", String.valueOf(page), "");
//            }
            binding.progresss.setVisibility(View.GONE);
            binding.mainProgress.setVisibility(View.GONE);
            dealerListManager = new LinearLayoutManager(requireActivity());
            binding.rvBus.setLayoutManager(dealerListManager);
            adapter = new ItemBusAdapter(requireActivity(),response.getMrt7al().getData(),this);
            binding.rvBus.setAdapter(adapter);
            Date date = convertStringToDate(response.getMrt7al().getData().get(0).getDatetime_from()
                    .replace("T"," ").replace("+03:00",""));
            if (date != null){
                mDepartDateCalendar.setTime(date);
                binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                if (page ==1)
                    adapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
            }

        binding.tvAvailableBus.setText("متوفر " +adapter.getItemCount() + " رحلات ");
        binding.noData.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        binding.rvBus.setVisibility(View.VISIBLE);
        binding.tvAvailableBus.setVisibility(View.VISIBLE);
        }

        binding.ivFilter.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("isDirect",isDirect);
            bundle.putString("fromPrice",from_price);
            bundle.putString("toprice",toPrice);
            bundle.putString("companyId" ,companyID);
            bundle.putString("busType",busType);
            try {
                Navigation.findNavController(requireActivity(), R.id.main_fragment)
                        .navigate(R.id.action_searchTripsFragment_to_filterBottomSheet, bundle);
            } catch (IllegalArgumentException e) {
            }

        });
        dealerListManager = new LinearLayoutManager(requireActivity());
        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            View view =  binding.scrollView.getChildAt(binding.scrollView.getChildCount() - 1);

            int diff = (view.getBottom() - (binding.scrollView.getHeight() + binding.scrollView
                    .getScrollY()));

            if (diff == 0) {
                // your pagination code
                if (!isLoadingData) {
                    //new updateData().execute();
                    isLoadingData = true;
                    date =updateLabel();
                    page =page+1;
                    try {
                        if (response.getMrt7al().getPagination().getPageCount() >= page) {
                            if (date.equals("")) {
                                mViewModel.search(from, to, date, time, count, busType,
                                        isDirect, from_price, toPrice,
                                        companyID != null ? companyID : "", String.valueOf(page), "");

                            } else {
                                mViewModel.search(from, to, date, time, count, busType,
                                        isDirect, from_price, toPrice,
                                        companyID != null ? companyID : "", String.valueOf(page), "");
                            }
                            binding.progresss.setVisibility(View.VISIBLE);

                        }
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });


//        binding.rvBus.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                                              public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                                  super.onScrolled(recyclerView, dx, dy);
//                                              }
//
//                                              @Override
//                                              public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                                  super.onScrollStateChanged(recyclerView, newState);
//                                                  visibleItemCount = dealerListManager.getChildCount();
//                                                  totalItemCount = dealerListManager.getItemCount();
//                                                  pastVisiblesItems = dealerListManager.findFirstVisibleItemPosition();
//                                                  if (pastVisiblesItems > mLastFirstVisibleItem) {
//                                                      isScrollDown = true;
//                                                  } else if (pastVisiblesItems < mLastFirstVisibleItem) {
//                                                      isScrollDown = false;
//                                                  }
//
//                                                  mLastFirstVisibleItem = pastVisiblesItems;
//                                                  if (isScrollDown)
//                                                      if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 7) {
//                                                          if (!isLoadingData) {
//                                                              //new updateData().execute();
//                                                              isLoadingData = true;
//                                                              page =page++;
//                                                              date = updateLabel();
//                                                              mViewModel.search(from,to,date,time,count,busType,
//                                                                      isDirect,from_price,toPrice,
//                                                                      companyID!=null?companyID:"",String.valueOf(page));
//                                                              binding.progresss.setVisibility(View.VISIBLE);
//                                                          }
//                                                      }
//                                              }
//
//                                          }
//        );
        return binding.getRoot();
    }
    private String from,to,time="",date="",count="0",companyID = "0";
    private LinearLayoutManager dealerListManager;
    private int page = 1;
    private boolean isLoadingData =false ,isScrollDown =true;

    String busType = "";
    private String updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(mDepartDateCalendar.getTime());
    }
    SearchTripsResponse response;
    private ItemBusAdapter adapter;
    private Date convertStringToDate (String stringDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = null ;
        try {
             date = format.parse(stringDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return date;
    }


    @Override
    public void onDestroy() {
        SearchTripsResponse.removeInstance();
        super.onDestroy();
    }

    @Override
    public void onResponse(boolean isSuccess, SearchTripsResponse homeResponse) {
        try{
            response = homeResponse;
        binding.mainProgress.setVisibility(View.GONE);
        if (homeResponse.getMrt7al().getData() != null) {
            if (page > 1) {
                adapter.notifyDataSetChanged();
            } else {
                binding.rvBus.setLayoutManager(dealerListManager);
                adapter = new ItemBusAdapter(requireActivity(),response.getMrt7al().getData(),this);
                binding.rvBus.setAdapter(adapter);
                Date date = convertStringToDate(response.getMrt7al().getData().get(0).getDatetime_from()
                        .replace("T"," ").replace("+03:00",""));
               if (date != null){
                   mDepartDateCalendar.setTime(date);
                   binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                   if (page ==1)
                   adapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
               }
            }
            binding.tvAvailableBus.setText("متوفر " +adapter.getItemCount() + " رحلات ");
            binding.noData.setVisibility(View.GONE);
            binding.progresss.setVisibility(View.GONE);
            binding.rvBus.setVisibility(View.VISIBLE);
            binding.tvAvailableBus.setVisibility(View.VISIBLE);
        } else  {
            binding.tvAvailableBus.setVisibility(View.GONE);
            binding.rvBus.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
            binding.progresss.setVisibility(View.GONE);
        }
        } catch (NullPointerException | IllegalStateException a){
            a.printStackTrace();
        }
        isLoadingData = false;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (DashboardActivity.tvTitle != null){
            DashboardActivity.tvTitle.setText(getString(R.string.search_h));
        }
        ContextCompat.registerReceiver(requireActivity(), someBroadcastReceiver, new IntentFilter("searchFilter"), ContextCompat.RECEIVER_NOT_EXPORTED);
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        binding.mainProgress.setVisibility(View.GONE);
        if (ConnectivityReceiver.isConnected()){
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else if (code == 404) {
                    Log.v("404", "my booking fragment page not found");

                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException | JsonSyntaxException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
//                    Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                        if (adapter != null)
                            adapter.clearData();
                        binding.noData.setVisibility(View.VISIBLE);
                        binding.rvBus.setVisibility(View.GONE);
                    }
//                    else {
//                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
        binding.tvAvailableBus.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        if (response.getMrt7al() != null){
            if (response.getMrt7al().getData().size() ==0) {
                binding.noData.setVisibility(View.VISIBLE);
                binding.rvBus.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onFavouriteAd(boolean success, String message) {
//        DialogsHelper.removeProgressDialog();
        binding.progresss.setVisibility(View.GONE);

    }

    @Override
    public void showProgress() {
//        DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void onReserve(SearchTripsResponse.Mrt7alBean.DataBean dataBean,int pos) {
        if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
            CompanyDetailsResponse.Mrt7alBean.DataBean bean = new CompanyDetailsResponse.Mrt7alBean.DataBean();
            Gson gson = new Gson();
            String model = gson.toJson(dataBean,SearchTripsResponse.Mrt7alBean.DataBean.class);
            Bundle b = new Bundle();
            b.putString("model",model);
            b.putString("page","search");
            b.putString("pos",String.valueOf(pos));
            Navigation.findNavController(requireActivity(),R.id.main_fragment).
                    navigate(R.id.action_searchTripsFragment_to_addPassengersFragment,b);
        } else {
            DialogsHelper.showLoginDialog(getString(R.string.please_login),requireActivity());
        }
        }


    @Override
    public void onFilterDataRetrieved(String from_price, String toPrice, String isDirect,
                                      String busType, int companyId) {
//        DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));

    }
    String from_price = "0",toPrice= "0",isDirect= "";

    private final BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if (Objects.equals(intent.getAction()
                     , "searchFilter")){

                     from_price = intent.getStringExtra("fromPrice");
                     toPrice = intent.getStringExtra("toPrice");
                 if(!Objects.equals(intent.getStringExtra("isDirect"), ""))
                     isDirect = intent.getStringExtra("isDirect");
                 if(!Objects.equals(intent.getStringExtra("busType"), ""))
                     busType = intent.getStringExtra("busType");
                 if(!Objects.equals(intent.getStringExtra("selectedCompanyId"), ""))
                 companyID = intent.getStringExtra("selectedCompanyId");
                 page =1;
                 binding.rvBus.setVisibility(View.GONE);
                 binding.mainProgress.setVisibility(View.VISIBLE);
                 if(date.equals("")) {
                     mViewModel.search(from, to, date, time, count, busType,
                             isDirect, from_price, toPrice,
                             companyID != null ? companyID : "", String.valueOf(page), "");

                 } else {
                     mViewModel.search(from, to, date, time, count, busType,
                             isDirect, from_price, toPrice,
                             companyID != null ? companyID : "", String.valueOf(page), "");
                 }
             }
        }
    };

}