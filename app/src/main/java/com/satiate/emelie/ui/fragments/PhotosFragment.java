package com.satiate.emelie.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.satiate.emelie.R;
import com.satiate.emelie.adapters.PhotosDetailsAdapter;
import com.satiate.emelie.adapters.QuiltPhotoAdapter;
import com.satiate.emelie.base.EmelieFragment;
import com.satiate.emelie.quiltview.QuiltView;
import com.satiate.emelie.ui.activities.HomeCardDetailsActivity;
import com.satiate.emelie.utils.EmelieUtilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 20/10/16.
 */

public class PhotosFragment extends EmelieFragment {

    @BindView(R.id.srv_photos)
    SuperRecyclerView srvPhotos;
    @BindView(R.id.quilt)
    QuiltView quiltView;

    private HomeCardDetailsActivity homeCardDetailsActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof HomeCardDetailsActivity) {
            homeCardDetailsActivity = (HomeCardDetailsActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
/*
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        PhotosDetailsAdapter photosDetailsAdapter = new PhotosDetailsAdapter(homeCardDetailsActivity, EmelieUtilities.generateRandomStory());
        srvPhotos.setAdapter(photosDetailsAdapter);
        srvPhotos.setLayoutManager(staggeredGridLayoutManager);*/

//        quiltView.setChildPadding(5);
//        quiltView.setOrientation(true);
        quiltView.setAdapter(new QuiltPhotoAdapter(homeCardDetailsActivity, EmelieUtilities.generateRandomStory()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
