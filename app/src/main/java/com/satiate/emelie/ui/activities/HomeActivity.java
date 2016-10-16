package com.satiate.emelie.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.satiate.emelie.R;
import com.satiate.emelie.adapters.HomePagerTransformer;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.events.DragToRemoveUser;
import com.satiate.emelie.models.User;
import com.satiate.emelie.ui.fragments.AddStoryFragment;
import com.satiate.emelie.ui.fragments.HomeStoryCardFragment;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.EmelieUtilities;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends EmelieActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.position_view)
    View positionView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.ll_home_like_comment)
    LinearLayout llHomeLikeComment;
    @BindView(R.id.frame_fragment_container)
    FrameLayout frameFragmentContainer;
    @BindView(R.id.iv_tab_footer_1)
    ImageView ivTabFooter1;
    @BindView(R.id.ll_tab_footer_1)
    LinearLayout llTabFooter1;
    @BindView(R.id.iv_tab_footer_2)
    ImageView ivTabFooter2;
    @BindView(R.id.ll_tab_footer_2)
    LinearLayout llTabFooter2;
    @BindView(R.id.iv_tab_footer_3)
    ImageView ivTabFooter3;
    @BindView(R.id.ll_tab_footer_3)
    LinearLayout llTabFooter3;
    @BindView(R.id.iv_tab_footer_4)
    ImageView ivTabFooter4;
    @BindView(R.id.ll_tab_footer_4)
    LinearLayout llTabFooter4;
    @BindView(R.id.iv_tab_footer_5)
    ImageView ivTabFooter5;
    @BindView(R.id.ll_tab_footer_5)
    LinearLayout llTabFooter5;
    @BindView(R.id.ll_tab_footer_main)
    LinearLayout llTabFooterMain;
    @BindView(R.id.ll_tab_footer_container)
    LinearLayout llTabFooterContainer;
    @BindView(R.id.iv_home_nav)
    ImageView ivHomeNav;
    @BindView(R.id.iv_home_add_story)
    ImageView ivHomeAddStory;

    private HomePagerTransformer homePagerTransformer;
    private List<HomeStoryCardFragment> fragments = new ArrayList<>();
    public static final String randomImageUrl = "https://unsplash.it/200/300/?random";
    private ResideMenu resideMenu;

    private int USER_COUNT = 10000;
    public static int currentTabPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        EventBus.getDefault().register(HomeActivity.this);

//        dealStatusBar();
        initImageLoader();
        fillViewPager();
        createResideMenu();
    }

    private void createResideMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setShadowVisible(false);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            resideMenu.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }else {
            resideMenu.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }*/

        resideMenu.setBackground(R.drawable.background);

        resideMenu.attachToActivity(this);
//        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.addIgnoredView(viewPager);

        String titles[] = {"Home", "Profile", "Story", "Settings"};
        int icon[] = {R.drawable.ic_home_active, R.drawable.ic_profile_active, R.drawable.ic_camera_active,
                R.drawable.ic_like_footer_active};

        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            item.setId(i);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }

        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                Toast.makeText(HomeActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                Toast.makeText(HomeActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillViewPager() {

        homePagerTransformer = new HomePagerTransformer(this);
        viewPager.setPageTransformer(false, homePagerTransformer);


        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                HomeStoryCardFragment fragment = new HomeStoryCardFragment();
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
    public void removeFragment(DragToRemoveUser dragToRemoveUser) {
        removeFragmentFromViewPager(dragToRemoveUser.getFragment());
    }

    private void removeFragmentFromViewPager(Fragment fragment) {

        //TODO handle when element is last, then no +1 is available & if size is 0 then no+1 available
        final int currentPosition = viewPager.getCurrentItem();

        viewPager.getAdapter().destroyItem(viewPager, currentPosition, fragment);
        USER_COUNT = USER_COUNT - 1;
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        fragments.remove(currentPosition);
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(currentPosition + 1, false);
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentPosition, false);
            }
        }, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.ll_tab_footer_1, R.id.ll_tab_footer_2, R.id.ll_tab_footer_3, R.id.ll_tab_footer_4,
            R.id.ll_tab_footer_5, R.id.iv_home_nav, R.id.iv_home_add_story})
    public void onClick(View view) {

        Log.d(Const.TAG, "click event detected " + view.getId());
        switch (view.getId()) {
            case R.id.ll_tab_footer_1:
                //home screen
                if (currentTabPos == 0) return;
                removeAllFragments(HomeActivity.this);
                hideFragmentContainer();
                currentTabPos = 0;
                break;
            case R.id.ll_tab_footer_2:
                //search/discover people
                if (currentTabPos == 1) return;
                currentTabPos = 1;

                break;
            case R.id.ll_tab_footer_3:
                //add story screen
                if (currentTabPos == 2) return;
                showFragmentContainer();
                getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment_container,
                        AddStoryFragment.newInstance(), "addstory").commit();

                currentTabPos = 2;
                break;
            case R.id.ll_tab_footer_4:
                //activity screen
                if (currentTabPos == 3) return;

                break;
            case R.id.ll_tab_footer_5:
                //profile screen
                if (currentTabPos == 4) return;

                currentTabPos = 4;
                break;
            case 0:
                if(resideMenu.isOpened())
                {
                    resideMenu.closeMenu();
                }else
                {
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                }
                break;
            case 1:
                Log.d(Const.TAG, "clicked on menu item 1");
                break;
            case 2:
                Log.d(Const.TAG, "clicked on menu item 2");
                break;
            case 3:
                Log.d(Const.TAG, "clicked on menu item 3");
                break;
            case R.id.iv_home_nav:
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                break;
            case R.id.iv_home_add_story:

                break;
        }
    }

    private void hideFragmentContainer()
    {
        frameFragmentContainer.setVisibility(View.GONE);
    }

    private void showFragmentContainer()
    {
        frameFragmentContainer.setVisibility(View.VISIBLE);
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }*/

}