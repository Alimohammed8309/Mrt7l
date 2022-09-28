package com.mrt7l.ui.fragment.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.FragmentHomeBinding;
import com.mrt7l.helpers.ConnectivityReceiver;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.helpers.SearchableSpinner;
import com.mrt7l.model.ErrorResponse404;
import com.mrt7l.model.NewOfferModel;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.activity.OffersActivity;
import com.mrt7l.ui.adapter.HomeBusAdapter;
import com.mrt7l.ui.adapter.ItemBusAdapter;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import com.mrt7l.utils.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;

public class HomeFragmentNewest extends Fragment implements View.OnClickListener, HomeBusAdapter.ClickListener
        , HomeInterface, ItemBusAdapter.ClickListener {
    public static final String mTitle = "Home";
    private SearchableSpinner mEdFromCity, mEdToCity,busTypeSpinner;
    public static String mFrom, mTo;
    private int mValue = 0;
     private ArrayList<NewOfferModel> mNewOfferList;
    private final ArrayList<HomeResponse.Mrt7alBean.DataBean> mRecentSearchList = new ArrayList<>();
     private ImageView mIvSwap;
    private ImageView mIvDescrease;
    private ImageView mSearch;
    private TextView mTvCount;
    private TextView mTvViewNewOffers;
    private Calendar mDepartDateCalendar;
    private TextView mEdDepartDate;
    private HomeViewModel viewModel;
    private String searchDate;
    HomeResponse homeResponse;
    private int page = 1;
    private final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mDepartDateCalendar.set(Calendar.YEAR, year);
            mDepartDateCalendar.set(Calendar.MONTH, monthOfYear);
            mDepartDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    private boolean mIsNonAcSelected;
    private boolean mIsSeaterSelected;
    private boolean mIsSleeperSelected;
    private FragmentHomeBinding binding;
    private int pastVisiblesItems, visibleItemCount, totalItemCount,mLastFirstVisibleItem = 0;
     private boolean isLoadingData =false ,isScrollDown =true;
    private LinearLayoutManager currentManager;

    /* create view */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeResponse = HomeResponse.getInstance();
        RegisterCollectionResponse registerCollectionResponse =
                RegisterCollectionResponse.getInstance();
             initView(binding.getRoot());
             binding.ivNotification.setOnClickListener(view -> {
                 Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(
                         R.id.action_notifications
                 );
             });
        currentManager = new LinearLayoutManager(requireActivity());
            setListener();
            getData();

            setOfferAdapter();
            viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            viewModel.init(this);

            if (mDepartDateCalendar == null) {
                mDepartDateCalendar = Calendar.getInstance();
            }
            binding.selectTime.setOnClickListener(view -> {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(requireActivity(),
                        (timePicker, selectedHour, selectedMinute) ->
                                binding.selectTime.setText(selectedHour + ":" + selectedMinute)
                        , hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("اختر الوقت");
                mTimePicker.show();
            });
            binding.refreshButton.setOnRefreshListener(() -> {
                binding.mainProgress.setVisibility(View.VISIBLE);
                binding.rvBus.setVisibility(View.GONE);
                 page =1;
                mRecentSearchList.clear();
                searchDate = updateSearchLabel();
                viewModel.search(searchDate,"ANDRIOD_IOS",String.valueOf(page));
                new Handler().postDelayed(() -> binding.refreshButton.setRefreshing(false), 2000);
            });
        mDepartDateCalendar = Calendar.getInstance();
        binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.
                format(mDepartDateCalendar.getTime()));
        try{
        if (homeResponse.getMrt7al() == null) {
//            binding.mainProgress.setVisibility(View.VISIBLE);
            binding.mainProgress.setVisibility(View.VISIBLE);
            viewModel.search(searchDate,"ANDRIOD_IOS",String.valueOf(page));
            } else {
                mRecentSearchList.clear();
                mRecentSearchList.addAll(homeResponse.getMrt7al().getData());
                binding.rvBus.setLayoutManager(currentManager);
                mRecentSearchAdapter = new HomeBusAdapter(requireActivity(), mRecentSearchList, this);
                binding.rvBus.setAdapter(mRecentSearchAdapter);
                Date date = convertStringToDate(mRecentSearchList.get(0).getDatetime_from()
                        .replace("T", " ").replace("+03:00", ""));
                if (date != null) {
                    mDepartDateCalendar.setTime(date);
                    binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
//                    mRecentSearchAdapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
                }
                if (registerCollectionResponse.getMrt7al() != null) {
                    cities.clear();
                    fromList.clear();
                    toList.clear();
                    cities.addAll(registerCollectionResponse.getMrt7al().getData().getCities());
                    fromList.add(getString(R.string.hint_from_city));
                    toList.add(getString(R.string.hint_to_city));
                    for (int i = 0; i < cities.size(); i++) {
                        fromList.add(cities.get(i).getName());
                        toList.add(cities.get(i).getName());
                    }

                    setupFromSpinner(binding.edFromCity);
                    setupToSpinner(binding.edToCity);
                }
            }
        } catch (NullPointerException | IllegalStateException a){
            a.printStackTrace();
        }
            binding.searchBtn.setOnClickListener(view -> {
                if (validate()) {
                    busType = "";
                    if (binding.vip.isChecked() && !binding.economy.isChecked()){
                        busType = "vip";
                    }
                    if (binding.economy.isChecked() && !binding.vip.isChecked()){
                        busType =  "normal";
                    }

                    Bundle bundle = new Bundle();
                    if (fromSelection!=-1)
                    bundle.putString("from", String.valueOf(cities.get(fromSelection).getId()));
                    if (toSelection!=-1)
                        bundle.putString("to", String.valueOf(cities.get(toSelection).getId()));
                    bundle.putString("busType",busType);
                    if (!binding.edOneWay.getText().toString().isEmpty()) {
                        bundle.putString("date", binding.edOneWay.getText().toString());
                    }  else {
                        bundle.putString("date", "");

                    }
                    if (!binding.selectTime.getText().toString().isEmpty()) {
                        bundle.putString("time", binding.selectTime.getText().toString());
                    } else {
                        bundle.putString("time", "");

                    }
                           bundle.putString("count", String.valueOf(
                                   adultCount + childCount + babyCount));

                    Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(
                            R.id.action_homeFragmentNewest_to_searchTripsFragment,bundle
                    );
                }
            });
            setUpadultSpinner(binding.adultSpinner);
            setUpChildSpinner(binding.childSpinner);
            setUpBabySpinner(binding.babySpinner);

            binding.ivPrevious.setOnClickListener(v -> {
                mRecentSearchList.clear();
                mDepartDateCalendar.add(Calendar.DATE, -1);
                binding.mainProgress.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                binding.rvBus.setVisibility(View.GONE);
                binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                searchDate = updateSearchLabel();
                page =1;
                viewModel.search( searchDate,"",String.valueOf(page));  });
                binding.ivNext.setOnClickListener(view -> {
                mRecentSearchList.clear();
                mDepartDateCalendar.add(Calendar.DATE, 1);
                binding.mainProgress.setVisibility(View.VISIBLE);
                binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
                searchDate = updateSearchLabel();
                page =1;
                binding.noData.setVisibility(View.GONE);
                binding.rvBus.setVisibility(View.GONE);
                viewModel.search(searchDate,"",String.valueOf(page));
            });
        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            View view =  binding.scrollView.getChildAt(binding.scrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (binding.scrollView.getHeight() + binding.scrollView
                    .getScrollY()));

            if (diff == 0) {
//                Log.v("diff ",String.valueOf(diff));
                // your pagination code
                if (!isLoadingData) {
                    //new updateData().execute();
                    isLoadingData = true;
                    searchDate =updateSearchLabel();
                    page =page+1;
                    try {
                        if (homeResponse.getMrt7al().getPagination() != null)
                            if (homeResponse.getMrt7al().getPagination().getPageCount() >= page) {
                                viewModel.search(searchDate, "", String.valueOf(page));
                                binding.progresss.setVisibility(View.VISIBLE);
                            }
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return binding.getRoot();
    }
    private int adultCount=0,childCount=0,babyCount =0;
    String busType = "";
    /* update label */

    @Override
    public void onResume() {
        super.onResume();

    }
    /* set adapter */
    private void setOfferAdapter() {
//        mRvNewOffer.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
//        mRvNewOffer.setAdapter(new NewOfferAdapter(getActivity(), mNewOfferList));

    }

    /* set listener */
    private void setListener() {
        mSearch.setOnClickListener(this);
        mTvViewNewOffers.setOnClickListener(this);
//        mTvAC.setOnClickListener(this);
//        mTvNonAc.setOnClickListener(this);
//        mTvSleeper.setOnClickListener(this);
//        mTvSeater.setOnClickListener(this);
         mIvSwap.setOnClickListener(this);
//        mIvDescrease.setOnClickListener(this);
//        mIvIncrease.setOnClickListener(this);
        mEdDepartDate.setOnClickListener(this);


    }

    /* init view */
    private void initView(View view) {
//        mRvNewOffer = view.findViewById(R.id.rvNewOffers);
        mTvViewNewOffers = view.findViewById(R.id.tvViewallNewOffer);
        mIvSwap = view.findViewById(R.id.ivSwap);
        mSearch = view.findViewById(R.id.btnSearch);
        mEdDepartDate = view.findViewById(R.id.edOneWay);
        mEdFromCity = view.findViewById(R.id.edFromCity);
        mEdToCity = view.findViewById(R.id.edToCity);
        mIvDescrease = view.findViewById(R.id.ivDescrease);
        mTvCount = view.findViewById(R.id.tvCount);
        mIvDescrease.setVisibility(View.INVISIBLE);
    }

    /* et data */
    private void getData() {


        mNewOfferList = new ArrayList<>();
        mNewOfferList.add(new NewOfferModel(getString(R.string.lbl_offer1)));
        mNewOfferList.add(new NewOfferModel(getString(R.string.lbl_offer2)));
        mNewOfferList.add(new NewOfferModel(getString(R.string.lbl_offer1)));
        mNewOfferList.add(new NewOfferModel(getString(R.string.lbl_offer2)));
        mNewOfferList.add(new NewOfferModel(getString(R.string.lbl_offer2)));

    }

    /* onClick listener */
    @Override
    public void onClick(View v) {
             if(v.getId() ==  R.id.edOneWay) {
                 if (getActivity() != null) {
                     DatePickerDialog datePickerDialogs = new DatePickerDialog(getActivity(), date, mDepartDateCalendar
                             .get(Calendar.YEAR), mDepartDateCalendar.get(Calendar.MONTH),
                             mDepartDateCalendar.get(Calendar.DAY_OF_MONTH));
                     datePickerDialogs.getDatePicker().setMinDate(new Date().getTime());
                     datePickerDialogs.show();
                 }
             } else if(v.getId() == R.id.ivSwap) {
                 if (fromSelection > -1) {
                     if (toSelection > -1) {
                         int from = fromSelection;
                         int to = toSelection;

                         requireActivity().runOnUiThread(() -> {
                             int selectionPosition = fromAdapter.getPosition(cities.get(to).getName());
                             mEdFromCity.post(() -> mEdFromCity.setSelection(selectionPosition));
                             int ds = toAdapter.getPosition(cities.get(from).getName());
                             mEdToCity.post(() -> mEdToCity.setSelection(ds));
                         });

                     } else {
                         mEdToCity.requestFocus();
                     }
                 } else {
                     mEdFromCity.requestFocus();
                 }
             } else if(v.getId() == R.id.ivDescrease) {
                 mValue = mValue - 1;
                 mTvCount.setText(String.valueOf(mValue));

                 if (mValue <= 1) {
                     ((BaseActivity) requireActivity()).invisibleView(mIvDescrease);
                 } else {
                     ((BaseActivity) requireActivity()).showView(mIvDescrease);
                     mTvCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                     mTvCount.setTextColor(getResources().getColor(R.color.colorPrimary));
                 }
             } else if(v.getId()==

             R.id.ivIncrease) {
                 mValue = mValue + 1;

                 if (mValue < 1) {
                     mValue = 1;

                 } else {

                     if (mValue == 1)
                         ((BaseActivity) requireActivity()).invisibleView(mIvDescrease);
                     else
                         ((BaseActivity) requireActivity()).showView(mIvDescrease);
                     mTvCount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                     mTvCount.setText(String.valueOf(mValue));
                     mTvCount.setTextColor(getResources().getColor(R.color.colorPrimary));

                 }
                 //            case R.id.tvAc:
//                if (!mIsAcSelected) {
//                    enable((TextView)v);
//                    mIsAcSelected = true;
//                } else {
//                    disable((TextView)v);
//                    mIsAcSelected = false;
//                }
//                break;
             } else if(v.getId() == R.id.tvNonAc) {
                 if (!mIsNonAcSelected) {
                     enable((TextView) v);
                     mIsNonAcSelected = true;
                 } else {
                     disable((TextView) v);
                     mIsNonAcSelected = false;
                 }

             } else if (v.getId() == R.id.tvSleeper) {
                 if (!mIsSleeperSelected) {
                     enable((TextView) v);
                     mIsSleeperSelected = true;
                 } else {
                     disable((TextView) v);
                     mIsSleeperSelected = false;
                 }

             } else if(v.getId() == R.id.tvSeater) {
                 if (!mIsSeaterSelected) {
                     enable((TextView) v);
                     mIsSeaterSelected = true;
                 } else {
                     disable((TextView) v);
                     mIsSeaterSelected = false;

                 }
//             } else if(v.getId() ==  R.id.btnSearch) {
//                 //    if (validate()) {
//
////                mFrom = mEdFromCity.getText().toString();
////                mTo = mEdToCity.getText().toString();
//                 Intent intent = new Intent(getActivity(), BusListActivity.class);
//                 intent.putExtra(Constants.intentdata.TRIP_KEY, mFrom + " To " + mTo);
//                 startActivity(intent);
                 //    }
             }
             else if(v.getId() == R.id.tvViewallNewOffer) {
                 Intent i = new Intent(getActivity(), OffersActivity.class);
                 i.putExtra(Constants.intentdata.OFFER, mNewOfferList);
                 startActivity(i);
             }

    }

    private String updateSearchLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(mDepartDateCalendar.getTime());
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

       String birthDay = sdf.format(mDepartDateCalendar.getTime());
        binding.edOneWay.setText(birthDay);
    }
    /* validation */
    private boolean validate() {
        boolean flag = true;
//        if (mEdFromCity.getSelectedItemPosition() == 0) {
//            flag = false;
//            ((BaseActivity) requireActivity()).showToast(getString(R.string.msg_from));
//        } else if (mEdToCity.getSelectedItemPosition() == 0) {
//            flag = false;
//            ((BaseActivity) requireActivity()).showToast(getString(R.string.msg_to));
//        }
        return flag;
    }

    /* disable textview */
    private void disable(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.textchild));
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), R.color.textchild), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* enable textview */
    private void enable(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.startcolor));
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), R.color.startcolor), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    HomeBusAdapter mRecentSearchAdapter;
     @Override
    public void onResponse(boolean isSuccess, HomeResponse homeResponse) {
         binding.mainProgress.setVisibility(View.GONE);
         binding.progresss.setVisibility(View.GONE);
        try {
            if (homeResponse.getMrt7al().getData() != null) {
                mRecentSearchList.addAll(homeResponse.getMrt7al().getData());
                if (page > 1) {
                    mRecentSearchAdapter.notifyDataSetChanged();
                } else {
                    binding.rvBus.setLayoutManager(currentManager);
                    mRecentSearchAdapter = new HomeBusAdapter(requireActivity(), mRecentSearchList, this);
                    binding.rvBus.setAdapter(mRecentSearchAdapter);
                    Date date = convertStringToDate(mRecentSearchList.get(0).getDatetime_from()
                            .replace("T", " ").replace("+03:00", ""));
                    if (date != null) {
                        mDepartDateCalendar.setTime(date);
                        binding.tvDate.setText(Constants.DateFormat.DAY_MONTH_YEAR_FORMATTER.format(mDepartDateCalendar.getTime()));
//                        if(page ==1)
//                        mRecentSearchAdapter.FilterData(Constants.DateFormat.FilterFormat.format(mDepartDateCalendar.getTime()));
                    }
                }
                binding.noData.setVisibility(View.GONE);
                binding.progresss.setVisibility(View.GONE);
                binding.rvBus.setVisibility(View.VISIBLE);
            } else {
                binding.rvBus.setVisibility(View.GONE);
                binding.noData.setVisibility(View.VISIBLE);
                binding.progresss.setVisibility(View.GONE);
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
         isLoadingData = false;

     }
    private ErrorResponse404 errorResponse;
    @Override
    public void handleError(Throwable t) {
        binding.mainProgress.setVisibility(View.GONE);
        binding.progresss.setVisibility(View.GONE);
        try{
        if (ConnectivityReceiver.isConnected()) {
            if (t instanceof HttpException) {
                int code = ((HttpException) t).code();
                if (code == 403) {
                    if (!DialogsHelper.isLoginDialogOnScreen())
                        DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
                } else if (code == 404) {
                    try {
                        ResponseBody body = ((HttpException) t).response().errorBody();
                        Gson gson = new Gson();
                        assert body != null;
                        errorResponse = gson.fromJson(body.string(), ErrorResponse404.class);
                    } catch (IOException | JsonSyntaxException ioException) {
                        ioException.printStackTrace();
                    }
//                    if (errorResponse.getMrt7al() != null) {
//                    Toast.makeText(requireActivity(), errorResponse.getMrt7al().getMsg(), Toast.LENGTH_SHORT).show();
                        if (page ==1) {
                            mRecentSearchAdapter.clearData();
                            binding.noData.setVisibility(View.VISIBLE);
                        }
//                    } else {
//                        Toast.makeText(requireActivity(), "خطأ بالبيانات", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        } else {
            Toast.makeText(requireActivity(), "تأكد من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
        }
    }catch (IllegalStateException e){
        e.printStackTrace();
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
    @Override
    public void onFavouriteAd(boolean success, String message) {

    }

    ArrayList<String> fromList = new ArrayList<>();
    ArrayList<String> toList = new ArrayList<>();

    ArrayList<RegisterCollectionResponse.Mrt7alBean.DataBean.CitiesBean> cities = new ArrayList<>();
    ArrayAdapter<String> fromAdapter,toAdapter;
    int fromSelection = -1,toSelection = -1;
    @Override
    public void onGetCollections(boolean success, RegisterCollectionResponse registerCollectionResponse) {
       try{ cities.addAll(registerCollectionResponse.getMrt7al().getData().getCities());
        fromList.add(getString(R.string.hint_from_city));
        toList.add(getString(R.string.hint_to_city));
        for (int i=0;i<cities.size();i++){
            fromList.add(cities.get(i).getName());
            toList.add(cities.get(i).getName());
        }
        setupFromSpinner(binding.edFromCity);
        setupToSpinner(binding.edToCity);
       } catch (NullPointerException | IllegalStateException a){
           a.printStackTrace();
       }

    }

    private void setUpadultSpinner(Spinner spinners) {
        List<String> s = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        ArrayList<String> NameList = new ArrayList<>(s);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.adultLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.adult.setText(NameList.get(position));
                    adultCount = Integer.parseInt(NameList.get(position));
                } else {
                    adultCount = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setUpChildSpinner(Spinner spinners) {
        List<String> s = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        ArrayList<String> NameList = new ArrayList<>(s);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.childLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.child.setText(NameList.get(position));
                    childCount = Integer.parseInt(NameList.get(position));
                 } else {
                    childCount =0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setUpBabySpinner(Spinner spinners) {
        List<String> s = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        ArrayList<String> NameList = new ArrayList<>(s);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    binding.babyLayout.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.edit_text_background));
                    binding.baby.setText(NameList.get(position));
                    babyCount = Integer.parseInt(NameList.get(position));
                 } else {
                    babyCount = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
     private void setupFromSpinner(SearchableSpinner spinners) {
        fromAdapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, fromList);
        fromAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(fromAdapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i== 0){
                    fromSelection = -1;
                } else {
                    fromSelection = i -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }



    private void setupToSpinner(SearchableSpinner spinners) {
          toAdapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, toList);
        toAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(toAdapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i== 0){
                    toSelection = -1;
                } else {
                    toSelection = i -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }


    @Override
    public void onReserve(HomeResponse.Mrt7alBean.DataBean dataBean, int pos) {
        if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
            Gson gson = new Gson();
            String model = gson.toJson(dataBean, HomeResponse.Mrt7alBean.DataBean.class);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putString("pos", String.valueOf(pos));
            bundle.putString("page", "home");
            Navigation.findNavController(requireActivity(), R.id.main_fragment).
                    navigate(R.id.action_homeFragmentNewest_to_addPassengersFragment, bundle);
        } else {
            DialogsHelper.showLoginDialog(getString(R.string.please_login),requireActivity());
        }
    }

    @Override
    public void onReserve(SearchTripsResponse.Mrt7alBean.DataBean dataBean, int pos) {
        if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
             Gson gson = new Gson();
            String model = gson.toJson(dataBean,SearchTripsResponse.Mrt7alBean.DataBean.class);
            Bundle b = new Bundle();
            b.putString("model",model);
            b.putString("page","search");
            b.putString("pos",String.valueOf(pos));
            Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(R.id.action_searchTripsFragment_to_reservationBottomSheet,b);
        } else {
            DialogsHelper.showLoginDialog(getString(R.string.please_login),requireActivity());
        }
    }
}
