package com.mrt7l.ui.fragment.reservation.addpassengers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.R;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.reservation.ReservationPostModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.BusitemViewHolder> {
    /*variable declaration*/
       ArrayList<AddPassengersFragment.DataBean> passengersList;

    /*constructor*/
    Context mContext;
    public PassengersAdapter(ArrayList<AddPassengersFragment.DataBean>  aBusList,
                             Context context) {
        /* initialize parameter*/
         this.passengersList = aBusList;
        mContext = context;
     }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public PassengersAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PassengersAdapter.BusitemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.reservation_passenger_row, parent,false));
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PassengersAdapter.BusitemViewHolder holder1, final int position) {
        if (position != 0) {
        holder1.name.setText(passengersList.get(position).getFull_name());
        holder1.passportNumber.setText(passengersList.get(position).getPassport_id());
            if (passengersList.get(position).getPassangerType()
                    .equals("child")){
                holder1. passengerType.setText(mContext.getString(R.string.child));
            } else if (passengersList.get(position).getPassangerType()
                    .equals("adult")){
                holder1. passengerType.setText(mContext.getString(R.string.adult));
            } else {
                holder1. passengerType.setText(mContext.getString(R.string.baby));
            }

        holder1.name.setTextColor(ContextCompat.getColor(mContext,R.color.colorlink_blue));
            holder1.passportNumber.setTextColor(ContextCompat.getColor(mContext,R.color.colorlink_blue));
            holder1.passengerType.setTextColor(ContextCompat.getColor(mContext,R.color.colorlink_blue));
        }
    }
    public interface ClickListener{
        void onReservation(CompanyDetailsResponse.Mrt7alBean.DataBean datesBean,int pos);
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return passengersList.size();
    }

    /*view holder*/
    class BusitemViewHolder extends RecyclerView.ViewHolder {

        private TextView name,passportNumber,passengerType ;

        BusitemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.passengerName);
            passportNumber = itemView.findViewById(R.id.passportNumber);
            passengerType = itemView.findViewById(R.id.passengerType);
        }
    }


}