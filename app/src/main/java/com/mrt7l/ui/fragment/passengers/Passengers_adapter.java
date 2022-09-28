package com.mrt7l.ui.fragment.passengers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.R;
import com.mrt7l.databinding.DeletePassengerDialogBinding;
import com.mrt7l.databinding.LoginerrorBinding;
import com.mrt7l.databinding.PassengerRowBinding;
import com.mrt7l.helpers.CircleTransform;
import com.mrt7l.ui.activity.SignInActivity;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class Passengers_adapter extends RecyclerView.Adapter<Passengers_adapter.PlacesViewHolder> {
    Activity anActivity;
    PassengersViewModel mViewmodel;
    private boolean isTrip =false;
    private PassengersResponse p;
    private WeakReference<HandlePassengersInterface> mNavigator;

    public HandlePassengersInterface getmNavigator() {
        return mNavigator.get();
    }

    public void setmNavigator(HandlePassengersInterface mNavigator) {
         this.mNavigator = new WeakReference<>(mNavigator);

    }

    private final ArrayList<PassengersResponse.Mrt7alBean.DataBean> list;
    public Passengers_adapter(Activity activity,
                              ArrayList<PassengersResponse.Mrt7alBean.DataBean> mlist,
                              HandlePassengersInterface passengersInterface,boolean isTripp) {
        anActivity = activity;
         isTrip = isTripp;
        p = PassengersResponse.getInstance();
        list =mlist;
        setmNavigator(passengersInterface);
     }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PassengerRowBinding view =PassengerRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new PlacesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() >0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        if (!isTrip){
            holder.rowBinding.correct.setVisibility(View.GONE);
        } else {
            holder.rowBinding.correct.setVisibility(View.VISIBLE);
        }
        holder.rowBinding.name.setText(list.get(position).getFull_name());
        holder.rowBinding.birthDay.setText(anActivity.getString(R.string.birthday)
                + " : " +list.get(position).getDate_of_birth());
        Picasso.get().load("https://administrator.mrt7al.com/" +list.get(position).getPassport_file())
                .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(anActivity, R.drawable.ic_profile))).transform(new CircleTransform()).
                into(holder.rowBinding.image);
        holder.rowBinding.phoneNumber.setText(anActivity.getString(R.string.text_phone)
                + " : " + list.get(position).getMobile());
        holder.rowBinding.passportNumber.setText(anActivity.getString(R.string.passport_num) +
                " : " + list.get(position).getPassport_id());
        holder.rowBinding.edit.setOnClickListener(view -> {
            getmNavigator().OnEdit(list.get(position));
        });
        holder.rowBinding.delete.setOnClickListener(view -> {
            showDeletePassengerDialog("هل تريد حذف هذا المسافر بالفعل",
                    anActivity,position,holder);
        });
        if (list.get(position).isSelected()){
            holder.rowBinding.correct.setBackgroundResource(R.drawable.correct_green);
        } else {
            holder.rowBinding.correct.setBackgroundResource(R.drawable.correct);
        }
        holder.rowBinding.correct.setOnClickListener(view -> {
            list.get(position).setSelected(!list.get(position).isSelected());
             getmNavigator().onPassengerChecked(list.get(position));
            notifyDataSetChanged();
        });
    }
    public void showDeletePassengerDialog(String message,
                                          Activity activity,
                                          int position,PlacesViewHolder holder) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        DeletePassengerDialogBinding registerErrorDialogBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.delete_passenger_dialog, null, false);
        registerErrorDialogBinding.content.setText(message);
        registerErrorDialogBinding.okButton.setOnClickListener(v -> {
            getmNavigator().OnDelete(list.get(position),position);

            dialog.cancel();
        });
        registerErrorDialogBinding.cancel.setOnClickListener(view -> dialog.cancel());
        dialog.setContentView(registerErrorDialogBinding.getRoot());
        dialog.show();
    }
    static class PlacesViewHolder extends RecyclerView.ViewHolder {
        PassengerRowBinding rowBinding;
        private PlacesViewHolder(@NonNull PassengerRowBinding itemView) {
            super(itemView.getRoot());
            rowBinding = itemView;
        }
     }
}
