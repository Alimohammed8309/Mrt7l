package com.mrt7l.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mrt7l.BaseActivity;
import com.mrt7l.R;
import com.mrt7l.model.NewOfferModel;
import com.mrt7l.ui.adapter.ViewOfferAdapter;
import com.mrt7l.utils.Constants;

public class OffersActivity extends BaseActivity implements View.OnClickListener {
    /*variable declaration*/
    private RecyclerView mRvOffer;
    private ArrayList<NewOfferModel> mOfferList;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        initLayouts();
        initializeListeners();

    }

    /* initialize listener */
    private void initializeListeners() {
        mIvBack.setOnClickListener(this);
        mOfferList = new ArrayList<>();
//        mOfferList = (ArrayList<NewOfferModel>)getIntent().getSerializableExtra(Constants.intentdata.OFFER);

        mRvOffer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRvOffer.setAdapter(new ViewOfferAdapter(OffersActivity.this, mOfferList));
        RunLayoutAnimation(mRvOffer);
    }

    /* init layout */
    private void initLayouts() {
        mIvBack = findViewById(R.id.ivBack);
        mRvOffer = findViewById(R.id.rvOffer);
    }

    /* onClick listener */
    @Override
    public void onClick(View v) {
        if (v == mIvBack) onBackPressed();
    }
}
