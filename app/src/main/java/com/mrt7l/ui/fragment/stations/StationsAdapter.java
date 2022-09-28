package com.mrt7l.ui.fragment.stations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.databinding.FavouriteItemBinding;
import com.mrt7l.databinding.StationsRowBinding;
import com.mrt7l.helpers.BroadcastHelper;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.SpecialViewHodler> {

    private Context context;
    private ClickListener listener;
     private ArrayList<StationsResponse.Mrt7alBean.DataBean> list;
     public StationsAdapter(Context context, ArrayList<StationsResponse.Mrt7alBean.DataBean> arrayList
     , ClickListener clickListener){
        this.context = context ;
        list = arrayList;
        listener = clickListener;
     }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StationsRowBinding binding =StationsRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new SpecialViewHodler(binding);
    }
    public interface ClickListener{
        void OnClick();
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
        holder.binding.stationName.setText(list.get(position).getName());
        holder.binding.address.setText(list.get(position).getAddress());
        holder.binding.distance.setText(list.get(position).getDistance());
        holder.itemView.setOnClickListener(view -> {
            Intent in = new Intent();
            in.putExtra("lat",list.get(position).getLat_at());
            in.putExtra("lng",list.get(position).getLong_at());
            BroadcastHelper.sendInform(context,"changeLocation",in);
            listener.OnClick();
        });

        holder.binding.shareLayout.setOnClickListener(view -> {
            String uri = "http://maps.google.com/maps?q=" +list.get(position).getLat_at()+","+list.get(position).getLong_at();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ShareSub = list.get(position).getName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
            context.startActivity(Intent.createChooser(sharingIntent, "مشاركة "));
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class  SpecialViewHodler  extends RecyclerView.ViewHolder {
        StationsRowBinding binding;
        public SpecialViewHodler(@NonNull StationsRowBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
