package com.mrt7l.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;


import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.helpers.PreferenceHelper;

public class SplashActivity extends BaseActivity {

    /*variable declaration*/
    private ImageView mIVLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initLayouts();
        initializeListeners();

    }

    /* init layout */
    private void initLayouts() {

        mIVLogo = findViewById(R.id.ivLogo);
    }

    /* initialize listener */
    private void initializeListeners() {
//        Glide.with(this).load(R.raw.ic_logo).into(mIVLogo);

        new Handler().postDelayed(() -> {
            if (!new PreferenceHelper(SplashActivity.this).getOnboardloaded()){
                startActivity(OnBoardActivity.class);
            } else {
                if (new PreferenceHelper(SplashActivity.this).getUSERID() != 0){
                    startActivity(DashboardActivity.class);
                } else {
                    startActivity(PhoneActivity.class);
                }
            }
                    finish();
//            try {
//                onBackPressed();
//            } catch (NullPointerException e){
//                e.printStackTrace();
//            }
                    },
                2000);
    }

}
