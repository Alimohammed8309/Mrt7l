package com.mrt7l.ui.fragment.print_ticket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.R;
import com.mrt7l.databinding.TicketItemBinding;
import com.mrt7l.ui.fragment.mytrips.CurrentOrdersResponse;
import com.mrt7l.ui.fragment.mytrips.PrintTicketModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PrintTicketAdapter extends RecyclerView.Adapter<PrintTicketAdapter.BusitemViewHolder> {
    /*variable declaration*/
    private Activity mContext;
    private ArrayList<PrintTicketModel> mBusList;
    private ClickListener clickListener;
    private CurrentOrdersResponse.Mrt7alBean.DataBean mModel;
    /*constructor*/

    public PrintTicketAdapter(Activity aCtx, ArrayList<PrintTicketModel> aBusList,
                              CurrentOrdersResponse.Mrt7alBean.DataBean model,ClickListener listener) {
        /* initialize parameter*/
        this.mContext = aCtx;
        this.mBusList = aBusList;
        this.clickListener  = listener;
        mModel = model;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public PrintTicketAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusitemViewHolder viewHolder;
        TicketItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ticket_item,parent,false);
        viewHolder = new BusitemViewHolder(binding);
        return viewHolder;
    }


     @Override
    public void onBindViewHolder(@NonNull PrintTicketAdapter.BusitemViewHolder holder1, final int position) {
        holder1.binding.busType.setText(mModel.getTrip_date().getTripType());
        holder1.binding.companyName.setText(mModel.getTrip_date().getCompany().getName());
         holder1.binding.tvStartTime.setText(formatTime(mModel.getTrip_date().getDatetime_from()));
         holder1.binding.tvTotalDuration.setText(getCurrentTime(mModel.getTrip_date().
                 getDatetime_from(),mModel.getTrip_date().getDatetime_to()));
         holder1.binding.tvEndTime.setText(formatTime(mModel.getTrip_date().getDatetime_to()));
         holder1.binding.price.setText(mModel.getTrip_date().getPrice()
         + " ريال ");
         holder1.binding.passngerName.setText(mBusList.get(position).getPassengerName());
         holder1.binding.tripDate.setText(formatDate(mModel.getTrip_date().getDatetime_from()));
         holder1.binding.tripNumber.setText(mBusList.get(position).getTripNumber());
        holder1.binding.arriveTime.setText(formatTime(mModel.getTrip_date().getDatetime_to()));
        holder1.binding.tripTime.setText(formatTime(mModel.getTrip_date().getDatetime_from()));
         holder1.binding.qr.setImageBitmap(mBusList.get(position).getBitmap());
         Picasso.get().load("https://administrator.mrt7al.com/" +
                 mModel.getTrip_date().getCompany().getLogo_pic()).into(holder1.binding.logo);

        holder1.binding.share.setOnClickListener(view -> {
            clickListener.onClickMoreInfo(mBusList.get(position).getBitmap());
        });
    }



    public interface ClickListener{
        void onClickMoreInfo(Bitmap bitmap);
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return mBusList.size();
    }

    /*view holder*/
    class BusitemViewHolder extends RecyclerView.ViewHolder {
        TicketItemBinding binding;
        BusitemViewHolder(TicketItemBinding itemView) {
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



}