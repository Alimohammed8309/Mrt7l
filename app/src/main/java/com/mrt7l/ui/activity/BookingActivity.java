package com.mrt7l.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.model.EdgeItem;
import com.mrt7l.model.EmptyItem;
import com.mrt7l.model.SeatModel;
import com.mrt7l.model.SeatType;
import com.mrt7l.ui.adapter.SeatAdapter;
import com.mrt7l.ui.adapter.SleeperAdapter;
import com.mrt7l.utils.Constants;
import com.mrt7l.utils.menu.AbstractItem;
import com.mrt7l.utils.menu.CenterItem;
import com.mrt7l.utils.menu.OnSeatSelected;

public class BookingActivity extends BaseActivity implements View.OnClickListener {

    public static int mCountSeat, mTotal;
    public static StringBuffer mSb;
    private int mSeatNo = 0;
    private int mSeatNoSleeper = 0;
    private RecyclerView mRvViewSeats;
    private ImageView mIvAvailable, mIcBook, mIcSelect, mIvLadies;
    private LinearLayout mLinear, mLlDack, mLlDynamic;
    private List<AbstractItem> mAbstractItemsList;
    private List<SeatModel> mSeatModelsItemsList;
    private Button mBtnBook;
    private TextView mTvLower, mTvUpper;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        initLayouts();
        initializeLayouts();
        initializeSeats();
    }

    /* initialize seats */
    private void initializeSeats() {
        if (Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.TYPECOACH)).contains(getString(R.string.text_sleeper))) {
            showView(mLlDack);
            mIvAvailable.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sleeper));
            mIcBook.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sleeper));
            mIcSelect.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sleeper));
            mIvLadies.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sleeper));
            mIvAvailable.setColorFilter(ContextCompat.getColor(this, R.color.view_color));
            mIcBook.setColorFilter(ContextCompat.getColor(this, R.color.dark_gray));
            mIcSelect.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvLadies.setColorFilter(ContextCompat.getColor(this, R.color.pink));

            int mSleeperColumns = 4;
            for (int i = 0; i < 28; i++) {
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                mSeatModelsItemsList.add(new SeatModel(SeatType.BOOKED));
                mSeatModelsItemsList.add(new SeatModel(SeatType.LADIES));
                if (i % mSleeperColumns == 0 || i % mSleeperColumns == 3) {
                    mSeatNoSleeper++;
                    mAbstractItemsList.add(new EdgeItem(String.valueOf(mSeatNoSleeper)));

                } else if (i % mSleeperColumns == 2) {
                    mSeatNoSleeper++;
                    mAbstractItemsList.add(new CenterItem(String.valueOf(mSeatNoSleeper)));
                } else {
                    mAbstractItemsList.add(new EmptyItem(mSeatModelsItemsList));
                }
            }

            GridLayoutManager mManager1 = new GridLayoutManager(this, mSleeperColumns);
            mRvViewSeats.setLayoutManager(mManager1);
            SleeperAdapter adapter = new SleeperAdapter((count, label) -> {
                if (count == 0) {
                    hideView(mLinear);
                } else {
                    mCountSeat = count;
                    showView(mLinear);
                    mSb.append(label).append(" ");
                    TextView mTvTotalPrice = findViewById(R.id.txtTicketPrice);
                    mTvTotalPrice.setText(String.format("%s%s", getString(R.string.rs), String.valueOf((Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.PRICE))) * count))));
                    mTotal = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.PRICE))) * count + 5;
                    ((TextView)findViewById(R.id.tvTotal)).setText(String.format("%s%s", getString(R.string.rs), String.valueOf((Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.PRICE))) * count + 5))));
                }
            }, mAbstractItemsList, this, mSeatModelsItemsList);
            mRvViewSeats.setAdapter(adapter);

        } else {

            mIvAvailable.setColorFilter(ContextCompat.getColor(this, R.color.view_color));
            mIcBook.setColorFilter(ContextCompat.getColor(this, R.color.dark_gray));
            mIcSelect.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            mIvLadies.setColorFilter(ContextCompat.getColor(this, R.color.pink));
            for (int i = 0; i < 40; i++) {
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                mSeatModelsItemsList.add(new SeatModel(SeatType.LADIES));
                mSeatModelsItemsList.add(new SeatModel(SeatType.BOOKED));
                mSeatModelsItemsList.add(new SeatModel(SeatType.EMPTY));
                int mCOLUMNS = 5;
                if (i % mCOLUMNS == 0 || i % mCOLUMNS == 4) {
                    mSeatNo++;
                    mAbstractItemsList.add(new EdgeItem(String.valueOf(mSeatNo)));
                } else if (i % mCOLUMNS == 1 || i % mCOLUMNS == 3) {
                    mSeatNo++;
                    mAbstractItemsList.add(new CenterItem(String.valueOf(mSeatNo)));
                } else {
                    mAbstractItemsList.add(new EmptyItem(mSeatModelsItemsList));
                }
                GridLayoutManager mManager = new GridLayoutManager(this, mCOLUMNS);
                mRvViewSeats.setLayoutManager(mManager);
                SeatAdapter adapter = new SeatAdapter((count, label) -> {
                    if (count == 0) {
                        hideView(mLinear);
                    } else {
                        mCountSeat = count;
                        showView(mLinear);
                        mSb.append(label).append(" ");
                        TextView mTvTotalPrice = findViewById(R.id.txtTicketPrice);
                        mTvTotalPrice.setText(String.format("%s%s", getString(R.string.rs), String.valueOf((Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.PRICE))) * count))));
                        mTotal = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.PRICE))) * count + 5;
                        ((TextView)findViewById(R.id.tvTotal)).setText(String.format("%s%s", getString(R.string.rs), String.valueOf((Integer.parseInt(getIntent().getStringExtra(Constants.intentdata.PRICE)) * count + 5))));
                    }
                }, mAbstractItemsList, this, mSeatModelsItemsList);
                mRvViewSeats.setAdapter(adapter);
            }
        }
        mSeatNo = 0;
        mSeatNoSleeper = 0;
    }

    /* initialize */
    private void initializeLayouts() {
        mBtnBook.setOnClickListener(this);
        mTvLower.setOnClickListener(this);
        mTvUpper.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mBtnBook.setStateListAnimator(null);


        int mCount = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra(Constants.intentdata.HOLD)));

        int i = 0;
        while (i < mCount) {
            i++;
            View view1 = getLayoutInflater().inflate(R.layout.layout_hold, mLlDynamic, false);
            final ImageView mIvHold = view1.findViewById(R.id.ivHold);
            mIvHold.setOnClickListener(v -> showToast(getString(R.string.text_city)));
            mLlDynamic.addView(view1);
        }
    }

    /* bind view ids */

    private void initLayouts() {
        mBtnBook = findViewById(R.id.btnBook);
        mLinear = findViewById(R.id.llOffer);
        mIvAvailable = findViewById(R.id.ivAvailable);
        mIvLadies = findViewById(R.id.icLadies);
        mIcBook = findViewById(R.id.icBook);
        mIcSelect = findViewById(R.id.icSelect);
        mAbstractItemsList = new ArrayList<>();
        mSeatModelsItemsList = new ArrayList<>();
        mSb = new StringBuffer();
        mRvViewSeats = findViewById(R.id.rvSeat);
        mLlDack = findViewById(R.id.lvDack);
        mTvLower = findViewById(R.id.tvLower);
        mTvUpper = findViewById(R.id.tvUpper);
        mLlDynamic = findViewById(R.id.llDynamicContent);
        mIvBack = findViewById(R.id.ivBack);
    }

    /* on click listener */
    @Override
    public void onClick(View v) {
        if (v == mBtnBook) startActivity(DroppingPointActivity.class);
        else if (v == mTvUpper) {
            mTvUpper.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_leftswitch_select));
            mTvUpper.setTextColor(getResources().getColor(R.color.white));
            mTvLower.setTextColor(getResources().getColor(R.color.textheader));
            mTvLower.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_rightswitch));
        } else if (v == mTvLower) {
            mTvLower.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_rightswitch_select));
            mTvLower.setTextColor(getResources().getColor(R.color.white));
            mTvUpper.setTextColor(getResources().getColor(R.color.textheader));
            mTvUpper.setBackground(ContextCompat.getDrawable(this,R.drawable.bg_leftswitch));
        } else if (v == mIvBack) onBackPressed();
    }
}
