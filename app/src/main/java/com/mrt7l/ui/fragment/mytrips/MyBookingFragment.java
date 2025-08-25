package com.mrt7l.ui.fragment.mytrips;

import static com.mrt7l.helpers.ConnectivityReceiver.isConnected;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mrt7l.R;
import com.mrt7l.databinding.CancelDialogBinding;
import com.mrt7l.databinding.FragmentMybookingBinding;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.PendingReservationDialogBinding;
import com.mrt7l.databinding.PrintTicketFragmentBinding;
import com.mrt7l.databinding.RatingDialogBinding;
import com.mrt7l.databinding.RegisterErrorDialogBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.activity.PayActivity;
import com.mrt7l.ui.activity.SignInActivity;
import com.mrt7l.ui.fragment.about.AboutAppResponse;
import com.mrt7l.ui.fragment.print_ticket.PrintTicketAdapter;
import com.mrt7l.ui.fragment.reservation.ErrorResponse;
import com.mrt7l.ui.fragment.reservation.RequestPayModel;

import okhttp3.ResponseBody;

public class MyBookingFragment extends Fragment implements View.OnClickListener, MyTripsInterface,
        CurrentOrderAdapter.ClickListener, PrintTicketAdapter.ClickListener, PastOrderAdapter.RateListener {
    private TextView mTvCompleted, mTvCancel;
    private FragmentMybookingBinding binding;
    private CurrentOrderAdapter currentOrderAdapter;
    private PastOrderAdapter pastOrderAdapter;
    private CurrentOrdersResponse currentOrdersResponse;
    private PastOrdersResponse pastOrderResponse;
    private int currentOrdersPage = 1;
    private int pastOrdersPage =1;
    private int pastVisiblesItems, visibleItemCount, totalItemCount,mLastFirstVisibleItem = 0;
    private int pastpastVisiblesItems, pastvisibleItemCount, pasttotalItemCount,pastmLastFirstVisibleItem = 0;
    private boolean isLoadingData =false ,pastisLoadingData = false,isScrollDown =true,pastisScrollDown = true;
    private LinearLayoutManager currentManager,pastManager;
    private ActivityResultLauncher<Intent> webViewLauncher;
    private boolean isRefreshData =false;

    /* create view */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mybooking, container, false);
            mTvCompleted = binding.tvCompleted;
            mTvCancel = binding.tvCancelled;
            binding.ivNotification.setOnClickListener(view -> {
                Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(
                        R.id.action_notifications
                );
            });
