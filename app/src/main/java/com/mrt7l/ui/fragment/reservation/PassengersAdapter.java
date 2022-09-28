//package com.mrt7l.ui.fragment.reservation;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mrt7l.R;
//import com.mrt7l.databinding.DirectReserveItemBinding;
//
//import java.util.ArrayList;
//
//public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.BusitemViewHolder> {
//    /*variable declaration*/
//    private Activity mContext;
//    private ArrayList<ReservationResponse.Mrt7alBean.DataBean.SubPassangersBean> mBusList;
//    private ClickListener clickListener;
//
//    /*constructor*/
//
//    public PassengersAdapter(Activity aCtx, ArrayList<ReservationResponse.Mrt7alBean
//            .DataBean.SubPassangersBean> aBusList ) {
//        /* initialize parameter*/
//        this.mContext = aCtx;
//        this.mBusList = aBusList;
////        this.clickListener  = listener;
//    }
//
//    /*  inflate layout */
//    @SuppressLint("InflateParams")
//    @NonNull
//    @Override
//    public PassengersAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        BusitemViewHolder viewHolder;
//        DirectReserveItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.direct_reserve_item,parent,false);
//        viewHolder = new BusitemViewHolder(binding);
//        return viewHolder;
//    }
//
//
//    @SuppressLint("DefaultLocale")
//    @Override
//    public void onBindViewHolder(@NonNull PassengersAdapter.BusitemViewHolder holder1, final int position) {
//        holder1.binding.firstName.setText(mBusList.get(position).getFullName());
//        if (mBusList.get(position).getGender().equals("ذكر")) {
//            holder1.binding.genderText.setText("ذكر");
//        } else {
//            holder1.binding.genderText.setText("أنثى");
//        }
//        holder1.binding.passportNumber.setText(mBusList.get(position).getPassportId());
//        holder1.binding.phoneNumber.setText(mBusList.get(position).getMobile());
//    }
//    public interface ClickListener{
//        void onClickMoreInfo(String profileId);
//    }
//    /*item count*/
//    @Override
//    public int getItemCount() {
//        return mBusList.size();
//    }
//
//    /*view holder*/
//    class BusitemViewHolder extends RecyclerView.ViewHolder {
//        DirectReserveItemBinding binding;
//        BusitemViewHolder(DirectReserveItemBinding itemView) {
//            super(itemView.getRoot());
//             binding = itemView;
//        }
//    }
//
//
//
//
//}