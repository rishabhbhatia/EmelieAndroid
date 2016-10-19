package com.satiate.emelie.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.cardslidepanel.HomeCardFragment;
import com.satiate.emelie.utils.Const;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends EmelieActivity implements View.OnClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.iv_home_nav)
    ImageView ivHomeNav;
    @BindView(R.id.iv_home_add_story)
    ImageView ivHomeAddStory;
    @BindView(R.id.rl_home_main)
    RelativeLayout rlHomeMain;

    public static final String randomImageUrl = "https://unsplash.it/200/300/?random";
    private ResideMenu resideMenu;

    private int USER_COUNT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

//        createResideMenu();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new HomeCardFragment())
                .commitAllowingStateLoss();
    }

    private void createResideMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.setShadowVisible(false);

        resideMenu.setBackground(R.drawable.background);

        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.addIgnoredView(viewPager);

        String titles[] = {"Home", "Profile", "Story", "Settings"};
        int icon[] = {R.drawable.ic_home_active, R.drawable.ic_profile_active, R.drawable.ic_camera_active,
                R.drawable.ic_like_footer_active};

        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            item.setId(i);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
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

    @OnClick({R.id.iv_home_nav, R.id.iv_home_add_story})
    public void onClick(View view) {

        switch (view.getId()) {
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
}