//        binding.refreshButton.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
//                if (child instanceof RecyclerView) {
//                    RecyclerView recyclerView = (RecyclerView) child;
//                    // Return true if RecyclerView can scroll up, false otherwise
//                    return recyclerView.canScrollVertically(-1); // -1 indicates scrolling up
//                }
//                return false; // Default behavior if child is not a RecyclerView
//            }
//        });
            // Register Activity result launcher
            webViewLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Log.v("result", result.getData().toString());
                            viewModel.checkPayStatus(token, viewModel.requestPayModel.getMrt7al().getData().getId());
                        } else {
                            DialogsHelper.removeProgressDialog();
                            Toast.makeText(requireActivity(), "فشلت عملية الدفع من فضلك حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                             }
                    });
            binding.rvBooking.setNestedScrollingEnabled(false);
            binding.rvPast.setNestedScrollingEnabled(false);
            binding.refreshButton.setOnRefreshListener(() -> {
                binding.noData.setVisibility(View.GONE);
                isRefreshData = true;
                currentOrdersPage = 1;
//            ((DashboardActivity) (requireActivity())).hideView(binding.refreshPastButton);
                ((DashboardActivity) (requireActivity())).hideView(binding.rvBooking);
//            ((DashboardActivity) (requireActivity())).hideView(binding.refreshButton);
                binding.mainProgress.setVisibility(View.VISIBLE);

//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
                new PreferenceHelper(requireActivity()).setReloadMyTrips(false);
                viewModel.getCurrentTrips(token, currentOrdersPage);
//            final Handler handler = new Handler(Looper.getMainLooper());
                new Handler().postDelayed(() -> {
                    binding.refreshButton.setRefreshing(false);
//                ((DashboardActivity) (requireActivity())).showView(binding.refreshButton);
                    ((DashboardActivity) (requireActivity())).showView(binding.rvBooking);

                }, 2000);


                binding.currentLine.setVisibility(View.VISIBLE);
//
//
            });
            binding.refreshPastButton.setOnRefreshListener(() -> {
                binding.noData.setVisibility(View.GONE);
                isRefreshData = true;
                pastOrdersPage = 1;
                ((DashboardActivity) (requireActivity())).hideView(binding.rvPast);
                binding.mainProgress.setVisibility(View.VISIBLE);
//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
                new PreferenceHelper(requireActivity()).setReloadMyTrips(false);
                viewModel.getPastTrips(token, pastOrdersPage);
//            final Handler handler = new Handler(Looper.getMainLooper());
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        binding.refreshPastButton.setRefreshing(false);
                        ((DashboardActivity) (requireActivity())).showView(binding.rvPast);
                    }
                }, 2000);
                binding.pastLine.setVisibility(View.VISIBLE);

            });
            currentManager = new LinearLayoutManager(requireActivity());
            pastManager = new LinearLayoutManager(requireActivity());
            binding.rvPast.setLayoutManager(pastManager);
            binding.rvBooking.setLayoutManager(currentManager);
            currentOrdersResponse = CurrentOrdersResponse.getInstance();
            pastOrderResponse = PastOrdersResponse.getInstance();
            initializeListeners();
            token = new PreferenceHelper(requireActivity()).getTOKEN();
            viewModel = new ViewModelProvider(this).get(MyTripsViewModel.class);
            viewModel.init(this, token, currentOrdersPage, pastOrdersPage);

            try {
                if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.refreshButton.setVisibility(View.GONE);
                    binding.mainProgress.setVisibility(View.GONE);
                } else {
                    if (new PreferenceHelper(requireActivity()).isReloadMyTrips()) {
                        currentOrdersPage = 1;
                        pastOrdersPage = 1;
                        binding.mainProgress.setVisibility(View.VISIBLE);
//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
                        new PreferenceHelper(requireActivity()).setReloadMyTrips(false);
                        viewModel.getCurrentTrips(token, currentOrdersPage);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(() -> viewModel.getPastTrips(token, pastOrdersPage), 3000);

                    } else {
                        if (currentOrdersResponse.getMrt7al() == null &&
                                pastOrderResponse.getMrt7al() == null) {
//                     binding.progresss.setVisibility(View.VISIBLE);
                            binding.mainProgress.setVisibility(View.VISIBLE);
                            viewModel.getCurrentTrips(token, currentOrdersPage);
                            viewModel.getPastTrips(token, pastOrdersPage);
//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
                        } else {
                            if (currentOrdersResponse.getMrt7al() != null) {
                                if (!currentOrdersResponse.getMrt7al().getData().isEmpty()) {
                                    currentOrders.addAll(currentOrdersResponse.getMrt7al().getData());
                                    currentOrderAdapter = new CurrentOrderAdapter(currentOrders, this,
                                            AboutAppResponse.getInstance().getMrt7al()
                                                    .getData().getMobile_whats(), requireContext());
                                    binding.rvBooking.setAdapter(currentOrderAdapter);
                                    binding.refreshPastButton.setVisibility(View.GONE);
                                    binding.refreshButton.setVisibility(View.VISIBLE);
                                }
                            } else {
                                binding.refreshButton.setVisibility(View.GONE);
                                binding.noData.setVisibility(View.VISIBLE);
                                binding.mainProgress.setVisibility(View.GONE);
                            }
                            if (!pastOrderResponse.getMrt7al().getData().isEmpty()) {
                                ordersList = pastOrderResponse.getMrt7al().getData();
                                pastOrderAdapter = new PastOrderAdapter(ordersList, requireContext(), this);
                                binding.rvPast.setAdapter(pastOrderAdapter);
                            }
                        }
                    }
                }
            } catch (NullPointerException | IllegalStateException a) {
                a.printStackTrace();
            }
            binding.rvBooking.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                          super.onScrolled(recyclerView, dx, dy);
                                                      }

                                                      @Override
                                                      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                          super.onScrollStateChanged(recyclerView, newState);
                                                          visibleItemCount = currentManager.getChildCount();
                                                          totalItemCount = currentManager.getItemCount();
                                                          pastVisiblesItems = currentManager.findFirstVisibleItemPosition();
                                                          if (pastVisiblesItems > mLastFirstVisibleItem) {
                                                              isScrollDown = true;
                                                          } else if (pastVisiblesItems < mLastFirstVisibleItem) {
                                                              isScrollDown = false;
                                                          }

                                                          mLastFirstVisibleItem = pastVisiblesItems;
                                                          if (isScrollDown)
                                                              if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 7) {
                                                                  if (!isLoadingData) {
                                                                      //new updateData().execute();
                                                                      isLoadingData = true;
                                                                      currentOrdersPage = currentOrdersPage + 1;
                                                                      if (currentOrdersResponse.getMrt7al().getPagination().getPageCount() >= currentOrdersPage) {
                                                                          viewModel.getCurrentTrips(token, currentOrdersPage);
                                                                          binding.progresss.setVisibility(View.VISIBLE);
                                                                      }
                                                                  }
                                                              }
                                                      }
                                                  }
            );
            binding.rvPast.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                   public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                       super.onScrolled(recyclerView, dx, dy);
                                                   }

                                                   @Override
                                                   public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                       super.onScrollStateChanged(recyclerView, newState);
                                                       pastvisibleItemCount = pastManager.getChildCount();
                                                       pasttotalItemCount = pastManager.getItemCount();
                                                       pastpastVisiblesItems = pastManager.findFirstVisibleItemPosition();
                                                       if (pastpastVisiblesItems > pastmLastFirstVisibleItem) {
                                                           pastisScrollDown = true;
                                                       } else if (pastVisiblesItems < mLastFirstVisibleItem) {
                                                           pastisScrollDown = false;
                                                       }

                                                       pastmLastFirstVisibleItem = pastpastVisiblesItems;
                                                       if (pastisScrollDown)
                                                           if ((pastvisibleItemCount + pastpastVisiblesItems) >= pasttotalItemCount - 7) {
                                                               if (!pastisLoadingData) {
                                                                   //new updateData().execute();
                                                                   pastisLoadingData = true;
                                                                   pastOrdersPage = pastOrdersPage + 1;
                                                                   if (pastOrderResponse.getMrt7al().getPagination().getPageCount() >= pastOrdersPage) {
                                                                       viewModel.getPastTrips(token, pastOrdersPage);
                                                                       binding.progresss.setVisibility(View.VISIBLE);
                                                                   }
                                                               }
                                                           }
                                                   }

                                               }
            );
            isLoadingData = true;

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (new PreferenceHelper(requireActivity()).isReloadMyTrips()){
            currentOrdersPage =1;
            pastOrdersPage =1;
            binding.progresss.setVisibility(View.VISIBLE);
//                DialogsHelper.showProgressDialog(requireActivity(), getString(R.string.loading_data));
            new PreferenceHelper(requireActivity()).setReloadMyTrips(false);
            viewModel.getCurrentTrips(token,currentOrdersPage);
            viewModel.getPastTrips(token,pastOrdersPage);
        }
    }
    public void showPaymentFailedDialog(String message) {
        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        RegisterErrorDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.register_error_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(R.id.backToHome);
            dialog.cancel();
        });
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }
    MyTripsViewModel viewModel;
    private String token;



    /* initialize listener */
    private void initializeListeners() {

        mTvCompleted.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

    }
    Dialog rateDialog;
    public  void showRatingDialog(PastOrdersResponse.Mrt7alBean.DataBean bean) {
        if(!requireActivity().isFinishing()) {
            rateDialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            rateDialog.setCancelable(true);
            RatingDialogBinding ratingDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.rating_dialog, null, false);
            ratingDialogBinding.companyName.setText(
                    bean.getTrip_date().getCompany().getName());
            ratingDialogBinding.busType.setText(
                    bean.getTrip_date().getBus_type());
            Glide.with(requireActivity()).load("https://administrator.mrt7al.com/" +
                    bean.getTrip_date().getCompany().getLogo_pic()).into(ratingDialogBinding
            .companyImage);

            ratingDialogBinding.okButton.setOnClickListener(v -> {
                if (checkRatingValues(ratingDialogBinding)){
                    DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading));
                    viewModel.ratePastTrip("Bearer "+token,String.valueOf(bean.
                            getRes_passangers().get(0).getReservation_id()),String.valueOf(
                                    ratingDialogBinding.busComfortRating.getRating()),
                            String.valueOf(ratingDialogBinding.busStatusRating.getRating()),
                            String.valueOf(ratingDialogBinding.busAdditionalServiceRating.getRating()),
                            String.valueOf(ratingDialogBinding.busPathroomRating.getRating()),
                            String.valueOf(ratingDialogBinding.driverPationRating.getRating()),
                            String.valueOf(ratingDialogBinding.driverSkillsRating.getRating()),
                            ratingDialogBinding.complaint.getText().toString()
                    );
                }
            });
             rateDialog.setContentView(ratingDialogBinding.getRoot());
            rateDialog.show();
        }

    }
    private boolean checkRatingValues(RatingDialogBinding ratingDialogBinding){
        if (ratingDialogBinding.busComfortRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_bus_comfort)
                    , Toast.LENGTH_SHORT).show();
            return false;
        } else  if (ratingDialogBinding.busStatusRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_bus_status)
                    , Toast.LENGTH_SHORT).show();
            return false;
        } else  if (ratingDialogBinding.busAdditionalServiceRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_bus_addtional_service)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }else  if (ratingDialogBinding.busPathroomRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_bus_pathroom)
                    , Toast.LENGTH_SHORT).show();
            return false;
        } else  if (ratingDialogBinding.driverPationRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_driver_pation)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }else  if (ratingDialogBinding.driverSkillsRating.getRating()<=0){
            Toast.makeText(requireActivity(), getString(R.string.please_rate_driver_skills)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /* onClick listener */
    @Override
    public void onClick(View v) {
        if (v == mTvCompleted) {
            if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
                DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
            } else {
//                mTvCompleted.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.bg_leftswitch_select));
//                mTvCompleted.setTextColor(getResources().getColor(R.color.white));
//
//                mTvCancel.setTextColor(getResources().getColor(R.color.textheader));
//                mTvCancel.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.bg_rightswitch));
                    binding.currentLine.setVisibility(View.VISIBLE);
                    binding.pastLine.setVisibility(View.GONE);
                ((DashboardActivity) (requireActivity())).showView(binding.refreshButton);
                ((DashboardActivity) (requireActivity())).hideView(binding.refreshPastButton);
                if (!currentOrders.isEmpty()) {
                    binding.noData.setVisibility(View.GONE);
                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                }
            }
        } else if (v == mTvCancel) {
            if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
                DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
            } else {
                binding.currentLine.setVisibility(View.GONE);
                binding.pastLine.setVisibility(View.VISIBLE);
                ((DashboardActivity) (requireActivity())).hideView(binding.refreshButton);
                ((DashboardActivity) (requireActivity())).showView(binding.refreshPastButton);
                if (!ordersList.isEmpty()) {
                    binding.noData.setVisibility(View.GONE);
                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean> currentOrders = new ArrayList<>();

    @Override
    public void onCurrentResponse(boolean isSuccess, CurrentOrdersResponse currentOrdersResponse) {
        try{
         if (isSuccess) {
            if (!currentOrdersResponse.getMrt7al().getData().isEmpty()) {
                if (currentOrdersPage >1) {
                    currentOrders.addAll(currentOrdersResponse.getMrt7al().getData());
                } else {
                    currentOrders = currentOrdersResponse.getMrt7al().getData();
                }
                currentOrderAdapter = new CurrentOrderAdapter(currentOrders,
                        this,AboutAppResponse.getInstance().getMrt7al().
                        getData().getMobile_whats(),requireContext());
                binding.rvBooking.setAdapter(currentOrderAdapter);
                binding.refreshButton.setVisibility(View.VISIBLE);
//                binding.rvBooking.setVisibility(View.VISIBLE);
//                binding.noData.setVisibility(View.GONE);
//                binding.rvPast.setVisibility(View.GONE);
                binding.noData.setVisibility(View.GONE);
            } else {
//                binding.rvBooking.setVisibility(View.GONE);
//                binding.noData.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.VISIBLE);
            }
        }
        binding.mainProgress.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        }catch (IllegalStateException e){
            //e.printStackTrace();
        }
    }



    private ArrayList<PastOrdersResponse.Mrt7alBean.DataBean> ordersList = new ArrayList<>();

    @Override
    public void onPastResponse(boolean isSuccess, PastOrdersResponse currentOrdersResponse) {
        try{
        if (isSuccess) {
            if (!currentOrdersResponse.getMrt7al().getData().isEmpty()) {
                if (pastOrdersPage ==1) {
                    ordersList = currentOrdersResponse.getMrt7al().getData();
                } else {
                    ordersList.addAll(currentOrdersResponse.getMrt7al().getData());
                }
                pastOrderAdapter = new PastOrderAdapter(ordersList,requireContext(),this);
                binding.rvPast.setAdapter(pastOrderAdapter);
//                binding.refreshPastButton.setVisibility(View.VISIBLE);
//                binding.rvPast.setVisibility(View.VISIBLE);
//                binding.noData.setVisibility(View.GONE);
//                binding.rvBooking.setVisibility(View.GONE);
            }
        }
        binding.mainProgress.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        }catch (IllegalStateException e){
            //e.printStackTrace();
        }
    }

    private ErrorResponse errorResponse;
    @Override
    public void handleError(Throwable t) {
        binding.mainProgress.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        if (isConnected()) {
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    try {
                        if (!DialogsHelper.isLoginDialogOnScreen()) {
                            DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                        }
                    } catch (IllegalStateException e) {
                        //e.printStackTrace();
                    }
                } else if (code == 404) {
                    ResponseBody body = ((HttpException) t).response().errorBody();
                    Gson gson = new Gson();
                    try {
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
                    } catch (IOException | IllegalStateException ioException) {
                        ioException.printStackTrace();
                    }
                    if (errorResponse.getMrt7al() != null) {
                        if (!errorResponse.getMrt7al().getMsg().equals("لم يتم العثور على نتائج بحث"))
                            Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();

            if (currentOrders.size() == 0) {
                if (!isRefreshData) {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.refreshPastButton.setVisibility(View.GONE);
                    binding.refreshButton.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onFavouriteAd(boolean isSuccess) {

    }

    @Override
    public void onCanceled(boolean isSuccess, CancelOrderResponse cancelOderResponse) {
        DialogsHelper.removeProgressDialog();
        if (isSuccess) {
            currentOrders.remove(canceledPosition);
            currentOrderAdapter.notifyDataSetChanged();
            if (currentOrders.size() < 1) {
                binding.noData.setVisibility(View.VISIBLE);
            } else {
                binding.noData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onTripRated(boolean success,String message) {
        DialogsHelper.removeProgressDialog();
        if (success) {
            rateDialog.cancel();
            pastOrderResponse.getMrt7al().getData().get(ratedRowPosition).setRates(new ArrayList<>());
            pastOrderResponse.getMrt7al().getData().get(ratedRowPosition).getRates().add(new PastOrdersResponse.Mrt7alBean.DataBean.RateBean());
            pastOrderAdapter.notifyDataSetChanged();
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }
    private void openPayActivity(String url) {
        DialogsHelper.removeProgressDialog();
        Intent intent = new Intent(getActivity(), PayActivity.class);
        intent.putExtra("url", url);
        webViewLauncher.launch(intent);
    }
    @Override
    public void handlePaymentError(String message) {
        DialogsHelper.removeProgressDialog();
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckingPayStatus(boolean success, String message) {
        DialogsHelper.removeProgressDialog();
        if(success){
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            viewModel.getCurrentTrips(token,currentOrdersPage);
            viewModel.getPastTrips(token,pastOrdersPage);
        } else {
            DialogsHelper.removeProgressDialog();
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPayRequested(boolean success, RequestPayModel requestPayModel) {
        Log.d("TAG", "onPayRequested: "+requestPayModel.getMrt7al().getSuccess());
        if(requestPayModel.getMrt7al().getSuccess()){
            openPayActivity(requestPayModel.getMrt7al().getData().getTransaction().getUrl());
        } else {
            DialogsHelper.removeProgressDialog();
            Toast.makeText(requireActivity(), requestPayModel.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPrint(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean, int pos) {
//        if (datesBean.getStatus().equals("مؤكدة")) {
            try {
//                ArrayList<PrintTicketModel> tickets = new ArrayList<>();
//                for (int i = 0; i < datesBean.getRes_passangers().size(); i++) {
//                    PrintTicketModel ticketModel = new PrintTicketModel();
//                    ticketModel.setPassengerName(datesBean.getRes_passangers().get(i).getSub_passanger().getFull_name());
//                    ticketModel.setTicketNumber(String.valueOf(datesBean.getTicket_number()));
//                    ticketModel.setTripDate(datesBean.getTrip_date().getDatetime_from());
//                    ticketModel.setTripNumber(String.valueOf(datesBean.getTrip_date().getId()));
//                    ticketModel.setPassportNumber(datesBean.getRes_passangers().get(i)
//                            .getSub_passanger().getPassport_id());
                Gson gson = new Gson();
                    String json = gson.toJson(datesBean);
                    Bundle bundle = new Bundle();
                    bundle.putString("data",json);
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_myBookingFragment_to_printTicketFragment,bundle);
//                    ticketModel.setBitmap(bitmap);
//                    tickets.add(ticketModel);
//                }
//                showPrintDialog(tickets, datesBean);
            } catch (Exception e) {

            }
//        } else {
//             showPendingDialog(getString(R.string.pending_trip),requireActivity(),
//                     AboutAppResponse.getInstance().getMrt7al().getData().getMobile_whats());
//        }
    }
    public void showPendingDialog(String message, Activity activity,String phone) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        PendingReservationDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.pending_reservation_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.whatsappLogo.setOnClickListener(v -> {
            PackageManager packageManager = requireActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            try {
                String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode("", "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    requireActivity().startActivity(i);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        });
        registerErrorDialogBinding.okButton.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }
    @Override
    public void onEdit(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean, int pos) {
        showEditDialog(getString(R.string.to_edit_trip), requireActivity(), datesBean, pos);
    }

    @Override
    public void onCancel(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean, int pos) {
        showCancelDialog(getString(R.string.sure_cancel), requireActivity(), datesBean, pos);

    }

    @Override
    public void onWhatsapp(String phone) {
        PackageManager packageManager = requireActivity().getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode("", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
//            if (i.resolveActivity(packageManager) != null) {
                requireActivity().startActivity(i);
//            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void onDetailsOpened(int position) {
        binding.rvBooking.scrollToPosition(position );

    }

    @Override
    public void onPayByVisaClicked(String reservationId) {
        DialogsHelper.showProgressDialog(requireActivity(),getString(R.string.loading_data));
        viewModel.requestPay(token,reservationId);
    }

    private int canceledPosition;

    public void showEditDialog(String message, Activity activity, CurrentOrdersResponse.Mrt7alBean.DataBean datesBean, int pos) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        CancelDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.cancel_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(v -> {

            if (registerErrorDialogBinding.reason.getText().toString().isEmpty()) {
                DialogsHelper.showTextError(registerErrorDialogBinding.reason,requireActivity());

            } else {
                DialogsHelper.showProgressDialog(activity, getString(R.string.canceling));
                canceledPosition = pos;
                viewModel.CancelOrder(token,String.valueOf(datesBean.getId()),
                        registerErrorDialogBinding.reason.getText().toString());
            }
            dialog.cancel();
        });
        registerErrorDialogBinding.cancel.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }

    public void showCancelDialog(String message, Activity activity, CurrentOrdersResponse.Mrt7alBean.DataBean datesBean, int pos) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        CancelDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.cancel_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(v -> {
            if (registerErrorDialogBinding.reason.getText().toString().isEmpty()) {
               DialogsHelper.showTextError(registerErrorDialogBinding.reason,requireActivity());
            } else {
                canceledPosition = pos;
                DialogsHelper.showProgressDialog(activity, getString(R.string.canceling));
                viewModel.CancelOrder(token, String.valueOf(datesBean.getId()),
                        registerErrorDialogBinding.reason.getText().toString());
            }
            dialog.cancel();
        });
        registerErrorDialogBinding.cancel.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }

    public void showPrintDialog(ArrayList<PrintTicketModel> ticketModels,CurrentOrdersResponse.Mrt7alBean.DataBean dataBean) {
        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        PrintTicketFragmentBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.print_ticket_fragment, null, false);
          registerErrorDialogBinding.ticketsRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        PrintTicketAdapter adapter = new PrintTicketAdapter(requireActivity(),ticketModels,dataBean,this);
          registerErrorDialogBinding.ticketsRecycler.setAdapter(adapter);
        registerErrorDialogBinding.backBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
         dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }


    @Override
    public void onClickMoreInfo(Bitmap bitmap) {
        try {
            File cachePath = new File(requireActivity().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            File imagePath = new File(requireActivity().getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
//
//            String path = Environment.getExternalStorageDirectory().toString();
//            OutputStream fOut = null;
//            int counter = 0;
//            File file = new File(requireActivity().getCacheDir(), "Mrt7l" + counter + ".jpg");
//            fOut = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//            fOut.flush(); // Not really required
//            fOut.close(); // do not forget to close the stream
            Uri contentUri = FileProvider.getUriForFile(requireActivity(), "com.mrt7l.fileprovider", newFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                shareIntent.setDataAndType(contentUri, requireActivity().getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareIntent, "Choose an app"));
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    int ratedRowPosition;
    @Override
    public void onRateClicked(PastOrdersResponse.Mrt7alBean.DataBean bean,int position) {
        showRatingDialog(bean);
        ratedRowPosition = position;
    }

    @Override
    public void onPastDetailsOpened(int position) {
        binding.rvPast.scrollToPosition(position );

    }

//        File imagePath = new File(requireActivity().getCacheDir(), "images");
//        File newFile = new File(imagePath, "image.png");
//
//    }
}