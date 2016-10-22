package com.satiate.emelie.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.satiate.emelie.R;
import com.satiate.emelie.adapters.PhotosDetailsHorizontalPhotoAdapter;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.utils.EmelieUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 20/10/16.
 */

public class HomeCardDetailsActivity extends EmelieActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.rv_details_horizontal_photo)
    RecyclerView rvDetailsHorizontalPhoto;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_details)
    RecyclerView rvDetails;

    /*@BindView(R.id.vp_card_details)
    ViewPager vpCardDetails;
    @BindView(R.id.tl_card_details)
    TabLayout tlCardDetails;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;*/

    private String[] mRecyclerViewValues =
            new String[]{"Apple Pie", "Banana Bread", "Cupcake", "Donut", "Eclair", "Froyo",
                    "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat",
                    "Lollipop", "M preview"};
    private String[] mRecommendedFoodItems = new String[]{"Chicken Biryani",     "Mutton Biryani", "Veg Biryani","Veg Fried Rice"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail_horizontal_coordinator_design);
        ButterKnife.bind(this);

        adjustAppBarSize();

       /* vpCardDetails = (ViewPager) findViewById(R.id.vp_card_details);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new JourneyFragment(),"JOURNEY");
        adapter.addFragment(new PhotosFragment(),"PHOTOS");

        vpCardDetails.setAdapter(adapter);
        tlCardDetails.addOnTabSelectedListener(this);

        tlCardDetails.setupWithViewPager(vpCardDetails);*/

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager recommendedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        rvDetails.setLayoutManager(mLayoutManager);
        rvDetailsHorizontalPhoto.setLayoutManager(recommendedLayoutManager);

        PhotosDetailsHorizontalPhotoAdapter photosDetailsHorizontalPhotoAdapter = new PhotosDetailsHorizontalPhotoAdapter(
                HomeCardDetailsActivity.this, EmelieUtilities.generateRandomStory().getPhotos());
        rvDetailsHorizontalPhoto.setAdapter(photosDetailsHorizontalPhotoAdapter);
    }

    private void adjustAppBarSize() {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
        int pixels = EmelieUtilities.getScreenPixels(HomeCardDetailsActivity.this, EmelieUtilities.getScreenHeight(HomeCardDetailsActivity.this));
        lp.height = pixels/7;
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
