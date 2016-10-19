package com.satiate.emelie.cardslidepanel;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.satiate.emelie.R;
import com.satiate.emelie.models.Story;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.EmelieUtilities;

@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class HomeCardFragment extends Fragment {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private List<Story> stories = new ArrayList<Story>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);
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
        for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < 100; i++)
            {
                Story story = new Story();
                story.setTitle(EmelieUtilities.generateRandomStrings(5));
                story.setLikesCount((int) (Math.random() * 10));
                story.setCommentsCount((int) (Math.random() * 6));
                stories.add(story);
            }
        }
    }

}
