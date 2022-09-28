package com.mrt7l.ui.fragment.mytrips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.R;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;

import java.util.ArrayList;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.BusitemViewHolder> {
    /*variable declaration*/
       ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean> passengersList;

    /*constructor*/
    Context mContext;
    public PassengersAdapter(ArrayList<CurrentOrdersResponse.Mrt7alBean.DataBean.ResPassangersBean.SubPassangerBean>  aBusList,
                             Context context) {
        /* initialize parameter*/
         this.passengersList = aBusList;
        mContext = context;
     }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusitemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.reservation_passenger_row, parent,false));
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull BusitemViewHolder holder1, final int position) {
        if (position != 0) {
        holder1.name.setText(passengersList.get(position).getFull_name());
        holder1.passportNumber.setText(passengersList.get(position).getPassport_id());
        holder1.passengerType.setText(passengersList.get(position).getPassangerType());
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