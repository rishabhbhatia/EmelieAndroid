package com.satiate.emelie.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public static final String ADDRESS1_TRANSITION_NAME = "address1";
    public static final String ADDRESS2_TRANSITION_NAME = "address2";
    public static final String ADDRESS3_TRANSITION_NAME = "address3";
    public static final String ADDRESS4_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.address1)
    TextView address1;
    @BindView(R.id.address2)
    ImageView address2;
    @BindView(R.id.address3)
    TextView address3;
    @BindView(R.id.address4)
    TextView address4;
    @BindView(R.id.address5)
    TextView address5;
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

        /*imageView = (ImageView) findViewById(R.id.image);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        address3 = findViewById(R.id.address3);
        address4 = findViewById(R.id.address4);
        address5 = findViewById(R.id.address5);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, image);

        ViewCompat.setTransitionName(image, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address1, ADDRESS1_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(address3, ADDRESS3_TRANSITION_NAME);
        ViewCompat.setTransitionName(address4, ADDRESS4_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
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
        if(motionEvent1.getAxisValue(1) - motionEvent.getAxisValue(1) > 250)
        {
            Log.d(Const.TAG, "hi i flinged down");
            HomeDetailActivity.this.finish();
        }
        return false;
    }
}