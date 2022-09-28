//package com.mrt7l.ui.activity;
//
//import android.app.Dialog;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.AppCompatSeekBar;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Objects;
//
//import com.mrt7l.BaseActivity;
//import com.mrt7l.R;
//import com.mrt7l.helpers.DialogsHelper;
//import com.mrt7l.model.BusModel;
//import com.mrt7l.model.ReservationListModel;
//import com.mrt7l.ui.ReservationBottomSheet;
//import com.mrt7l.ui.adapter.ItemBusAdapter;
//import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
//import com.mrt7l.ui.fragment.search_trips.SearchInterface;
//import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
//import com.mrt7l.ui.fragment.search_trips.SearchTripsViewModel;
//import com.mrt7l.utils.Constants;
//
//public class BusListActivity extends BaseActivity  {
//
//    /*variable declaration*/
//    private RecyclerView mRvBuses;
//    private LinearLayoutManager dealerListManager;
//    private int pastVisiblesItems, visibleItemCount, totalItemCount,mLastFirstVisibleItem = 0;
//    private boolean isLoadingData =false ,isScrollDown =true;
//    private List<BusModel> mBusList;
//    private ImageView mIvBack, mIvFilter, mIvPrevious, mIvNext;
//    private TextView mTvDate,mTvTitle,noData;
//    private Calendar mDepartDateCalendar;
//    private SearchTripsViewModel viewModel;
//    private int page = 1;
//    private String from,to,time,date,count,companyID;
//    private ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bus_list);
//        response = SearchTripsResponse.getInstance();
//        noData = findViewById(R.id.noData);
//        viewModel = new ViewModelProvider(this).get(SearchTripsViewModel.class);
//        if (getIntent() != null){
//            from = getIntent().getStringExtra("from");
//            to = getIntent().getStringExtra("to");
//            time = getIntent().getStringExtra("time");
//            date = getIntent().getStringExtra("date");
//            count = getIntent().getStringExtra("count");
//            companyID = getIntent().getStringExtra("companyID");
//
//            DialogsHelper.showProgressDialog(this,getString(R.string.loading_data));
//            viewModel.search(from,to,date,time,count,companyID,String.valueOf(page));
//        }
//        initLayouts();
//        initializeListeners();
//        progressBar = findViewById(R.id.progresss);
//        dealerListManager = new LinearLayoutManager(this);
//        mRvBuses.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                                                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                                            super.onScrolled(recyclerView, dx, dy);
//                                                        }
//
//                                                        @Override
//                                                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                                            super.onScrollStateChanged(recyclerView, newState);
//                                                            visibleItemCount = dealerListManager.getChildCount();
//                                                            totalItemCount = dealerListManager.getItemCount();
//                                                            pastVisiblesItems = dealerListManager.findFirstVisibleItemPosition();
//                                                            if (pastVisiblesItems > mLastFirstVisibleItem) {
//                                                                isScrollDown = true;
//                                                            } else if (pastVisiblesItems < mLastFirstVisibleItem) {
//                                                                isScrollDown = false;
//                                                            }
//
//                                                            mLastFirstVisibleItem = pastVisiblesItems;
//                                                            if (isScrollDown)
//                                                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 7) {
//                                                                    if (!isLoadingData) {
//                                                                        //new updateData().execute();
//                                                                        isLoadingData = true;
//                                                                        page =page++;
//                                                                        viewModel.search(from,to,date,time,count,companyID,String.valueOf(page));
//                                                                        progressBar.setVisibility(View.VISIBLE);
//                                                                    }
//                                                                }
//
//                                                        }
//
//                                                    }
//        );
//    }
//
//    /* initialize listener */
//    private void initializeListeners() {
//
//        String mTitle=getIntent().getStringExtra(Constants.intentdata.TRIP_KEY);
//        String mSearchTitle=getIntent().getStringExtra(Constants.intentdata.SEARCH_BUS);
//        String mPackageTitle=getIntent().getStringExtra(Constants.intentdata.PACKAGE_NAME);
//
////        if(mTitle!=null) {
////            mTvTitle.setText(mTitle);
////        }
////        if(mSearchTitle!=null)
////        {
////            mTvTitle.setText(mSearchTitle);
////        }
////        if(mPackageTitle!=null)
////        {
////            mTvTitle.setText(mPackageTitle);
////        }
////
////        mBusList = new ArrayList<>();
////        mRvBuses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
////
////        mRvBuses.setAdapter(new ItemBusAdapter(this, mBusList,this));
////        RunLayoutAnimation(mRvBuses);
//
//        mDepartDateCalendar = Calendar.getInstance();
//        mTvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
//
//    }
//    private ItemBusAdapter adapter;
//    /* init layout */
//    private void initLayouts() {
//        mRvBuses = findViewById(R.id.rvBus);
//        mIvBack = findViewById(R.id.ivBack);
//        mIvFilter = findViewById(R.id.ivFilter);
//        mIvPrevious = findViewById(R.id.ivPrevious);
//        mIvNext = findViewById(R.id.ivNext);
//        mTvDate = findViewById(R.id.tvDate);
//        mTvTitle = findViewById(R.id.tvTitle);
//    }
//
//
//
//    SearchTripsResponse response;
//
//}
