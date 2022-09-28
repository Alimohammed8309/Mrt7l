package com.mrt7l.ui.fragment.explain_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt7l.databinding.FavouriteItemBinding;
import com.mrt7l.databinding.VideoRowBinding;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.SpecialViewHodler> {

    private Context context;
    private ClickListener listener;
     private ArrayList<VideosResponse.Mrt7alBean.DataBean> list;
     public VideosAdapter(Context context, ArrayList<VideosResponse.Mrt7alBean.DataBean> arrayList
     , ClickListener clickListener){
        this.context = context ;
        list = arrayList;
        listener = clickListener;
     }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoRowBinding binding =VideoRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new SpecialViewHodler(binding);
    }
    public interface ClickListener{
         void  onClick(String url);
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
        holder.binding.title.setText(list.get(position).getTitle());
        holder.binding.description.setText(list.get(position).getDescription());

        holder.itemView.setOnClickListener(v -> {
            listener.onClick(list.get(position).getUrl());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class  SpecialViewHodler  extends RecyclerView.ViewHolder {
        VideoRowBinding binding;
        public SpecialViewHodler(@NonNull VideoRowBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
