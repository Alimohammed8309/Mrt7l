package com.mrt7l.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.mrt7l.R;
import com.mrt7l.databinding.HomeItemBinding;
import com.mrt7l.ui.fragment.home.HomeResponse;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder> {

    /*variable declaration*/
     private Context mContext;
    private ArrayList<HomeResponse.Mrt7alBean.DataBean> mRecentSearchList;
    private RecentSearchAdapter.ClickListener clickListener;

    /*constructor*/
    public RecentSearchAdapter(Context aContext, ArrayList<HomeResponse.Mrt7alBean.DataBean> aRecentSearchlist) {
        /* initialize parameter*/
        this.mContext = aContext;
        this.mRecentSearchList = aRecentSearchlist;
    }

    /*set onClick listener*/
    public void setOnClickListener(ClickListener mListener) {
        this.clickListener = mListener;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecentSearchViewHolder viewHolder;
         HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                 ,R.layout.home_item, parent,false);
        viewHolder = new RecentSearchViewHolder(binding);
        return viewHolder;
    }

    /*bind viewholder*/
    @Override
    public void onBindViewHolder(@NonNull RecentSearchViewHolder holder, int position) {
        final HomeResponse.Mrt7alBean.DataBean mBookibgModel = mRecentSearchList.get(position);
//        holder.binding.favourite.setOnClickListener(view -> mListener.onFavourite(mBookibgModel,position));
         holder.binding.availableSeats.setText(" الأماكن المتاحة : " + mRecentSearchList.get(position).getPassengerNetAvailable());
        holder.binding.companyName.setText(mRecentSearchList.get(position).getCompany().getName());
        holder.binding.tvStartTime.setText(formatTime(mRecentSearchList.get(position).getDatetime_from()));
//        holder.binding.mTvTypeCoach.setText(formatDate(mBusList.get(position).getDatetime_from()));
        holder.binding.tvEndTime.setText(formatTime(mRecentSearchList.get(position).getDatetime_to()));
        holder.binding.tvTotalDuration.setText(getCurrentTime(mRecentSearchList.get(position).getDatetime_from(),
                mRecentSearchList.get(position).getDatetime_to()));
        Glide.with(mContext).load("https://administrator.mrt7al.com/" +
                mRecentSearchList.get(position).getCompany().getLogo_pic()).into(holder.binding.companyImage);

        holder.binding.tvStartTimeAA.setText(mRecentSearchList.get(position).getFrom_city().getName());
        holder.binding.tvEndTimeAA.setText(mRecentSearchList.get(position).getTo_city().getName());
//        holder1.mTvHold.setText(String.format(mContext.getString(R.string.text_addhold), mBusList.get(position).get()));
        holder.binding.price.setText( mRecentSearchList.get(position).getPrice()+" ريال "  );
//        holder1.mTvRatingBar.setText(String.format(mContext.getString(R.string.text_add5), mBusList.get(position).getRate()));
        holder.binding.busType.setText(mRecentSearchList.get(position).getTripType());
        if (mRecentSearchList.get(position).getIs_direct().equals("غير مباشر")){
            holder.binding.unDirectLayout.setVisibility(View.VISIBLE);
            holder.binding.directLayout.setVisibility(View.GONE);
        } else {
            holder.binding.unDirectLayout.setVisibility(View.GONE);
            holder.binding.directLayout.setVisibility(View.VISIBLE);
        }

        if (!mRecentSearchList.get(position).getCompany().getIs_star().equals("off")){
            holder.binding.advancedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.advancedLayout.setVisibility(View.GONE);
        }

        holder.binding.reserve.setOnClickListener(view -> {
            clickListener.onReserve(mRecentSearchList.get(position),position);
        });

    }
    public interface ClickListener{
        void onReserve(HomeResponse.Mrt7alBean.DataBean dataBean, int pos);
    }
    public String formatDate(String date){
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH);

        try {
            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return theDate;
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return mRecentSearchList.size();
    }

    /*onclick listener interface*/


    /*view holder*/
    static class RecentSearchViewHolder extends RecyclerView.ViewHolder {
        public HomeItemBinding binding;
        RecentSearchViewHolder(HomeItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
    public String formatTime(String date){
        String theDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formats = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH);

        try {
            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
            assert datse != null;
            theDate = formats.format(datse);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return theDate;
    }

    public String getCurrentTime(String adDate,String toDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String currentdate = "";

        //  long different = endDate.getTime() - startDate.getTime();
        try{
            if (adDate != null) {
                Date date = format.parse(adDate.replace("T" , " ").replace("+03:00",""));
                Date tdate = format.parse(toDate.replace("T" , " ").replace("+03:00",""));

                assert date != null;


                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;
                long monthesInMilli = daysInMilli * 30;
                long yearInMilli = monthesInMilli * 12;
                long different = tdate.getTime() - date.getTime();

                long elapsedyears = different / yearInMilli;
                different = different % yearInMilli;

                long elapsedmonths = different / monthesInMilli;
                different = different % monthesInMilli;

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;


                currentdate = String.valueOf(Integer.parseInt(String.valueOf(elapsedHours)));

            }
        } catch (ParseException e) {
            // TODO Auto-generated catch blockf
            //e.printStackTrace();
        }
        return currentdate;

    }


//    public void FilterData(String date){
//        mBusList.clear();
//        for (int i=0;i<allDataList.size();i++){
//            String dd = "";
//            String currentDate  = allDataList.get(i).getDatetime_from()
//                    .replace("T"," ")
//                    .replace("+03:00","");
//            try {
//                Date s = convertStringToDate(currentDate);
//                dd = Constants.DateFormat.FilterFormat.format(s);
//            }catch (Exception e){
//                //e.printStackTrace();
//            }
//
//            if(  dd.equals(date)) {
//                mBusList.add(allDataList.get(i));
//
//            }
//        }
//        notifyDataSetChanged();
//    }

    private Date convertStringToDate (String stringDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = null ;
        try {
            date = format.parse(stringDate);
            System.out.println(date);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return date;
    }
}

