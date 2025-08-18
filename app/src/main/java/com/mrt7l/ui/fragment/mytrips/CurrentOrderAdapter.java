package com.mrt7l.ui.fragment.mytrips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mrt7l.R;
import com.mrt7l.databinding.ItemBookingBinding;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.BusitemViewHolder> {
    /*variable declaration*/
     private ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean> mBusList;
    private final ClickListener clickListener;
    private final String whats;
    private final Context context;
    /*constructor*/

    public CurrentOrderAdapter(ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean> aBusList,
                               ClickListener listener,String whatsapp,Context mContext) {
        /* initialize parameter*/
         this.mBusList = aBusList;
        this.clickListener  = listener;
        this.whats = whatsapp;
        this.context = mContext;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public CurrentOrderAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingBinding bookingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_booking,parent,false);
        return new CurrentOrderAdapter.BusitemViewHolder(bookingBinding);
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CurrentOrderAdapter.BusitemViewHolder holder1, final int position) {

        final CurrentOrdersResponse.Mrt7alBean.DataBean mBusModel = mBusList.get(position);
        if(mBusModel.isSelected()){
            holder1.binding.AllDataLayout.setVisibility(View.VISIBLE);
        } else {
            holder1.binding.AllDataLayout.setVisibility(View.GONE);
        }
        holder1.binding.whatsappLogo.setOnClickListener(v -> {
            clickListener.onWhatsapp(whats);
        });
        if (mBusModel.getTrip_date() != null) {
            if (mBusModel.getTrip_date().getCompany() != null) {
                Picasso.get().load("https://administrator.mrt7al.com/" +
                        mBusModel.getTrip_date().getCompany().getLogo_pic())
                        .into(holder1.binding.companyImage);
                holder1.binding.companyName.setText(mBusModel.getTrip_date().getCompany().getName());
                holder1.binding.placeTitle.setText(mBusModel.getTrip_date().getCompany().getAddress());
                holder1.binding.accountTitle.setText(mBusModel.getTrip_date().getCompany().getBank_account());
            }
            holder1.binding.payByVisa.setOnClickListener(v -> {
                Log.d("TAG", "onBindViewHolder: "+mBusModel.getRes_passangers().get(0).getReservation_id());
                clickListener.onPayByVisaClicked(String.valueOf(mBusModel.getRes_passangers().get(0).getReservation_id()));
            });
            holder1.binding.tvStartTime.setText(formatTime(mBusModel.getTrip_date().getDatetime_to()));
            holder1.binding.tvEndTime.setText(formatTime(mBusModel.getTrip_date().getDatetime_from()));
            holder1.binding.tvTotalDuration.setText(String.valueOf(mBusModel.getTrip_date().getWaitingTime()));
            holder1.binding.tvStartTimeAA.setText(mBusModel.getTrip_date().getFrom_city().getName());
            holder1.binding.tvEndTimeAA.setText(mBusModel.getTrip_date().getTo_city().getName());
            holder1.binding.type.setText(mBusModel.getTrip_date().getBus_type());
            holder1.binding.tripDate.setText(formatDate(mBusModel.getTrip_date().getDatetime_from()));
            if (mBusModel.getStatus().equals("pending")){
                holder1.binding.tripPending.setVisibility(View.VISIBLE);
                holder1.binding.tripConfirmed.setVisibility(View.GONE);
                holder1.binding.tripCanceled.setVisibility(View.GONE);
                holder1.binding.cancel.setVisibility(View.VISIBLE);
                holder1.binding.editTrip.setVisibility(View.VISIBLE);
                holder1.binding.tripPending.setText(context.getString(R.string.pending));
                holder1.binding.printTicket.setVisibility(View.GONE);
                if (mBusModel.getPayMethod() != null) {
                    if (mBusModel.getPayMethod().equals("tap")) {
                        holder1.binding.payByVisa.setVisibility(View.VISIBLE);
                    }
                }
            } else if (mBusModel.getStatus().equals("canceled")) {
                holder1.binding.tripPending.setVisibility(View.GONE);
                holder1.binding.tripConfirmed.setVisibility(View.GONE);
                holder1.binding.tripCanceled.setVisibility(View.VISIBLE);
                holder1.binding.tripCanceled.setText(context.getString(R.string.canceled));
                holder1.binding.cancel.setVisibility(View.GONE);
                holder1.binding.editTrip.setVisibility(View.GONE);
                holder1.binding.printTicket.setVisibility(View.GONE);
                holder1.binding.payByVisa.setVisibility(View.GONE);
            } else {
                holder1.binding.tripPending.setVisibility(View.GONE);
                holder1.binding.tripConfirmed.setVisibility(View.VISIBLE);
                holder1.binding.tripCanceled.setVisibility(View.GONE);
                holder1.binding.tripConfirmed.setText(context.getString(R.string.confirmed));
                holder1.binding.printTicket.setVisibility(View.VISIBLE);
                holder1.binding.cancel.setVisibility(View.GONE);
                holder1.binding.editTrip.setVisibility(View.GONE);
                holder1.binding.payByVisa.setVisibility(View.GONE);
            }

            holder1.binding.detailsText.setOnClickListener(v -> {
                mBusModel.setSelected(!mBusModel.isSelected());
                        notifyItemChanged(position);
                        clickListener.onDetailsOpened(position);

            });
            holder1.binding.price.setText( mBusModel.getTrip_date().getPrice() +" ريال  ");
            holder1.binding.tripNum.setText(mBusModel.getTripID());
            holder1.binding.startStation.setText(mBusModel.getTrip_date().getStation().getName());
         }

        if (mBusModel.getPayMethod() != null) {
            holder1.binding.payStatus.setText(mBusModel.getPayMethod());
            if (mBusModel.getPayMethod().equals("transfer")) {
                if (mBusModel.getReceipt_file() != null) {
                    holder1.binding.billPhoto.setVisibility(View.VISIBLE);
                    Glide.with(context).load("https://administrator.mrt7al.com/" + mBusModel.getReceipt_file())
                            .into(holder1.binding.billPhoto);
                } else {
                    holder1.binding.billPhoto.setVisibility(View.GONE);
                    holder1.binding.billImageTitle.setVisibility(View.GONE);
                }
            } else {
                holder1.binding.billPhoto.setVisibility(View.GONE);
                holder1.binding.billImageTitle.setVisibility(View.GONE);
            }
        } else {
            holder1.binding.billPhoto.setVisibility(View.GONE);
            holder1.binding.billImageTitle.setVisibility(View.GONE);
        }
        holder1.binding.ticketNum.setText(String.valueOf(mBusModel.getTicket_number()));
//        if (mBusModel.getMainPassanger().equals("ON")) {
//                 holder1.binding.reservationNumber.setText(String.valueOf(mBusModel.getId()));
//                holder1.binding.name.setText(mBusModel.getMain_Passanger().getFirstName() + " " +
//                        mBusModel.getMain_Passanger().getSecondName());
//                holder1.binding.passportNumber.setText(String.valueOf(mBusModel.getMain_Passanger()
//                        .getPassportId()));
//        } else {
             if (mBusModel.getRes_passangers().size() > 0) {
                 if (mBusModel.getRes_passangers().size() > 1){
                    holder1.binding.dataLayout.setVisibility(View.GONE);
                    holder1.binding.passengersRecyclerView.setVisibility(View.VISIBLE);
                    holder1.binding.passengersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                     if (mBusModel.getRes_passangers().size() > 0){
                         ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean>
                                 subPassangerBeans = new ArrayList<>();
                         subPassangerBeans.add(new CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean());

                         for (int i=0;i<mBusModel.getRes_passangers().size();i++){
                             if (mBusModel.getRes_passangers().get(i).getSub_passanger() != null){
                                 subPassangerBeans.add(mBusModel.getRes_passangers().get(i).getSub_passanger() );
                             }
                         }
                         com.mrt7l.ui.fragment.mytrips.PassengersAdapter passengersAdapter = new PassengersAdapter(subPassangerBeans,context);
                         holder1.binding.passengersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                         holder1.binding.passengersRecyclerView.setAdapter(passengersAdapter);
                         holder1.binding.price.setText(String.valueOf(mBusModel.getTotal_price()))  ;
                     }
                 } else {
                     holder1.binding.reservationNumber.setText(String.valueOf(mBusModel.getRes_passangers().get(0).getReservation_id()));
                     if (mBusModel.getRes_passangers().get(0).getSub_passanger() != null) {
                         if (mBusModel.getRes_passangers().get(0).getSub_passanger().getPassangerType()
                         .equals("child")){
                             holder1.binding.passengerType.setText(context.getString(R.string.child));
                         } else if (mBusModel.getRes_passangers().get(0).getSub_passanger().getPassangerType()
                                 .equals("adult")){
                             holder1.binding.passengerType.setText(context.getString(R.string.adult));
                         } else {
                             holder1.binding.passengerType.setText(context.getString(R.string.baby));
                         }
                         holder1.binding.name.setText(mBusModel.getRes_passangers().get(0).getSub_passanger().getFull_name());
                         holder1.binding.passportNumber.setText(String.valueOf(mBusModel.getRes_passangers().get(0).getSub_passanger().getPassport_id()));
                     }
                     holder1.binding.dataLayout.setVisibility(View.VISIBLE);
                     holder1.binding.passengersRecyclerView.setVisibility(View.GONE);
                 }
             }
//        }
//        holder1.binding.sta.setText(mBusModel.getStatus());
         holder1.binding.printTicket.setOnClickListener(view -> {
            clickListener.onPrint(mBusModel,position);
        });
        holder1.binding.editTrip.setOnClickListener(view -> {
            clickListener.onEdit(mBusModel,position);
        });
        holder1.binding.cancel.setOnClickListener(view -> {
            clickListener.onCancel(mBusModel,position);
        });
    }

    public interface ClickListener {
        void onPrint(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean,int pos);
        void onEdit(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean,int pos);
        void onCancel(CurrentOrdersResponse.Mrt7alBean.DataBean datesBean,int pos);
        void onWhatsapp(String phone);
        void onDetailsOpened(int position);
        void onPayByVisaClicked(String reservationId);

    }

    /*item count*/
    @Override
    public int getItemCount() {
        return mBusList.size();
    }

    /*view holder*/
    class BusitemViewHolder extends RecyclerView.ViewHolder {
        ItemBookingBinding binding;
        BusitemViewHolder(ItemBookingBinding itemView) {
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


//             if (elapsedHours >= 1) {
                double sss = ((float) elapsedMinutes)/60;
                int ss= Integer.parseInt(String.format(Locale.ENGLISH,"%2.00f",elapsedHours +sss).trim());
                    currentdate = String.valueOf(ss);
//                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch blockf
            //e.printStackTrace();
        }
        return currentdate;

    }

}