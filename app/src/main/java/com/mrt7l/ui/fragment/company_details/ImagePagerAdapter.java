package com.mrt7l.ui.fragment.company_details;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mrt7l.R;
import com.mrt7l.databinding.PagerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ImagePagerAdapter  extends PagerAdapter {
        Activity mContext;
    ArrayList<CompanyDetailsResponse.Mrt7alBean.DataBean.CompanyPhotosBean>sliderImagesId ;

    ImagePagerAdapter(Activity context , ArrayList<CompanyDetailsResponse.Mrt7alBean.DataBean.CompanyPhotosBean> list) {
            this.mContext = context;
            this.sliderImagesId = list;
        }



        @Override
        public int getCount() {
            return sliderImagesId.size();
        }


        @Override
        public boolean isViewFromObject(@NonNull View v, @NonNull Object obj) {
            return v == ((ImageView) obj);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int i) {
            ImageView mImageView =new ImageView(mContext);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.get().load("https://administrator.mrt7al.com/" +
                    sliderImagesId.get(i).getPhoto()).placeholder(R.mipmap.ic_launcher).
                    into(mImageView);
            ((ViewPager) container).addView(mImageView, 0);
            return mImageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int i, @NonNull Object obj) {
            ((ViewPager) container).removeView((ImageView) obj);
        }


    }



