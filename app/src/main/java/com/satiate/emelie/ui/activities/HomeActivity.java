package com.satiate.emelie.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.satiate.emelie.R;
import com.satiate.emelie.adapters.HomePagerTransformer;
import com.satiate.emelie.events.DragToRemoveUser;
import com.satiate.emelie.models.User;
import com.satiate.emelie.ui.fragments.CommonFragment;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.EmelieUtilities;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {

    private TextView indicatorTv;
    private View positionView;
    private ViewPager viewPager;
    private HomePagerTransformer homePagerTransformer;
    private List<CommonFragment> fragments = new ArrayList<>();
    public static final String randomImageUrl = "https://unsplash.it/200/300/?random";

    private int USER_COUNT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EventBus.getDefault().register(HomeActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        positionView = findViewById(R.id.position_view);

        dealStatusBar();
        initImageLoader();
        fillViewPager();
    }

    private void fillViewPager()
    {
        indicatorTv = (TextView) findViewById(R.id.indicator_tv);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        homePagerTransformer = new HomePagerTransformer(this);
        viewPager.setPageTransformer(false, homePagerTransformer);


        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = new CommonFragment();
                fragments.add(fragment);
                User user = EmelieUtilities.generateRandomUser();
                fragment.bindData(user);
                return fragment;
            }

            @Override
            public int getCount() {
                return USER_COUNT;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    private void updateIndicatorTv()
    {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#000000'>" + currentItem + "</font>  /  " + totalNum));
    }

    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(this))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs().build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Subscribe
    public void removeFragment(DragToRemoveUser dragToRemoveUser)
    {
        removeFragmentFromViewPager(dragToRemoveUser.getFragment());
    }

    private void removeFragmentFromViewPager(Fragment fragment) {

        final int currentPosition = viewPager.getCurrentItem();

        viewPager.getAdapter().destroyItem(viewPager, currentPosition, fragment);
        USER_COUNT = USER_COUNT - 1;
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        fragments.remove(currentPosition);
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(currentPosition+1, false);
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentPosition, false);
            }
        }, 100);
    }

}