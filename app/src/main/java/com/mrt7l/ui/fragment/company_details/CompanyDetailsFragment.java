package com.mrt7l.ui.fragment.company_details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.R;
import com.mrt7l.databinding.CompanyDetailsFragmentBinding;
import com.mrt7l.databinding.PolicyDialogBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.adapter.ItemBusAdapter;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import com.mrt7l.utils.Constants;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;

public class CompanyDetailsFragment extends Fragment implements CompanyDetailsInterface, TripsAdapter.ClickListener, ItemBusAdapter.ClickListener {

    private CompanyDetailsViewModel mViewModel;
      public static CompanyDetailsFragment newInstance() {
        return new CompanyDetailsFragment();
    }
    private CompanyDetailsFragmentBinding binding;
    StaggeredGridLayoutManager gridLayoutManager;
    private ImagesAdapter mAdapter;
    private Calendar mDepartDateCalendar;
     private LinearLayoutManager currentManager;
    private MyFavouriteResponse favouriteResponse;
    private int page = 1;
    private String searchDate;
    private CompanyDetailsResponse detailsResponse = CompanyDetailsResponse.getInstance();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.company_details_fragment, container, false);
        currentManager = new LinearLayoutManager(getActivity());
        detailsResponse = CompanyDetailsResponse.getInstance();
        binding.tripsRecycler.setLayoutManager(currentManager);
        favouriteResponse = MyFavouriteResponse.getInstance();
        mViewModel = new ViewModelProvider(this).get(CompanyDetailsViewModel.class);
        if (mDepartDateCalendar == null) {
            mDepartDateCalendar = Calendar.getInstance();
        }
        if (detailsResponse.getMrt7al() == null) {
            assert getArguments() != null;
            String companyID = getArguments().getString("companyID");
            mViewModel.init(this, companyID, String.valueOf(
                    new PreferenceHelper(requireActivity()).getTOKEN()));
        } else {
            if (detailsResponse.getMrt7al().getData() != null) {
                binding.address.setText(detailsResponse.getMrt7al().getData().getAddress());
                binding.bankStatement.setText(detailsResponse.getMrt7al().getData().getBank_account());
                binding.busType.setText(getString(R.string.txt_BusType) + " : " +
                        detailsResponse.getMrt7al().getData().getTypeOfBuses());
                if (detailsResponse.getMrt7al().getData().getCompany_photos().size() >0) {
                    ImagePagerAdapter adapterView = new ImagePagerAdapter(requireActivity(),
                            detailsResponse.getMrt7al().getData().getCompany_photos());
                    binding.viewPageAndroid.setAdapter(adapterView);
                    gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                    binding.bottomGalleryRecyclerView.setLayoutManager(gridLayoutManager);
                    binding.pagerBackground.setVisibility(View.GONE);
                    binding.viewPageAndroid.setVisibility(View.VISIBLE);
                    binding.bottomGalleryRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    binding.pagerBackground.setVisibility(View.VISIBLE);
                    binding.viewPageAndroid.setVisibility(View.GONE);
                    binding.bottomGalleryRecyclerView.setVisibility(View.GONE);
                }
            }
            mAdapter = new ImagesAdapter(getActivity(), detailsResponse.getMrt7al().getData().getCompany_photos(), binding.viewPageAndroid);
            if (detailsResponse.getMrt7al().getData().getUser() != null) {
                if (detailsResponse.getMrt7al().getData().getUser().getRates() != null &&
                        detailsResponse.getMrt7al().getData().getUser().getRates().size() > 0) {
                    float rate = Float.parseFloat(String.valueOf(detailsResponse.getMrt7al().getData().getUser().getRates().get(0).getRateSum() /
                            detailsResponse.getMrt7al().getData().getUser().getRates().get(0).getRateCounts()));
                    binding.ratingBar.setRating(rate);
                    binding.ratingNumber.setText(String.valueOf(rate));
                } else {
                    binding.ratingBar.setRating(0);
                    binding.ratingNumber.setText("0");
                }
            } else {
                binding.ratingBar.setRating(0);
                binding.ratingNumber.setText("0");
            }
            binding.bottomGalleryRecyclerView.setAdapter(mAdapter);
            if (DashboardActivity.tvTitle != null){
                if (detailsResponse.getMrt7al().getData().getTrip_dates().size() > 0)
                    DashboardActivity.tvTitle.setText(detailsResponse.getMrt7al().getData().getName());
            }
            binding.policies.setOnClickListener(v -> showPolicyDialog(detailsResponse.getMrt7al().getData().getReservation_policy()
                    ,requireActivity()));
            binding.viewPageAndroid.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (binding.bottomGalleryRecyclerView.getLayoutManager() != null) {
                        binding.bottomGalleryRecyclerView.getLayoutManager().scrollToPosition(position);
                        mAdapter.setSelectedPosition(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (detailsResponse.getMrt7al().getData() != null){
                binding.companyName.setText(detailsResponse.getMrt7al().getData().getName());

                binding.details.setText(detailsResponse.getMrt7al().getData(). getCompany_info());
            }
            if (detailsResponse.getMrt7al().getData().
                    getLogo_pic()!= null)
                Glide.with(requireContext()).load("https://administrator.mrt7al.com/" +
                        detailsResponse.getMrt7al().getData().
                                getLogo_pic()).into(binding.logo);
            if (detailsResponse.getMrt7al().getData().getUser() != null ) {
                if (detailsResponse.getMrt7al().getData().getUser().getCity() != null) {
                    binding.city.setText(detailsResponse.getMrt7al().getData().getUser().getCity().getName());
                }
            }
            if (detailsResponse.getMrt7al().getData() != null &&
                    detailsResponse.getMrt7al().getData().getFavorite_companies() != null &&
                    detailsResponse.getMrt7al().getData().getFavorite_companies().size() >0){
                binding.favourite.setBackgroundResource(R.drawable.heart);
            } else {
                binding.favourite.setBackgroundResource(R.drawable.blank_heart);
            }
            binding.favourite.setOnClickListener(view -> {
                if (detailsResponse.getMrt7al().getData() != null &&
                        detailsResponse.getMrt7al().getData().getFavorite_companies()
                                != null && detailsResponse.getMrt7al().getData().getFavorite_companies().size()>0){
//                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.sending));
                    mViewModel.addToFavourite("Bearer " + new PreferenceHelper(requireActivity()).getTOKEN(),
                            String.valueOf(detailsResponse.getMrt7al().getData().getId()),String.valueOf(new PreferenceHelper(requireActivity())
                                    .getUSERID()),"dislike");
                } else {
//                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.sending));
                    mViewModel.addToFavourite("Bearer " + new PreferenceHelper(requireActivity()).getTOKEN(),
                            String.valueOf(detailsResponse.getMrt7al().getData().getId()),String.valueOf(new
                                    PreferenceHelper(requireActivity()).getUSERID()),"like");
                }
            });
            if (detailsResponse.getMrt7al().getData().getTrip_dates().size() > 0 ) {
                tripsAdapter = new TripsAdapter(detailsResponse.getMrt7al()
                        , this,requireActivity());
                binding.tripsRecycler.setAdapter(tripsAdapter);
                Date date = convertStringToDate(detailsResponse.getMrt7al().getData()
                        .getTrip_dates().get(0).getDatetime_from()
                        .replace("T"," ").replace("+03:00",""));
                if (date != null){
                    mDepartDateCalendar.setTime(date);
                    binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                    tripsAdapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
                }
            }
        }
        mRecentSearchList = new ArrayList<>();
        binding.ivPrevious.setOnClickListener(v -> {
            mRecentSearchList.clear();
            mDepartDateCalendar.add(Calendar.DATE, -1);
            binding.mainProgress.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
            binding.tripsRecycler.setVisibility(View.GONE);
            binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
            searchDate = updateSearchLabel();
            page =1;
            mViewModel.search( null,null,searchDate,null,null,null
                    ,null,null,null
                    ,String.valueOf(detailsResponse.getMrt7al().getData().getId())
                    ,String.valueOf(page));
        });
        binding.ivNext.setOnClickListener(view -> {
            mRecentSearchList.clear();
            mDepartDateCalendar.add(Calendar.DATE, 1);
            binding.mainProgress.setVisibility(View.VISIBLE);
            binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.
                    format(mDepartDateCalendar.getTime()));
            searchDate = updateSearchLabel();
            page =1;
            binding.noData.setVisibility(View.GONE);
            binding.tripsRecycler.setVisibility(View.GONE);
            mViewModel.search( null,null,searchDate,null,null,null
                    ,null,null,null
                    ,String.valueOf(detailsResponse.getMrt7al().getData().getId())
                    ,String.valueOf(page));
        });
        binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.
                format(mDepartDateCalendar.getTime()));
         return binding.getRoot();
    }


    private String updateSearchLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(mDepartDateCalendar.getTime());
    }

    @Override
    public void onDestroy() {
        CompanyDetailsResponse.removeInstance();
        Log.v("removeinstance","");
        super.onDestroy();
    }




    ArrayList <SearchTripsResponse.Mrt7alBean.DataBean> mRecentSearchList;
    TripsAdapter tripsAdapter;
    @Override
    public void onResponse(boolean isSuccess, CompanyDetailsResponse homeResponse) {
        try{
        DialogsHelper.removeProgressDialog();
        detailsResponse = homeResponse;
        if (isSuccess){
            if (detailsResponse.getMrt7al().getData() != null) {
                binding.address.setText(detailsResponse.getMrt7al().getData().getAddress());
                binding.bankStatement.setText(detailsResponse.getMrt7al().getData().getBank_account());
                binding.busType.setText(getString(R.string.txt_BusType) + " : " +
                        detailsResponse.getMrt7al().getData().getTypeOfBuses());
                if (detailsResponse.getMrt7al().getData().getCompany_photos().size() >0) {
                    ImagePagerAdapter adapterView = new ImagePagerAdapter(requireActivity(),
                            detailsResponse.getMrt7al().getData().getCompany_photos());
                    binding.viewPageAndroid.setAdapter(adapterView);
                    gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                    binding.bottomGalleryRecyclerView.setLayoutManager(gridLayoutManager);
                    binding.pagerBackground.setVisibility(View.GONE);
                    binding.viewPageAndroid.setVisibility(View.VISIBLE);
                    binding.bottomGalleryRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    binding.pagerBackground.setVisibility(View.VISIBLE);
                    binding.viewPageAndroid.setVisibility(View.GONE);
                    binding.bottomGalleryRecyclerView.setVisibility(View.GONE);
                }
            }
            mAdapter = new ImagesAdapter(getActivity(), detailsResponse.getMrt7al().getData().getCompany_photos(), binding.viewPageAndroid);
            if (detailsResponse.getMrt7al().getData().getUser() != null ){
                if (detailsResponse.getMrt7al().getData(). getUser().getRates() != null) {
                if (detailsResponse.getMrt7al().getData().getUser().getRates().size() > 0) {
                    float rate = Float.parseFloat(String.valueOf(detailsResponse.getMrt7al().getData().getUser().getRates().get(0).getRateSum() /
                            detailsResponse.getMrt7al().getData().getUser().getRates().get(0).getRateCounts()));
                    binding.ratingBar.setRating(rate);
                    binding.ratingNumber.setText(String.valueOf(rate));
                } else {
                    binding.ratingBar.setRating(0);
                    binding.ratingNumber.setText("0");
                }
            } else {
                binding.ratingBar.setRating(0);
                binding.ratingNumber.setText("0");
            }
            } else {
                binding.ratingBar.setRating(0);
                binding.ratingNumber.setText("0");
            }
            binding.bottomGalleryRecyclerView.setAdapter(mAdapter);
            if (DashboardActivity.tvTitle != null){
                if (detailsResponse.getMrt7al().getData().getTrip_dates().size() > 0)
                DashboardActivity.tvTitle.setText(detailsResponse.getMrt7al().getData().getName());
            }
            binding.policies.setOnClickListener(v -> showPolicyDialog(detailsResponse.getMrt7al().getData().getReservation_policy()
                    ,requireActivity()));
            binding.viewPageAndroid.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (binding.bottomGalleryRecyclerView.getLayoutManager() != null) {
                        binding.bottomGalleryRecyclerView.getLayoutManager().scrollToPosition(position);
                        mAdapter.setSelectedPosition(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (detailsResponse.getMrt7al().getData() != null){
                binding.companyName.setText(detailsResponse.getMrt7al().getData().getName());

            binding.details.setText(detailsResponse.getMrt7al().getData(). getCompany_info());
            }
            if (detailsResponse.getMrt7al().getData().
                    getLogo_pic()!= null)
            Glide.with(requireContext()).load("https://administrator.mrt7al.com/" +
                    detailsResponse.getMrt7al().getData().
                            getLogo_pic()).into(binding.logo);
            if (detailsResponse.getMrt7al().getData().getUser() != null){
                if (detailsResponse.getMrt7al().getData().getUser().getCity()  != null) {
               binding.city.setText(detailsResponse.getMrt7al().getData().getUser().getCity().getName());
           }
            }
            if (detailsResponse.getMrt7al().getData() != null &&
                    detailsResponse.getMrt7al().getData().getFavorite_companies() != null &&
                    detailsResponse.getMrt7al().getData().getFavorite_companies().size() >0){
                binding.favourite.setBackgroundResource(R.drawable.heart);
            } else {
                binding.favourite.setBackgroundResource(R.drawable.blank_heart);
            }
            binding.favourite.setOnClickListener(view -> {
                if (detailsResponse.getMrt7al().getData() != null &&
                        detailsResponse.getMrt7al().getData().getFavorite_companies()
                        != null && detailsResponse.getMrt7al().getData().getFavorite_companies().size()>0){
//                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.sending));
                    mViewModel.addToFavourite("Bearer " + new PreferenceHelper(requireActivity()).getTOKEN(),
                            String.valueOf(detailsResponse.getMrt7al().getData().getId()),String.valueOf(new PreferenceHelper(requireActivity())
                                    .getUSERID()),"dislike");
                } else {
//                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.sending));
                    mViewModel.addToFavourite("Bearer " + new PreferenceHelper(requireActivity()).getTOKEN(),
                            String.valueOf(detailsResponse.getMrt7al().getData().getId()),String.valueOf(new
                                    PreferenceHelper(requireActivity()).getUSERID()),"like");
                }
            });
            if (detailsResponse.getMrt7al().getData().getTrip_dates().size() > 0 ) {
                tripsAdapter = new TripsAdapter(detailsResponse.getMrt7al()
                        , this,requireActivity());
                binding.tripsRecycler.setAdapter(tripsAdapter);
                Date date = convertStringToDate(detailsResponse.getMrt7al().getData()
                        .getTrip_dates().get(0).getDatetime_from()
                        .replace("T"," ").replace("+03:00",""));
                if (date != null){
                    mDepartDateCalendar.setTime(date);
                    binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                    tripsAdapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
                }
            }
        }
        } catch (NullPointerException | IllegalStateException a){
            a.printStackTrace();
        }
    }
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
    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        DialogsHelper.removeProgressDialog();
        binding.mainProgress.setVisibility(View.GONE);
        binding.noData.setVisibility(View.VISIBLE);
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
                        Toast.makeText(requireActivity(),
                                errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
     }

    @Override
    public void onFavouriteAd(boolean success, String message) {
        favouriteResponse.setMrt7al(null);
        if (success){
            if (message.equals("dislike")){
                binding.favourite.setBackgroundResource(R.drawable.blank_heart);
                detailsResponse.getMrt7al().getData().getFavorite_companies().clear();
            } else {
                binding.favourite.setBackgroundResource(R.drawable.heart);
                CompanyDetailsResponse.Mrt7alBean.DataBean.FavoriteCompaniesBean bean =
                        new CompanyDetailsResponse.Mrt7alBean.DataBean.FavoriteCompaniesBean();
                bean.setStatus("like");
                detailsResponse.getMrt7al().getData().getFavorite_companies().add(bean);
            }
        }
    }
    ItemBusAdapter adapter;
    @Override
    public void onSearchTrips(boolean success, SearchTripsResponse homeResponse) {
        binding.mainProgress.setVisibility(View.GONE);
        if (homeResponse.getMrt7al().getData() != null) {
                mRecentSearchList.clear();
                mRecentSearchList.addAll(homeResponse.getMrt7al().getData());
                binding.tripsRecycler.setLayoutManager(currentManager);
                adapter = new ItemBusAdapter(requireActivity(),mRecentSearchList,this);
//                adapter = new ItemBusAdapter(requireActivity(), response.getMrt7al().getData(),this);
                binding.tripsRecycler.setAdapter(adapter);
                Date date = convertStringToDate(homeResponse.getMrt7al().getData().
                        get(0).getDatetime_from()
                        .replace("T"," ").replace("+03:00",""));
                if (date != null){
                    mDepartDateCalendar.setTime(date);
                    binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                    adapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
                }

            binding.noData.setVisibility(View.GONE);
            binding.mainProgress.setVisibility(View.GONE);
            binding.tripsRecycler.setVisibility(View.VISIBLE);
         } else  {
             binding.tripsRecycler.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
            binding.mainProgress.setVisibility(View.GONE);
        }
        homeResponse.setMrt7al(null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    public void showPolicyDialog(String message, Activity activity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        PolicyDialogBinding policyDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.policy_dialog, null, false);
        policyDialogBinding.content.setText(message);
        policyDialogBinding.okButton.setOnClickListener(v -> {
            dialog.cancel();
        });
        policyDialogBinding.close.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(policyDialogBinding.getRoot());
        dialog.show();
    }


    @Override
    public void onReserve(CompanyDetailsResponse.Mrt7alBean.DataBean dataBean, int pos) {
        if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
            Gson gson = new Gson();
            String model = gson.toJson(dataBean, CompanyDetailsResponse.Mrt7alBean.DataBean.class);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putString("pos", String.valueOf(pos));
            bundle.putString("page", "details");
            Navigation.findNavController(requireActivity(), R.id.main_fragment).
                    navigate(R.id.action_companyDetailsFragment_to_addPassengersFragment, bundle);
        } else {
            DialogsHelper.showLoginDialog(getString(R.string.please_login),requireActivity());
        }
    }

    @Override
    public void onReserve(SearchTripsResponse.Mrt7alBean.DataBean dataBean, int pos) {
        if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
            Gson gson = new Gson();
            String model = gson.toJson(dataBean, SearchTripsResponse.Mrt7alBean.DataBean.class);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putString("pos", String.valueOf(pos));
            bundle.putString("page", "search");
            Navigation.findNavController(requireActivity(), R.id.main_fragment).
                    navigate(R.id.action_companyDetailsFragment_to_addPassengersFragment, bundle);
        } else {
            DialogsHelper.showLoginDialog(getString(R.string.please_login),requireActivity());
        }
    }
}