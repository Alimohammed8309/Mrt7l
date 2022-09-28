package com.mrt7l.ui.fragment.choose_car;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mrt7l.R;
import com.mrt7l.databinding.HomeItemBinding;
import com.mrt7l.ui.fragment.search_trips.SearchTripsResponse;
import java.util.ArrayList;
import java.util.List;

public class ChooseCarAdapter  extends RecyclerView.Adapter<ChooseCarAdapter.BusitemViewHolder> {
    /*variable declaration*/
    private final Activity mContext;
    private List<SearchTripsResponse.Mrt7alBean.DataBean> mRecentSearchList = new ArrayList<>();
    private List<SearchTripsResponse.Mrt7alBean.DataBean> allDataList = new ArrayList<>();
    private final ChooseCarAdapter.ClickListener clickListener;

    /*constructor*/

    public ChooseCarAdapter(Activity aCtx, ArrayList<SearchTripsResponse.Mrt7alBean.DataBean>
            aBusList, ChooseCarAdapter.ClickListener listener) {
        /* initialize parameter*/
        this.mContext = aCtx;
        this.mRecentSearchList=aBusList;
        this.allDataList.clear();
        this.allDataList.addAll(aBusList);
        this.clickListener  = listener;
    }

    /*  inflate layout */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ChooseCarAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChooseCarAdapter.BusitemViewHolder viewHolder;
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.home_item, parent,false);
        viewHolder = new ChooseCarAdapter.BusitemViewHolder(binding);
        return viewHolder;
    }

    /*bind viewholder*/
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ChooseCarAdapter.BusitemViewHolder holder , final int position) {


    }
    public interface ClickListener{
        void onReserve(SearchTripsResponse.Mrt7alBean.DataBean dataBean,int pos);
    }
    /*item count*/
    @Override
    public int getItemCount() {
        return mRecentSearchList.size();
    }
    static class BusitemViewHolder extends RecyclerView.ViewHolder {
        public HomeItemBinding binding;
        BusitemViewHolder(HomeItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

}
