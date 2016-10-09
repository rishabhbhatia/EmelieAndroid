package com.satiate.emelie.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.satiate.emelie.R;
import com.satiate.emelie.ui.activities.HomeDetailActivity;
import com.satiate.emelie.utils.DragLayout;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {

    private ImageView imageView;
    private View address1, address2, address3, address4, address5;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4;
    private String imageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        address1 = dragLayout.findViewById(R.id.address1);
        address2 = dragLayout.findViewById(R.id.address2);
        address3 = dragLayout.findViewById(R.id.address3);
        address4 = dragLayout.findViewById(R.id.address4);
        address5 = dragLayout.findViewById(R.id.address5);
        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);

        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);

        dragLayout.setGotoDetailListener(this);
        return rootView;
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, HomeDetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(address1, HomeDetailActivity.ADDRESS1_TRANSITION_NAME),
                new Pair(address2, HomeDetailActivity.ADDRESS2_TRANSITION_NAME),
                new Pair(address3, HomeDetailActivity.ADDRESS3_TRANSITION_NAME),
                new Pair(address4, HomeDetailActivity.ADDRESS4_TRANSITION_NAME),
                new Pair(address5, HomeDetailActivity.ADDRESS5_TRANSITION_NAME),
                new Pair(ratingBar, HomeDetailActivity.RATINGBAR_TRANSITION_NAME),
                new Pair(head1, HomeDetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, HomeDetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(head3, HomeDetailActivity.HEAD3_TRANSITION_NAME),
                new Pair(head4, HomeDetailActivity.HEAD4_TRANSITION_NAME)
        );
        Intent intent = new Intent(activity, HomeDetailActivity.class);
        intent.putExtra(HomeDetailActivity.EXTRA_IMAGE_URL, imageUrl);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}