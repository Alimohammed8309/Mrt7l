package com.mrt7l.ui.fragment.company_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrt7l.R;
import com.mrt7l.databinding.HomeItemBinding;
import com.mrt7l.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.BusitemViewHolder> {
    /*variable declaration*/
     private CompanyDetailsResponse.Mrt7alBean mBusList;
     private ArrayList<CompanyDetailsResponse.Mrt7alBean.DataBean.TripDatesBean> allDataList
             = new ArrayList<>();
    private ClickListener clickListener;
    private Activity mContext;
    /*constructor*/

    public TripsAdapter(CompanyDetailsResponse.Mrt7alBean aBusList,
                        ClickListener listener, Activity context) {
        /* initialize parameter*/
         this.mBusList = aBusList;
        this.clickListener  = listener;
        allDataList.addAll(mBusList.getData().getTrip_dates());
        mContext = context;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public TripsAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusitemViewHolder viewHolder;
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.home_item, parent,false);
        viewHolder = new BusitemViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusitemViewHolder holder , final int position) {
        if(mBusList.getData().getTrip_dates().get(position).getAvab_chairs() >0) {
            holder.binding.availableSeats.setText("متاح");
        } else {
            holder.binding.availableSeats.setText("غير متاح");
        }
               holder.binding.companyName.setText(mBusList.getData().getName());
            if (!mBusList.getData().getIs_star().equals("off")){
                holder.binding.advancedLayout.setVisibility(View.VISIBLE);
            } else {
                holder.binding.advancedLayout.setVisibility(View.GONE);
            }

        holder.binding.tripDate.setText(formatDate(mBusList.getData().getTrip_dates().get(position).getDatetime_from()));
        Glide.with(mContext).load("https://administrator.mrt7al.com/" +
                    mBusList.getData().getLogo_pic()).into(holder.binding.companyImage);
        holder.binding.tvStartTime.setText(mBusList.getData().getTrip_dates().get(position).getArrived_time());
        holder.binding.tvEndTime.setText(mBusList.getData().getTrip_dates().get(position).getGo_time());
     int hours = (int) Math.round(mBusList.getData().getTrip_dates().get(position).getWaitingTime()/60);
        int minuts ;
        if(hours >=1) {
             minuts = (int) Math.round(mBusList.getData().getTrip_dates().get(position).getWaitingTime()  - (hours *60));
        } else {
              minuts = mBusList.getData().getTrip_dates().get(position).getWaitingTime();
        }
        if(hours >= 0 && minuts>=0) {
            if (minuts < 10) {
                holder.binding.tvTotalDuration.setText("0" + hours + ":0" + minuts);
            } else {
                holder.binding.tvTotalDuration.setText("0" + hours + ":" + minuts);
            }
        } else {
            holder.binding.tvTotalDuration.setText("00:00");
        }
        holder.binding.tvStartTimeAA.setText(mBusList.getData().getTrip_dates().get(position).getFrom_city().getName());
        holder.binding.tvEndTimeAA.setText(mBusList.getData().getTrip_dates().get(position).getTo_city().getName());
        holder.binding.tripDate.setText(formatDate(mBusList.getData().getTrip_dates().get(position).getDatetime_from()));
        holder.binding.price.setText( mBusList.getData().getTrip_dates().get(position).getPrice()+" ريال "  );
        holder.binding.busType.setText("نوع النقل :" + mBusList.getData().getTrip_dates().get(position).getBus_type());
        if (mBusList.getData().getTrip_dates().get(position).getType() != null) {
            if (mBusList.getData().getTrip_dates().get(position).getType().equals("غير مباشر")) {
                holder.binding.unDirectLayout.setVisibility(View.VISIBLE);
                holder.binding.directLayout.setVisibility(View.GONE);
            } else {
                holder.binding.unDirectLayout.setVisibility(View.GONE);
                holder.binding.directLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.binding.unDirectLayout.setVisibility(View.GONE);
            holder.binding.directLayout.setVisibility(View.GONE);
        }
        holder.binding.reserve.setOnClickListener(view -> clickListener.onReserve(mBusList.getData(),position));
    }

    public interface ClickListener{
        void onReserve(CompanyDetailsResponse.Mrt7alBean.DataBean dataBean, int pos);
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return mBusList.getData().getTrip_dates().size();
    }
    static class BusitemViewHolder extends RecyclerView.ViewHolder {
        public HomeItemBinding binding;
        BusitemViewHolder(HomeItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
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
                assert tdate != null;
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



    public void FilterData(String date){
        mBusList.getData().getTrip_dates().clear();
        for (int i=0;i<allDataList.size();i++){
            String dd = "";
            String currentDate  = allDataList.get(i).getDatetime_from()
                    .replace("T"," ")
                    .replace("+03:00","");
            try {
                Date s = convertStringToDate(currentDate);
                dd = Constants.DateFormat.FilterFormat.format(s);
            }catch (Exception e){
                //e.printStackTrace();
            }

            if(  dd.equals(date)) {
                mBusList.getData().getTrip_dates().add(allDataList.get(i));

            }
        }
        notifyDataSetChanged();
    }

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