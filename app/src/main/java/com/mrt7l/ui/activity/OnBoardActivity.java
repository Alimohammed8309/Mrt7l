package com.mrt7l.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.mrt7l.R;
import com.mrt7l.helpers.PreferenceHelper;


import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_on_board);
        new PreferenceHelper(OnBoardActivity.this).setOnboardLoaded(true);
        AhoyOnboarderCard scr1 = new AhoyOnboarderCard ("ابحث عن حجزك",
                "نساعدك على البحث ضمن مواعيد حجز عدد كبير من شركات النقل بضغطة زر واحدة",
                R.drawable.onboard1);
        scr1.setIconLayoutParams(600,600,140,0,0,0);
        scr1.setDescriptionColor(R.color.white);
        scr1.setTitleColor(R.color.white);
        scr1.setBackgroundColor(R.color.onboard_blue);
        AhoyOnboarderCard scr2 = new AhoyOnboarderCard("الحجز اونلاين",
                "نساعدك على حجز رحلتك بشكل اسرع دون تكبد عناء الذهاب إلى محطات النقل",
                R.drawable.onboard2);
        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.onboard_blue);
        colorList.add(R.color.move);
        colorList.add(R.color.colorPrimary);
        setColorBackground(colorList);
//        setColorBackground(R.color.white);

        scr2.setDescriptionColor(R.color.white);
        scr2.setTitleColor(R.color.white);
        scr2.setBackgroundColor(R.color.move);
        scr2.setIconLayoutParams(550,550,140,0,0,0);
        AhoyOnboarderCard scr3 = new AhoyOnboarderCard("تأكيد حجز",
                "نساعدك على تأكيد حجز لدى شركات النقل بشكل أسهل وأكثر مرونة",
                R.drawable.white_logo_onboard);
        scr3.setDescriptionColor(R.color.white);
        scr3.setTitleColor(R.color.white);
        scr3.setBackgroundColor(R.color.colorPrimary);
        scr2.setDescriptionTextSize(16);
        scr3.setDescriptionTextSize(16);
        scr1.setDescriptionTextSize(16);
        scr3.setIconLayoutParams(650,650,140,0,0,0);
        setFinishButtonTitle(getString(R.string.reg_now));
         ArrayList<AhoyOnboarderCard> elements = new ArrayList<>();
         elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        Typeface face = Typeface.createFromAsset(getAssets(), "font/ns_ar.ttf");
        setFont(face);
        setOnboardPages(elements);
    }

        @Override
        public void onFinishButtonPressed() {
            if (new PreferenceHelper(OnBoardActivity.this).getUSERID() != 0){
                startActivity(DashboardActivity.class);
            } else {
                startActivity(PhoneActivity.class);
            }
        }

          private void startActivity(Class aClass) {
        startActivity(new Intent(this, aClass));
        finish();
    }

}