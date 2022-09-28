package com.mrt7l.ui.fragment.stations;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.mrt7l.R;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.utils.Constants;


public class StationsBottomSheet extends BottomSheetDialogFragment implements StationsAdapter.ClickListener {

    public static boolean isShown = false;

    RecyclerView stations;


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


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        isShown = true;
        View contentView = View.inflate(getContext(), R.layout.stations_bottom_sheet, null);
        stations = contentView.findViewById(R.id.stationsRecycler);
        stations.setLayoutManager(new LinearLayoutManager(requireActivity()));
        StationsAdapter adapter = new StationsAdapter(requireActivity(),obj.getMrt7al().getData(),this);
        stations.setAdapter(adapter);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        if (behavior instanceof BottomSheetBehavior  ) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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


    public static StationsBottomSheet newInstance(CompanyDetailsResponse.Mrt7alBean.DataBean.TripDatesBean company) {
        Bundle args = new Bundle();
        StationsBottomSheet fragment = new StationsBottomSheet();
        args.putString(Constants.MeOnly, "حجز الرحلة لي");
        args.putString(Constants.currentOther, "حجز الرحلة لمسافر سابق");
        args.putString(Constants.newOther, "حجز الرحلة لمسافر جديد");
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onDismiss(@NonNull DialogInterface dialog) {

        super.onDismiss(dialog);
        isShown = false;
    }
    private String model;
    private StationsResponse obj;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            model = getArguments().getString("model");
            obj = new Gson().fromJson(model,StationsResponse.class);


        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void OnClick() {
        Navigation.findNavController(requireActivity(),R.id.main_fragment).navigateUp();
    }
}