package com.mrt7l.ui.fragment.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.databinding.FavouriteItemBinding;
import com.mrt7l.databinding.ItemNotificationBinding;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.SpecialViewHodler> {

    private Context context;
         private ArrayList<NotificationsResponse.Mrt7alBean.DataBean> list;
     public NotificationsAdapter(Context context, ArrayList<NotificationsResponse.Mrt7alBean.DataBean> arrayList){
        this.context = context ;
        list = arrayList;
      }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding =ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new SpecialViewHodler(binding);
    }
    public interface ClickListener{
        void onDelete(MyFavouriteResponse.Mrt7alBean.DataBean datesBean, int pos);
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
        holder.binding.title.setText(list.get(position).getTitle());
        holder.binding.body.setText(list.get(position).getNoty_body());
        holder.binding.date.setText(list.get(position).getCreated()
                .replace("T"," ").replace("+03:00"," "));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class  SpecialViewHodler  extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;
        public SpecialViewHodler(@NonNull ItemNotificationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
