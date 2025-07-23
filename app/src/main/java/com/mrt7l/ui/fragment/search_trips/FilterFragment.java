package com.mrt7l.ui.fragment.search_trips;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mrt7l.R;
import com.mrt7l.databinding.FilterBottomFragmentBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.model.RegisterCollectionResponse;
import com.mrt7l.ui.ReservationBottomSheet;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.home.HomeViewModel;
import com.mrt7l.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends BottomSheetDialogFragment {


    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
      * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(FilterInterface filterInterface) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
//        setNavigator(SearchTripsFragment.newInstance());

    }
    private WeakReference<FilterInterface> mNavigator;
    private FilterInterface getNavigator() {
        return mNavigator.get();
    }

    void setNavigator(FilterInterface navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
    RegisterCollectionResponse collectionResponse;
    FilterBottomFragmentBinding binding;
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate( inflater,R.layout.filter_bottom_fragment, container, false);
//
//
//        return binding.getRoot();
//    }

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    Spinner companiesSpinner;
    CheckBox direct,unDirect,all,economy,vip;
    EditText priceFrom,priceTo;
    TextView search,clearAll;
    String isDirect="",fromPrice = "",toPrice= "",busType="";
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_bottom_fragment, null);
        collectionResponse = RegisterCollectionResponse.getInstance();
        direct = contentView.findViewById(R.id.direct);
        unDirect = contentView.findViewById(R.id.unDirect);
        all = contentView.findViewById(R.id.all);
        economy = contentView.findViewById(R.id.economy);
        vip = contentView.findViewById(R.id.vip);
        priceFrom = contentView.findViewById(R.id.priceFrom);
        priceTo = contentView.findViewById(R.id.priceTo);
        companiesSpinner = contentView.findViewById(R.id.comapaniesSpinner);
        search = contentView.findViewById(R.id.search);
        clearAll = contentView.findViewById(R.id.clearAll);
        try {
            setUpCompaniesSpinner(companiesSpinner,collectionResponse.getMrt7al().getData().getCompanies());
        }catch (NullPointerException e){
            //e.printStackTrace();
        }
        if (!isDirect.equals("")){
             if (isDirect.equals("on")){
                 direct.setChecked(true);
             } else {
                 unDirect.setChecked(true);
             }
         }

         if (!busType.equals("")){
             if (busType.equals("vip")){
                 vip.setChecked(true);
             } else {
                 economy.setChecked(true);
             }
         } else {
             all.setChecked(true);
         }

         if (!fromPrice.equals("")){
             priceFrom.setText(fromPrice);
         }
        if (!toPrice.equals("")){
            priceTo.setText(toPrice);
        }
        if (selectedCompanyId != 0){
            for (int i=0;i<collectionResponse.getMrt7al().getData().getCompanies().size();i++){
                if (selectedCompanyId == collectionResponse.getMrt7al().getData().getCompanies().get(i).getId()){
                    companiesSpinner.setSelection(i-1);
                    break;
                }
            }
        }
        all.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                vip.setChecked(false);
                economy.setChecked(false);
            }
        });
        vip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                all.setChecked(false);
             }
        });
        economy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                all.setChecked(false);
            }
        });
        clearAll.setOnClickListener(v -> {
            all.setChecked(true);
            direct.setChecked(false);
            unDirect.setChecked(false);
            companiesSpinner.setSelection(0);
            priceTo.setText("");
            priceFrom.setText("");
        });
        search.setOnClickListener(v -> {
            if (priceFrom.getText().length() > 0)
            fromPrice= priceFrom.getText().toString();
            if (priceTo.getText().length() > 0)
                toPrice= priceTo.getText().toString();
            if (direct.isChecked() && unDirect.isChecked()){
                isDirect = "";
            } else if (direct.isChecked() && !unDirect.isChecked()){
                isDirect = "on";
            } else if (!direct.isChecked() && unDirect.isChecked()){
                isDirect = "off";
            }

            if (vip.isChecked() && economy.isChecked()){
                busType = "";
            } else if (vip.isChecked() && !economy.isChecked()){
                busType = "vip";
            } else if (!vip.isChecked() && economy.isChecked()){
                busType = "normal";
            }
             Intent intent = new Intent();

            intent.putExtra("fromPrice",fromPrice);
            intent.putExtra("toPrice",toPrice);
            intent.putExtra("isDirect",isDirect);
            intent.putExtra("busType",busType);
            intent.putExtra("selectedCompanyId",String.valueOf(selectedCompanyId));
            BroadcastHelper.sendInform(requireContext(),"filterData",intent);
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
            //            getNavigator().onFilterDataRetrieved(fromPrice,toPrice,isDirect,
            //            busType,selectedCompanyId);
        });
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
//        contentView.findViewById(R.id.me).setOnClickListener(view -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("model",model);
//            bundle.putString("me","me");
//            bundle.putString("page",page);
//            if (position != null){
//                bundle.putString("pos",position);
//            }
//            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
//            navController.navigate(R.id.action_reservationBottomSheet_to_reserveForMeFragment,bundle);
//        });
//        contentView.findViewById(R.id.newPassenger).setOnClickListener(view -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("model",model);
//            if (position != null){
//                bundle.putString("pos",position);
//            } bundle.putString("page",page);
//            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
//            navController.navigate(R.id.action_reservationBottomSheet_to_addPassengerFragment,bundle);
//        });
//        contentView.findViewById(R.id.pastPassenger).setOnClickListener(view -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("model",model);
//            bundle.putString("page",page);
//            if (position != null){
//                bundle.putString("pos",position);
//            }  NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
//            navController.navigate(R.id.action_reservationBottomSheet_to_passengersFragment,bundle);
//        });

        if (behavior instanceof BottomSheetBehavior && getActivity() != null) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int screenHeight = displaymetrics.heightPixels;
            ((BottomSheetBehavior) behavior).setPeekHeight(screenHeight);
            ((BottomSheetBehavior) behavior).addBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
