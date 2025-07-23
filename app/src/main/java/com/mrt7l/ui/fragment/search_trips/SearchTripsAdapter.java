//package com.mrt7l.ui.fragment.search_trips;
//
//import android.annotation.SuppressLint;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mrt7l.R;
//import com.mrt7l.ui.fragment.company_details.CompanyDetailsResponse;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//
//public class SearchTripsAdapter extends RecyclerView.Adapter<SearchTripsAdapter.BusitemViewHolder> {
//    /*variable declaration*/
//     private ArrayList<SearchTripsResponse.Mrt7alBean.DataBean> mBusList;
//    private ClickListener clickListener;
//
//    /*constructor*/
//
//    public SearchTripsAdapter(ArrayList<SearchTripsResponse.Mrt7alBean.DataBean> aBusList, ClickListener listener) {
//        /* initialize parameter*/
//         this.mBusList = aBusList;
//        this.clickListener  = listener;
//    }
//
//    /*  inflate layout */
//    @SuppressLint("InflateParams")
//    @NonNull
//    @Override
//    public SearchTripsAdapter.BusitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new SearchTripsAdapter.BusitemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_available_bus, parent,false));
//    }
//
//    /*bind viewholder*/
//    @SuppressLint("DefaultLocale")
//    @Override
//    public void onBindViewHolder(@NonNull SearchTripsAdapter.BusitemViewHolder holder1, final int position) {
//
//        final CompanyDetailsResponse.Mrt7alBean.DataBean.TripDatesBean mBusModel = mBusList.get(position);
//        holder1.mTvTravellerName.setText(mBusModel.getc().getName());
//        holder1.mTvStartTime.setText(formatTime(mBusModel.getDatetime_from()));
//        holder1.mTvTypeCoach.setText(formatDate(mBusModel.getDatetime_from()));
//        holder1.mTvEndTime.setText(formatTime(mBusModel.getDatetime_to()));
//        holder1.mTvTotalDuration.setText(getCurrentTime(mBusModel.getDatetime_from(),mBusModel.getDatetime_to()));
//        holder1.mTvStartTimeAA.setText(mBusModel.getFrom_city().getName());
//        holder1.mTvEndTimeAA.setText(mBusModel.getTo_city().getName());
////        holder1.mTvHold.setText(String.format(mContext.getString(R.string.text_addhold), mBusModel.get()));
//        holder1.mTvPrice.setText(String.format("ريال %s", mBusModel.getPrice()));
////        holder1.mTvRatingBar.setText(String.format(mContext.getString(R.string.text_add5), mBusModel.getRate()));
//        holder1.type.setText(mBusModel.getTripType());
//        holder1.mRelativeLayout.setOnClickListener(v -> {
//
//
//        });
//        holder1.reserve.setOnClickListener(view -> {
//            clickListener.onReservation(mBusModel);
//        });
//    }
//    public interface ClickListener{
//        void onReservation(CompanyDetailsResponse.Mrt7alBean.DataBean.TripDatesBean datesBean);
//    }
//    /*item count*/
//    @Override
//    public int getItemCount() {
//        return mBusList.size();
//    }
//
//    /*view holder*/
//    class BusitemViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView mTvTravellerName,reserve, mTvStartTime,type, mTvEndTime, mTvTotalDuration, mTvHold, mTvPrice, mTvTypeCoach, mTvRatingBar,mTvStartTimeAA,mTvEndTimeAA;
//        private ConstraintLayout mRelativeLayout;
//
//        BusitemViewHolder(View itemView) {
//            super(itemView);
//            mTvTravellerName = itemView.findViewById(R.id.tvTravellerName);
//            mTvStartTime = itemView.findViewById(R.id.tvStartTime);
//            mTvEndTime = itemView.findViewById(R.id.tvEndTime);
//            mTvTotalDuration = itemView.findViewById(R.id.tvTotalDuration);
//            mTvHold = itemView.findViewById(R.id.tvHold);
//            mTvRatingBar = itemView.findViewById(R.id.tvRatingbar);
//            mTvPrice = itemView.findViewById(R.id.tvPrice);
//            mTvTypeCoach = itemView.findViewById(R.id.tvTypeCoach);
//            mRelativeLayout = itemView.findViewById(R.id.rlMain);
//            mTvStartTimeAA = itemView.findViewById(R.id.tvStartTimeAA);
//            mTvEndTimeAA = itemView.findViewById(R.id.tvEndTimeAA);
//            type = itemView.findViewById(R.id.type);
//            reserve = itemView.findViewById(R.id.reserve);
//        }
//
//    }
//
//    public String formatDate(String date){
//        String theDate = "";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH);
//
//        try {
//            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
//            assert datse != null;
//            theDate = formats.format(datse);
//        } catch (ParseException e) {
//            //e.printStackTrace();
//        }
//
//return theDate;
//    }
//    public String formatTime(String date){
//        String theDate = "";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        SimpleDateFormat formats = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH);
//
//        try {
//            Date datse = format.parse(date.replace("T" , " ").replace("+03:00","") );
//            assert datse != null;
//            theDate = formats.format(datse);
//        } catch (ParseException e) {
//            //e.printStackTrace();
//        }
//
//        return theDate;
//    }
//
//    public String getCurrentTime(String adDate,String toDate) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        String currentdate = "";
//
//        //  long different = endDate.getTime() - startDate.getTime();
//        try{
//            if (adDate != null) {
//                Date date = format.parse(adDate.replace("T" , " ").replace("+03:00",""));
//                Date tdate = format.parse(toDate.replace("T" , " ").replace("+03:00",""));
//
//                assert date != null;
//
//
//                long secondsInMilli = 1000;
//                long minutesInMilli = secondsInMilli * 60;
//                long hoursInMilli = minutesInMilli * 60;
//                long daysInMilli = hoursInMilli * 24;
//                long monthesInMilli = daysInMilli * 30;
//                long yearInMilli = monthesInMilli * 12;
//                long different = tdate.getTime() - date.getTime();
//
//                long elapsedyears = different / yearInMilli;
//                different = different % yearInMilli;
//
//                long elapsedmonths = different / monthesInMilli;
//                different = different % monthesInMilli;
//
//                long elapsedDays = different / daysInMilli;
//                different = different % daysInMilli;
//
//                long elapsedHours = different / hoursInMilli;
//                different = different % hoursInMilli;
//
//                long elapsedMinutes = different / minutesInMilli;
//                different = different % minutesInMilli;
//
//                long elapsedSeconds = different / secondsInMilli;
//
//
//             if (elapsedHours >= 1) {
//                    currentdate = String.valueOf(Integer.parseInt(String.valueOf(elapsedHours)));
//                }
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch blockf
//            //e.printStackTrace();
//        }
//        return currentdate;
//
//    }
//
//}