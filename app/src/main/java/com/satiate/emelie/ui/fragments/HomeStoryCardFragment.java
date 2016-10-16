package com.satiate.emelie.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.satiate.emelie.R;
import com.satiate.emelie.events.ShowUserDetailsEvent;
import com.satiate.emelie.models.User;
import com.satiate.emelie.ui.activities.HomeActivity;
import com.satiate.emelie.ui.activities.HomeDetailActivity;
import com.satiate.emelie.utils.AspectRatioCardView;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.DragLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class HomeStoryCardFragment extends Fragment implements DragLayout.GotoDetailListener {


    @BindView(R.id.head1)
    ImageView head1;
    @BindView(R.id.head2)
    ImageView head2;
    @BindView(R.id.head3)
    ImageView head3;
    @BindView(R.id.head4)
    ImageView head4;
    @BindView(R.id.frame_home_card_container)
    FrameLayout frameHomeCardContainer;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_home_footer_name)
    TextView tvHomeFooterName;
    @BindView(R.id.tv_home_footer_age)
    TextView tvHomeFooterAge;
    @BindView(R.id.rl_home_footer)
    RelativeLayout rlHomeFooter;
    @BindView(R.id.card_home)
    AspectRatioCardView cardHome;
    @BindView(R.id.drag_layout)
    DragLayout dragLayout;
    @BindView(R.id.tv_home_like)
    TextView tvHomeLike;
    @BindView(R.id.tv_home_comment)
    TextView tvHomeComment;
    @BindView(R.id.tv_home_views)
    TextView tvHomeViews;
    @BindView(R.id.tv_home_comments)
    TextView tvHomeComments;
    @BindView(R.id.ll_home_footer_like)
    LinearLayout llHomeFooterLike;
    @BindView(R.id.ll_home_footer_comment)
    LinearLayout llHomeFooterComment;
    @BindView(R.id.iv_story_card_like_animation)
    ImageView ivStoryCardLikeAnimation;

    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_story_card, null);
        ButterKnife.bind(this, rootView);
        dragLayout.setGotoDetailListener(this, HomeStoryCardFragment.this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateScreen();
    }

    private void populateScreen() {
        ImageLoader.getInstance().displayImage(user.getImageUrl(), image);
        tvHomeFooterName.setText(user.getName());
        tvHomeFooterAge.setText(user.getAge() + " days ago");
    }

    @Override
    public void gotoDetail() {

        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(image, HomeDetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(head1, HomeDetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, HomeDetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(head3, HomeDetailActivity.HEAD3_TRANSITION_NAME),
                new Pair(head4, HomeDetailActivity.HEAD4_TRANSITION_NAME),
                new Pair(tvHomeFooterName, HomeDetailActivity.FOOTER_NAME_TRANSITION_NAME),
                new Pair(tvHomeFooterAge, HomeDetailActivity.FOOTER_AGE_TRANSITION_NAME),
                new Pair(tvHomeLike, HomeDetailActivity.FOOTER_LIKE_TRANSITION_NAME),
                new Pair(tvHomeComment, HomeDetailActivity.FOOTER_COMMENT_TRANSITION_NAME),
                new Pair(tvHomeViews, HomeDetailActivity.FOOTER_VIEWS_TRANSITION_NAME),
                new Pair(tvHomeComments, HomeDetailActivity.FOOTER_COMMENTS_TRANSITION_NAME)
        );
        Intent intent = new Intent(activity, HomeDetailActivity.class);
        ShowUserDetailsEvent showUserDetailsEvent = new ShowUserDetailsEvent(user);
        EventBus.getDefault().postSticky(showUserDetailsEvent);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void bindData(User user) {
        this.user = user;
    }

    @OnClick({R.id.ll_home_footer_like, R.id.ll_home_footer_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_footer_like:
                break;
            case R.id.ll_home_footer_comment:
                break;
        }
    }

    public void likeStory() {
        Log.d(Const.TAG, "like story");
        ivStoryCardLikeAnimation.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeOutUp).duration(1000).playOn(ivStoryCardLikeAnimation);
    }

}