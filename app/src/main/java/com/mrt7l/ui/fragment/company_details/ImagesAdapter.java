package com.mrt7l.ui.fragment.company_details;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mrt7l.R;
import com.mrt7l.databinding.ImageItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.SpecialViewHodler> {

    private Context context;
    private ViewPager viewPager;
    private int selectedPosition ;
    private ArrayList<CompanyDetailsResponse.Mrt7alBean.DataBean.CompanyPhotosBean> sliderImagesId;
    public ImagesAdapter(Context context,  ArrayList<CompanyDetailsResponse.Mrt7alBean.DataBean.CompanyPhotosBean> sliderImagesId , ViewPager mViewPager){
        this.context = context ;
        this.sliderImagesId = sliderImagesId ;
        viewPager = mViewPager;
        selectedPosition = viewPager.getCurrentItem() ;
    }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding binding =ImageItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SpecialViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
        holder.binding.image.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get().load("https://administrator.mrt7al.com/" +
                sliderImagesId.get(position).getPhoto()).placeholder(R.mipmap.ic_launcher)
    .into(holder.binding.image);


        if(selectedPosition  == position){
            holder.binding.image.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.binding.image.setPadding(2,2,2,2);
        }
        else{
            holder.binding.image.setBackgroundColor(0);
            holder.binding.image.setPadding(0,0,0,0);
        }

        holder.binding.image.setOnClickListener(v -> {
            viewPager.setCurrentItem(position);
            changeBorderColor(holder);
        });
    }

    @Override
    public int getItemCount() {
        return sliderImagesId.size();
    }
    public void setSelectedPosition(int position){
        selectedPosition = position;
        notifyDataSetChanged();
    }


    private void changeBorderColor(SpecialViewHodler holder) {
        int currentPosition = holder.getLayoutPosition() ;
        if(selectedPosition!=currentPosition){
            int lastSelectionPosition = selectedPosition ;
            selectedPosition = currentPosition ;
            notifyItemChanged(lastSelectionPosition);
            holder.binding.image.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.binding.image.setPadding(2,2,2,2);
        }
    }
    class  SpecialViewHodler  extends RecyclerView.ViewHolder {

        ImageItemBinding binding;
        public SpecialViewHodler(@NonNull ImageItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