//        if (getArguments()!= null) {
//            meOnly.setText(getArguments().getString(Constants.MeOnly));
//            current.setText(getArguments().getString(Constants.currentOther));
//            newPassenger.setText(getArguments().getString(Constants.newOther));
//        }
    }


    public static ReservationBottomSheet newInstance(CompanyDetailsResponse.Mrt7alBean.DataBean.TripDatesBean company) {
        Bundle args = new Bundle();
        ReservationBottomSheet fragment = new ReservationBottomSheet();
//        args.putString(Constants.MeOnly, "حجز الرحلة لي");
//        args.putString(Constants.currentOther, "حجز الرحلة لمسافر سابق");
//        args.putString(Constants.newOther, "حجز الرحلة لمسافر جديد");
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onDismiss(@NonNull DialogInterface dialog) {

        super.onDismiss(dialog);
     }
    private String model,page,position;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){

            isDirect =  getArguments().getString("isDirect");
            fromPrice = getArguments().getString("fromPrice");
            toPrice = getArguments().getString("toprice");
            try{
                selectedCompanyId = Integer.parseInt(Objects.requireNonNull(requireArguments().getString("companyId")));
            } catch (NullPointerException e){
                //e.printStackTrace();
            }
            busType = getArguments().getString("busType");
        }
        collectionResponse = RegisterCollectionResponse.getInstance();
        return super.onCreateDialog(savedInstanceState);
    }
    int selectedCompanyId = 0;

    private void setUpCompaniesSpinner(Spinner spinners, final ArrayList<RegisterCollectionResponse.Mrt7alBean.DataBean.CompaniesBean> initialData) {
        ArrayList<String> NameList = new ArrayList<>();
        NameList.add(getString(R.string.all));
        NameList.addAll(HandleNationalityData(initialData));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.item_spinner, NameList);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinners.setAdapter(adapter);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCompanyId = initialData. get(position-1).getId();
                } else {
                     selectedCompanyId = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private ArrayList<String> HandleNationalityData(ArrayList<RegisterCollectionResponse.Mrt7alBean.DataBean.CompaniesBean> response){
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0; i<response. size();i++){
            arrayList.add(response.get(i).getName());
        }
        return arrayList;
    }
}