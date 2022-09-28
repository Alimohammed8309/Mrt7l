package com.mrt7l.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.databinding.ItemBookingBinding;
import com.mrt7l.model.BookingModel;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    /*variable declaration*/
    private Context mCtx;
    private List<BookingModel> mBookList;

    /*constructor*/
    public BookingAdapter(Context aCtx, List<BookingModel> aBookList) {
        /* initialize parameter*/
        this.mCtx = aCtx;
        this.mBookList = aBookList;

    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingBinding bookingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.item_booking,parent,false);
        return new BookingViewHolder(bookingBinding);
    }

    /*bind viewholder*/
    @Override
    public void onBindViewHolder(@NonNull final BookingViewHolder holder1, int position) {
        final BookingModel mBookingModel = mBookList.get(position);


    }

    /*item count*/
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    /*view holder*/
    class BookingViewHolder extends RecyclerView.ViewHolder {
        ItemBookingBinding bookingBinding;
        BookingViewHolder(ItemBookingBinding itemView) {
            super(itemView.getRoot());
            bookingBinding = itemView;
        }
    }
}
