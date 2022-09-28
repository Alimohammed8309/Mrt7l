package com.mrt7l.ui.fragment.favourite;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mrt7l.databinding.FavouriteItemBinding;
import com.mrt7l.databinding.ImageItemBinding;
import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.SpecialViewHodler> {

    private Context context;
    private ClickListener listener;
     private ArrayList<MyFavouriteResponse.Mrt7alBean.DataBean> list;
     private CompanyDetailsResponse companyDetailsResponse;
     public FavouriteAdapter(Context context,ArrayList<MyFavouriteResponse.Mrt7alBean.DataBean> arrayList
     ,ClickListener clickListener){
        this.context = context ;
        list = arrayList;
        listener = clickListener;
         companyDetailsResponse = CompanyDetailsResponse.getInstance();
     }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavouriteItemBinding binding =FavouriteItemBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new SpecialViewHodler(binding);
    }
    public interface ClickListener{
        void onDelete(MyFavouriteResponse.Mrt7alBean.DataBean datesBean,int pos);
        void  onClick(MyFavouriteResponse.Mrt7alBean.DataBean datesBean);
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
         if (list.get(position).getCompany() != null) {
             holder.binding.title.setText(list.get(position).getCompany().getName());
             Picasso.get().load("https://administrator.mrt7al.com/" + list.get(position).getCompany()
                     .getLogo_pic()).into(holder.binding.logo);
             holder.binding.content.setText(list.get(position).getCompany().getCompany_info());
             holder.binding.ratingBar.setRating(4.5f);
         }
        holder.itemView.setOnClickListener(v -> {
            companyDetailsResponse.setMrt7al(null);
            listener.onClick(list.get(position));
        });
        holder.binding.heart.setOnClickListener(view -> {
            listener.onDelete(list.get(position),position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class  SpecialViewHodler  extends RecyclerView.ViewHolder {
        FavouriteItemBinding binding;
        public SpecialViewHodler(@NonNull FavouriteItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
