package com.mrt7l.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mrt7l.R;
import com.mrt7l.model.ReservationListModel;
import com.mrt7l.ui.activity.BookingActivity;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.utils.Constants;


public class    ReservationBottomSheet extends BottomSheetDialogFragment {

    public static boolean isShown = false;

   TextView meOnly;
   TextView current;
    TextView newPassenger;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

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


    @Override
    public void setupDialog(Dialog dialog, int style) {
        isShown = true;
        View contentView = View.inflate(getContext(), R.layout.reservatoion_bottom_sheet, null);
        meOnly = contentView.findViewById(R.id.me);
        current = contentView.findViewById(R.id.pastPassenger);
        newPassenger = contentView.findViewById(R.id.newPassenger);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        contentView.findViewById(R.id.me).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("model",model);
            bundle.putString("me","me");
            bundle.putString("page",page);
            if (position != null){
                bundle.putString("pos",position);
            }
            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
            navController.navigate(R.id.action_reservationBottomSheet_to_reserveForMeFragment,bundle);
        });
        contentView.findViewById(R.id.newPassenger).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("model",model);
            if (position != null){
                bundle.putString("pos",position);
            } bundle.putString("page",page);
            NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
            navController.navigate(R.id.action_reservationBottomSheet_to_addPassengerFragment,bundle);
        });
        contentView.findViewById(R.id.pastPassenger).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("model",model);
            bundle.putString("page",page);
            if (position != null){
                bundle.putString("pos",position);
            }  NavController navController = Navigation.findNavController(requireActivity(),R.id.main_fragment);
            navController.navigate(R.id.action_reservationBottomSheet_to_passengersFragment,bundle);
        });

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
    private String model,page,position;
     @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
           model = getArguments().getString("model");
            page = getArguments().getString("page");
            position = getArguments().getString("pos");
        }
        return super.onCreateDialog(savedInstanceState);
    }
}
