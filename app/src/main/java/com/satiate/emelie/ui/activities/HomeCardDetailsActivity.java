package com.satiate.emelie.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 20/10/16.
 */

public class HomeCardDetailsActivity extends EmelieActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.vp_card_details)
    ViewPager vpCardDetails;
    @BindView(R.id.tl_card_details)
    TabLayout tlCardDetails;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_card_details);
        ButterKnife.bind(this);

        tlCardDetails.addTab(tlCardDetails.newTab().setText("JOURNEY"));
        tlCardDetails.addTab(tlCardDetails.newTab().setText("PHOTOS"));
        tlCardDetails.addTab(tlCardDetails.newTab().setText("COMMENTS"));

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
