package com.satiate.emelie.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.satiate.emelie.R;
import com.satiate.emelie.utils.Const;
import com.satiate.emelie.utils.MyExceptionHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class HomeDetailActivity extends FragmentActivity implements GestureDetector.OnGestureListener {

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

    public static final String FOOTER_NAME_TRANSITION_NAME = "name";
    public static final String FOOTER_AGE_TRANSITION_NAME = "age";
    @BindView(R.id.tv_home_footer_name)
    TextView tvHomeFooterName;
    @BindView(R.id.tv_home_footer_age)
    TextView tvHomeFooterAge;
    @BindView(R.id.ll_home_footer)
    LinearLayout llHomeFooter;

    private boolean shouldFinish = false;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.detail_list_container)
    LinearLayout detailListContainer;
    @BindView(R.id.ll_home_details_container)
    LinearLayout llHomeDetailsContainer;

    private GestureDetector gestureScanner;

    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME,
            HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2,
            R.drawable.head3, R.drawable.head4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        ButterKnife.bind(this);

        //Custom exception handler
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                HomeDetailActivity.class));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, image);

        ViewCompat.setTransitionName(image, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvHomeFooterName, FOOTER_NAME_TRANSITION_NAME);
        ViewCompat.setTransitionName(image, FOOTER_AGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(rating, RATINGBAR_TRANSITION_NAME);

        dealListView();

        gestureScanner = new GestureDetector(HomeDetailActivity.this, this);
        llHomeDetailsContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(Const.TAG, "gesture detector is setup");
                gestureScanner.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.home_detail_list_item, null);
            detailListContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            if (i < headStrs.length) {
                headView.setImageResource(imageIds[i % imageIds.length]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(Const.TAG, "motion event down");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(Const.TAG, "motion event scroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(Const.TAG, "hello motion event: " + motionEvent + " to " + motionEvent1);

        //motionEvent1 - motionEvent means swiping from top to down
        if (motionEvent1.getAxisValue(1) - motionEvent.getAxisValue(1) > 250) {
            Log.d(Const.TAG, "hi i flinged down");
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        goBackToHome();
//        super.onBackPressed();
    }

    public void goBackToHome() {
        shouldFinish = true;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeDetailActivity.this, null);
        Intent intent = new Intent(HomeDetailActivity.this, HomeActivity.class);
        ActivityCompat.startActivity(HomeDetailActivity.this, intent, options.toBundle());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (shouldFinish) {
            HomeDetailActivity.this.finish();
        }
    }
}