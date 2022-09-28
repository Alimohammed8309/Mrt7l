package com.mrt7l.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.LogoutDialogBinding;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.activity.DashboardActivity;
import com.mrt7l.ui.activity.ProfileSettingsActivity;
import com.mrt7l.ui.activity.SettingActivity;
import com.mrt7l.ui.activity.SignInActivity;

import java.util.Objects;

public class MoreFragment extends Fragment implements View.OnClickListener {

    /*variable declaration*/
    public static final String mTitle = "More";
    private TextView mTvProfileSettings;
    private TextView passengers;
    private TextView myTrips;
    private TextView favourite;
    private TextView explain;
    private TextView about;
    private TextView mTvHelp;
    private TextView mTvLogout;
    private TextView mTvSetting;

    /* create view */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container,false);
        initLayouts(view);
        initializeListeners();
        if (new PreferenceHelper(requireActivity()).getUSERID() == 0) {
            mTvLogout.setText(getString(R.string.login));
        } else {
            mTvLogout.setText(getString(R.string.text_logout));
        }
            return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (DashboardActivity.tvTitle != null){
            DashboardActivity.tvTitle.setText(getString(R.string.more));
        }
    }
    /* initialize listener */
    private void initializeListeners() {
        mTvProfileSettings.setOnClickListener(this);
        favourite.setOnClickListener(this);
        about.setOnClickListener(this);
         mTvHelp.setOnClickListener(this);
        mTvLogout.setOnClickListener(this);
        mTvSetting.setOnClickListener(this);
        explain.setOnClickListener(view -> Navigation.findNavController(requireActivity(),R.id.main_fragment).
                navigate(R.id.action_moreFragment_to_explainAppFragment));
        passengers.setOnClickListener(v -> Navigation.findNavController(requireActivity(),R.id.main_fragment).
                navigate(R.id.action_moreFragment_to_explainAppFragment));
        myTrips.setOnClickListener(v -> Navigation.findNavController(requireActivity(),R.id.main_fragment).
                navigate(R.id.action_moreFragment_to_explainAppFragment));
    }

    /* init layout */
    private void initLayouts(View view) {
        mTvProfileSettings = view.findViewById(R.id.tvProfileSettings);
        favourite = view.findViewById(R.id.favourite);
        about = view.findViewById(R.id.aboutApp);
         mTvHelp = view.findViewById(R.id.tvHelp);
        mTvLogout = view.findViewById(R.id.tvLogout);
        explain = view.findViewById(R.id.previewApp);
        myTrips = view.findViewById(R.id.reservations);
        passengers = view.findViewById(R.id.passengers);
        mTvSetting = view.findViewById(R.id.tvSetting);
        TextView stations = view.findViewById(R.id.stations);
        stations.setOnClickListener(view1 ->
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_moreFragment_to_stationsFragment));
    }

    /* onClick listener */
    @Override
    public void onClick(View v) {
        if (v == mTvProfileSettings) {
            if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
                ((BaseActivity) requireActivity()).startActivity(ProfileSettingsActivity.class);
            } else {
                DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
            }
        } else if (v == favourite){
            if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_moreFragment_to_favouriteFragment);
            } else {
                DialogsHelper.showLoginDialog(getString(R.string.please_login), requireActivity());
            }
        }
        else if (v == about) {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_moreFragment_to_aboutFragment);

        }        else if (v == mTvHelp)
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_moreFragment_to_contactUsFragment);
        else if (v == mTvSetting)
            ((BaseActivity) requireActivity()).startActivity(SettingActivity.class);
        else if (v == mTvLogout) {
            if (new PreferenceHelper(requireActivity()).getUSERID() != 0) {
                showLogoutDialog();
            } else {
                Intent intent = new Intent(requireActivity(),SignInActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        }

    }

Dialog logoutDialog;
    public void showLogoutDialog( ) {
        if(!requireActivity().isFinishing()) {
            logoutDialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logoutDialog.setCancelable(false);
            LogoutDialogBinding registerErrorDialogBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(requireActivity()),
                            R.layout.logout_dialog, null, false);
             registerErrorDialogBinding.okButton.setOnClickListener(v -> {
                 new PreferenceHelper(requireActivity()).Logout();
                 startActivity(new Intent(getActivity(), SignInActivity.class));
            });
            registerErrorDialogBinding.cancel.setOnClickListener(view -> logoutDialog.cancel());
            logoutDialog.setContentView(registerErrorDialogBinding.getRoot());
            logoutDialog.show();
        }
    }
}