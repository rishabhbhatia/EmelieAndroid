package com.satiate.emelie.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.ui.fragments.JourneyFragment;
import com.satiate.emelie.ui.fragments.PhotosFragment;

import java.util.ArrayList;
import java.util.List;

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

        vpCardDetails = (ViewPager) findViewById(R.id.vp_card_details);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new JourneyFragment(),"JOURNEY");
        adapter.addFragment(new PhotosFragment(),"PHOTOS");

        vpCardDetails.setAdapter(adapter);
        tlCardDetails.addOnTabSelectedListener(this);

        tlCardDetails.setupWithViewPager(vpCardDetails);
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
