package com.satiate.emelie.cardslidepanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.models.Story;
import com.satiate.emelie.ui.activities.HomeActivity;
import com.satiate.emelie.ui.activities.HomeCardDetailsActivity;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.EmelieUtilities;
import com.special.ResideMenu.ResideMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class HomeCardFragment extends Fragment {

    @BindView(R.id.iv_home_nav)
    ImageView ivHomeNav;
    @BindView(R.id.iv_home_add_story)
    ImageView ivHomeAddStory;
    @BindView(R.id.rl_card_header)
    RelativeLayout rlCardHeader;
    @BindView(R.id.card_left_btn)
    Button cardLeftBtn;
    @BindView(R.id.card_right_btn)
    Button cardRightBtn;
    @BindView(R.id.card_center_btn)
    Button cardCenterBtn;
    @BindView(R.id.card_bottom_layout)
    LinearLayout cardBottomLayout;
    @BindView(R.id.image_slide_panel)
    CardSlidePanel imageSlidePanel;
    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private HomeActivity homeActivity;

    private List<Story> stories = new ArrayList<Story>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof HomeActivity)
        {
            homeActivity = (HomeActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView(View rootView) {
        CardSlidePanel slidePanel = (CardSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d(Const.TAG, "hello-" + stories.get(index).getTitle());
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d(Const.TAG, "hello" + stories.get(index).getTitle() + " type=" + type);
            }

            @Override
            public void onItemClick(View cardView, int index) {
                Log.d(Const.TAG, "hello" + stories.get(index).getTitle());
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);

        prepareDataList();
        slidePanel.fillData(stories);
    }

    private void prepareDataList() {

        //create next 3 cards, Total 4, 1 vis & 3 behind it
        //create 100 cards
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 100; i++) {
                Story story = new Story();
                story.setTitle(EmelieUtilities.generateRandomStrings(5));
                story.setLikesCount((int) (Math.random() * 10));
                story.setCommentsCount((int) (Math.random() * 6));
                stories.add(story);
            }
        }
    }

    @OnClick({R.id.ll_home_nav, R.id.iv_home_add_story, R.id.card_center_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_nav:
                if(homeActivity.resideMenu.isOpened())
                {
                    homeActivity.resideMenu.closeMenu();
                }else
                {
                    homeActivity.resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                }
                break;
            case R.id.iv_home_add_story:
                break;
            case R.id.card_center_btn:
                goToDetailsScreen();
                break;
        }
    }

    private void goToDetailsScreen()
    {
        homeActivity.startActivity(new Intent(homeActivity, HomeCardDetailsActivity.class));
    }
}
