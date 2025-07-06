package com.mrt7l.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.helpers.DialogsHelper;
import com.mrt7l.helpers.PreferenceHelper;
import com.mrt7l.ui.fragment.home.HomeFragmentNewest;
import com.mrt7l.ui.fragment.stations.StationsFragment;
import com.onesignal.Continue;
import com.onesignal.OneSignal;

public class DashboardActivity extends BaseActivity implements
        View.OnClickListener {
    private ImageView mIvNotification;

    public static TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        OneSignal.getNotifications().requestPermission(false, Continue.none());
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigation);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.main_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
         tvTitle = findViewById(R.id.tvTitle);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        initLayouts();
        initializeListeners();
    }

    /* init layout */
    @SuppressLint("ClickableViewAccessibility")
    public void initLayouts() {
        mIvNotification = findViewById(R.id.ivNotification);
//        TextView title = findViewById(R.id.tvTitle);
//        title.setOnClickListener(view -> {
//            throw new RuntimeException("Test Crash"); // Force a crash
//        });
    }

    /* initialize listener */
    public void initializeListeners() {
        SetNotificationImage(mIvNotification);
        mIvNotification.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

             for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        BroadcastHelper.sendInform(this,"locationGranted");
                    } else {
                        BroadcastHelper.sendInform(this,"LocationRefused");

                    }
                }
            }

    }

    /* set selected item in bottom navigation */
    private void setSelected(ImageView mBarImg) {
        mBarImg.setBackground(getResources().getDrawable(R.drawable.bg_tint_icon));
    }

    /* onBack press */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /* onClick listener */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivNotification:
                if (new PreferenceHelper(this).getUSERID() != 0) {
                    Navigation.findNavController(this,R.id.main_fragment).navigate(R.id.action_notifications);
                } else {
                    DialogsHelper.showLoginDialog(getString(R.string.please_login), this);
                }
                break;
        }
    }

    /* get Activity result */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }




}
