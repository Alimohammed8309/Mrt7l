package com.mrt7l.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrt7l.R;
import com.mrt7l.databinding.HomeItemBinding;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.home.HomeResponse;
import com.mrt7l.utils.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeBusAdapter extends RecyclerView.Adapter<HomeBusAdapter.BusitemViewHolder> {
    /*variable declaration*/
    private final Activity mContext;
    private  List<HomeResponse.Mrt7alBean.DataBean> mRecentSearchList = new ArrayList<>();
    private  List<HomeResponse.Mrt7alBean.DataBean> allDataList = new ArrayList<>();
    private final ClickListener clickListener;
    private final CompanyDetailsResponse companyDetailsResponse;

    /*constructor*/

    public HomeBusAdapter(Activity aCtx, List<HomeResponse.Mrt7alBean.DataBean> aBusList, ClickListener listener) {
        /* initialize parameter*/
        this.mContext = aCtx;
        this.mRecentSearchList=aBusList;
        this.allDataList=aBusList;
        this.clickListener  = listener;
        companyDetailsResponse= CompanyDetailsResponse.getInstance();
    }

    /*  inflate layout */
     @NonNull
    @Override
    public HomeBusAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         BusitemViewHolder viewHolder;
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.home_item, parent,false);
        viewHolder = new  BusitemViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBusAdapter.BusitemViewHolder holder , final int position) {
        if(mRecentSearchList.get(position).getPassengerNetAvailable() >0) {
            holder.binding.availableSeats.setText("متاح");
        } else {
            holder.binding.availableSeats.setText("غير متاح");
        }
        if (mRecentSearchList.get(position).getCompany() != null) {
            holder.binding.companyName.setText(mRecentSearchList.get(position).getCompany().getName());
            if (!mRecentSearchList.get(position).getCompany().getIs_star().equals("off")){
                holder.binding.advancedLayout.setVisibility(View.VISIBLE);
            } else {
                holder.binding.advancedLayout.setVisibility(View.GONE);
            }
        }
        holder.binding.tripDate.setText(formatDate(mRecentSearchList.get(position).getDatetime_from()));
        holder.binding.companyName.setOnClickListener(v -> {
            companyDetailsResponse.setMrt7al(null);
            NavController navController = Navigation.findNavController(mContext,R.id.main_fragment);
            Bundle bundle = new Bundle();
            bundle.putString("companyID", String.valueOf(mRecentSearchList.get(position).getCompany_id()));
            try {
                navController.navigate(R.id.action_homeFragmentNewest_to_companyDetailsFragment, bundle);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        });
        if (mRecentSearchList.get(position).getCompany() != null) {
            Glide.with(mContext).load("https://administrator.mrt7al.com/" +
                    mRecentSearchList.get(position).getCompany().getLogo_pic()).into(holder.binding.companyImage);
        }
        holder.binding.tvStartTime.setText(mRecentSearchList.get(position).getArrived_time());
        holder.binding.tvEndTime.setText(mRecentSearchList.get(position).getGo_time());
        int hours = (int) Math.round(mRecentSearchList.get(position).getWaitingTime()/60);
        int minuts ;
        if(hours >=1) {
             minuts = (int) Math.round(mRecentSearchList.get(position).getWaitingTime()  - (hours *60));
        } else {
              minuts = mRecentSearchList.get(position).getWaitingTime();
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

        holder.binding.tvStartTimeAA.setText(mRecentSearchList.get(position).getFrom_city().getName());
        holder.binding.tvEndTimeAA.setText(mRecentSearchList.get(position).getTo_city().getName());
        holder.binding.tripDate.setText(formatDate(mRecentSearchList.get(position).getDatetime_from()));
//        holder1.mTvHold.setText(String.format(mContext.getString(R.string.text_addhold), mBusList.get(position).get()));
        holder.binding.price.setText( mRecentSearchList.get(position).getPrice()+" ريال "  );
//        holder1.mTvRatingBar.setText(String.format(mContext.getString(R.string.text_add5), mBusList.get(position).getRate()));
        holder.binding.busType.setText("نوع النقل :" + mRecentSearchList.get(position).getBus_type());
            if (mRecentSearchList.get(position).getIs_direct() != null) {
                if (mRecentSearchList.get(position).getIs_direct().equals("غير مباشر")) {
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

        holder.binding.reserve.setOnClickListener(view -> clickListener.onReserve(mRecentSearchList.get(position),position));

    }

    public interface ClickListener{
        void onReserve(HomeResponse.Mrt7alBean.DataBean dataBean,int pos);
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return mRecentSearchList.size();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return theDate;
    }
    public void clearData(){
        mRecentSearchList.clear();
        allDataList.clear();
        notifyDataSetChanged();
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
            e.printStackTrace();
        }
        return currentdate;

    }



    public void FilterData(String date){
        mRecentSearchList.clear();
        for (int i=0;i<allDataList.size();i++){
            String dd = "";
            String currentDate  = allDataList.get(i).getDatetime_from()
                    .replace("T"," ")
                    .replace("+03:00","");
            try {
                Date s = convertStringToDate(currentDate);
                 dd = Constants.DateFormat.FilterFormat.format(s);
            }catch (Exception e){
                e.printStackTrace();
            }

           if(  dd.equals(date)) {
               mRecentSearchList.add(allDataList.get(i));

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
            e.printStackTrace();
        }
        return date;
    }
}