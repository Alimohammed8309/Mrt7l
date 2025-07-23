package com.mrt7l.ui.fragment.mytrips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
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

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.BusitemViewHolder> {
    /*variable declaration*/
     private ArrayList<PastOrdersResponse.Mrt7alBean.DataBean> mBusList;
    private Context context;
    /*constructor*/
    private RateListener rateListener;
    public PastOrderAdapter(ArrayList<PastOrdersResponse.Mrt7alBean.DataBean>
                                    aBusList,Context mContext,RateListener listener) {
        /* initialize parameter*/
         this.mBusList = aBusList;
         this.context = mContext;
         this.rateListener = listener;
     }

     public interface RateListener {
        void onRateClicked(PastOrdersResponse.Mrt7alBean.DataBean bean,int position);
         void onPastDetailsOpened(int position);

     }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public PastOrderAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingBinding bookingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_booking,parent,false);
        return new PastOrderAdapter.BusitemViewHolder(bookingBinding);
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PastOrderAdapter.BusitemViewHolder holder1, final int position) {
        holder1.binding.buttonsLayout.setVisibility(View.GONE);
        holder1.binding.payMehtodLayout.setVisibility(View.GONE);
        holder1.binding.contactLayout.setVisibility(View.GONE);
        holder1.binding.note.setVisibility(View.GONE);
        holder1.binding.whatsappLogo.setVisibility(View.GONE);
        final PastOrdersResponse.Mrt7alBean.DataBean mBusModel = mBusList.get(position);
        if(mBusModel.isSelected()){
            holder1.binding.AllDataLayout.setVisibility(View.VISIBLE);
        } else {
            holder1.binding.AllDataLayout.setVisibility(View.GONE);
        }
        if (mBusModel.getTrip_date() != null) {
            if (mBusModel.getTrip_date().getCompany() != null) {
                Picasso.get().load("https://administrator.mrt7al.com/" +
                        mBusModel.getTrip_date().getCompany().getLogo_pic())
                        .into(holder1.binding.companyImage);
                holder1.binding.companyName.setText(mBusModel.getTrip_date().getCompany().getName());
                holder1.binding.placeTitle.setText(mBusModel.getTrip_date().getCompany().getAddress());
            }
            holder1.binding.account.setText(context.getString(R.string.pay_type));
            holder1.binding.accountTitle.setText(mBusModel.getPayMethod());
            holder1.binding.tvStartTime.setText(formatTime(mBusModel.getTrip_date().getDatetime_to()));
            holder1.binding.tvEndTime.setText(formatTime(mBusModel.getTrip_date().getDatetime_from()));
            holder1.binding.tvTotalDuration.setText(String.valueOf(mBusModel.getTrip_date().getWaitingTime()));
            holder1.binding.tvStartTimeAA.setText(mBusModel.getTrip_date().getFrom_city().getName());
            holder1.binding.tvEndTimeAA.setText(mBusModel.getTrip_date().getTo_city().getName());
            holder1.binding.type.setText(mBusModel.getTrip_date().getTripType());
            holder1.binding.tripDate.setText(formatDate(mBusModel.getTrip_date().getDatetime_from()));
            switch (mBusModel.getStatus()) {
                case "pending":
                    holder1.binding.tripPending.setVisibility(View.VISIBLE);
                    holder1.binding.tripConfirmed.setVisibility(View.GONE);
                    holder1.binding.tripCanceled.setVisibility(View.GONE);
                     holder1.binding.rateTrip.setVisibility(View.GONE);
                    holder1.binding.tripPending.setText(context.getString(R.string.pending));
                    break;
                case "canceled":
                    holder1.binding.tripPending.setVisibility(View.GONE);
                    holder1.binding.tripConfirmed.setVisibility(View.GONE);
                    holder1.binding.tripCanceled.setVisibility(View.VISIBLE);
                     holder1.binding.tripCanceled.setText(context.getString(R.string.canceled));
                    holder1.binding.rateTrip.setVisibility(View.GONE);
                    break;
                case "confirmed":
                    holder1.binding.tripPending.setVisibility(View.GONE);
                    holder1.binding.tripCanceled.setVisibility(View.GONE);
                    holder1.binding.tripConfirmed.setText(mBusModel.getStatus());
                     holder1.binding.tripCanceled.setVisibility(View.GONE);
                    holder1.binding.tripConfirmed.setText(context.getString(R.string.ended));
                     holder1.binding.tripConfirmed.setVisibility(View.VISIBLE);
                    if (mBusModel.getRates().size() == 0)
                    holder1.binding.rateTrip.setVisibility(View.VISIBLE);
                    break;

            }
            holder1.binding.rateTrip.setOnClickListener(v -> {
                rateListener.onRateClicked(mBusList.get(position),position);
            });
            holder1.binding.detailsText.setOnClickListener(v -> {
                mBusModel.setSelected(!mBusModel.isSelected());
                notifyItemChanged(position);
                rateListener.onPastDetailsOpened(position);
            });
            holder1.binding.price.setText( mBusModel.getTrip_date().getPrice() +" ريال  ");
            holder1.binding.tripNum.setText(mBusModel.getTripID());
            holder1.binding.startStation.setText(mBusModel.getTrip_date().getStation().getName());

//            holder1.binding.companyPlace.setText(mBusModel.getTrip_date().getCompany().getAddress());
//            holder1.binding.account.setText(mBusModel.getTrip_date().getCompany().getBank_account());
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
        if (mBusModel.getRes_passangers().size() > 0) {
            holder1.binding.reservationNumber.setText(String.valueOf(mBusModel.getRes_passangers().get(0).getReservation_id()));
            if (mBusModel.getRes_passangers().get(0).getSub_passanger() != null) {
                holder1.binding.passengerType.setText(mBusModel.getRes_passangers().get(0).getSub_passanger().getPassangerType());
                holder1.binding.name.setText(mBusModel.getRes_passangers().get(0).getSub_passanger().getFull_name());
                holder1.binding.passportNumber.setText(String.valueOf(mBusModel.getRes_passangers().get(0).getSub_passanger().getPassport_id()));
            }
        }
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
                    currentdate = String.valueOf(Integer.parseInt(String.valueOf(elapsedHours)));
//                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch blockf
            //e.printStackTrace();
        }
        return currentdate;

    }

}