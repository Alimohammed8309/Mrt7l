package com.mrt7l.ui.fragment.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mrt7l.databinding.QuestionRowBinding;
import com.mrt7l.ui.fragment.favourite.MyFavouriteResponse;

import java.util.ArrayList;


public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.SpecialViewHodler> {

    private Context context;
      private ArrayList<FaqResponse.Mrt7alBean.DataBean> list;
     public FaqAdapter(Context context, ArrayList<FaqResponse.Mrt7alBean.DataBean> arrayList
      ){
        this.context = context ;
        list = arrayList;
      }

    @NonNull
    @Override
    public SpecialViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionRowBinding binding =QuestionRowBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new SpecialViewHodler(binding);
    }
    public interface ClickListener{
        void onDelete(MyFavouriteResponse.Mrt7alBean.DataBean datesBean, int pos);
    }
    @Override
    public void onBindViewHolder(@NonNull SpecialViewHodler holder, int position) {
         if (list.get(position).isSelected()){
             holder.binding.answer.setVisibility(View.VISIBLE);
         } else {
             holder.binding.answer.setVisibility(View.GONE);
         }
        holder.binding.title.setText(list.get(position).getQuestion());
        holder.binding.answer.setText(list.get(position).getAnswer());
        holder.binding.number.setText(String.valueOf(position +1));
        holder.binding.title.setOnClickListener(view -> {
            list.get(position).setSelected(!list.get(position).isSelected());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class  SpecialViewHodler  extends RecyclerView.ViewHolder {
        QuestionRowBinding binding;
        public SpecialViewHodler(@NonNull QuestionRowBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
