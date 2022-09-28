package com.mrt7l.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.mrt7l.R;
import com.mrt7l.model.BookingModel;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    /*variable declaration*/
    private Context mContext;
    private List<BookingModel> mNotificationList;
    private onClickListener mListener;

    /*constructor*/
    public NotificationAdapter(Context aCtx, List<BookingModel> aNotificationList) {
        /* initialize parameter*/
        this.mContext = aCtx;
        this.mNotificationList = aNotificationList;
    }

    /*set onClick listener*/
    public void setOnClickListener(onClickListener mListener) {
        this.mListener = mListener;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_notification, null));
    }

    /*bind viewholder*/
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder1, int position) {
        final BookingModel mNotificationModel = mNotificationList.get(position);

        holder1.itemView.setOnClickListener(v -> {

            if (mListener != null) {
                notifyDataSetChanged();
                mListener.onClick(mNotificationModel);
            }
        });
    }

    /*  remove item */
    public void removeItem(int position) {
        mNotificationList.remove(position);
        notifyItemRemoved(position);
    }

    /*  restore item */
    public void restoreItem(BookingModel item, int position) {
        mNotificationList.add(position, item);
        notifyItemInserted(position);
    }

    /*  get data */
    public List<BookingModel> getData() {
        return mNotificationList;
    }

    /*item count*/
    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    /*onclick listener interface*/
    public interface onClickListener {

        void onClick(BookingModel notificationModel);
    }

    /*view holder*/
    class NotificationViewHolder extends RecyclerView.ViewHolder {


        NotificationViewHolder(View itemView) {
            super(itemView);

        }

    }
}
