package com.mrt7l.ui.fragment.reservation.addpassengers;

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

public class PassengersPriceAdapter extends RecyclerView.Adapter<PassengersPriceAdapter.BusitemViewHolder> {
    /*variable declaration*/
       ArrayList<PassengerPriceModel> passengersList;
    Context mContext;
    /*constructor*/

    public PassengersPriceAdapter(ArrayList<PassengerPriceModel>  aBusList, Context context) {
        /* initialize parameter*/
         this.passengersList = aBusList;
        mContext = context;
     }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public PassengersPriceAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PassengersPriceAdapter.BusitemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.passenger_price_row, parent,false));
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PassengersPriceAdapter.BusitemViewHolder holder1, final int position) {
         holder1.type.setText(passengersList.get(position).getType());
        holder1.count.setText(passengersList.get(position).getCount());
        if (position != 0) {
            if (Integer.parseInt(passengersList.get(position).getCount()) != 0) {
                holder1.price.setText(passengersList.get(position).getPrice());
            } else {
                holder1.price.setText("0");
            }
        }
        if (position == 0){
            holder1.price.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder1.count.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder1.type.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            holder1.count.setTextColor(ContextCompat.getColor(mContext, R.color.colorlink_blue));
            holder1.type.setTextColor(ContextCompat.getColor(mContext, R.color.colorlink_blue));
            holder1.price.setTextColor(ContextCompat.getColor(mContext, R.color.colorlink_blue));
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

        private TextView type,count,price ;

        BusitemViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.passengerName);
            count = itemView.findViewById(R.id.passportNumber);
            price = itemView.findViewById(R.id.passengerType);
        }
    }


}