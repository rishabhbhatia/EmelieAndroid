package com.satiate.emelie.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieFragment;
import com.satiate.emelie.ui.activities.HomeCardDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rishabh Bhatia on 20/10/16.
 */

public class PhotosFragment extends EmelieFragment {

    private HomeCardDetailsActivity homeCardDetailsActivity;


    @BindView(R.id.srv_photos)
    SuperRecyclerView srvPhotos;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof HomeCardDetailsActivity)
        {
            homeCardDetailsActivity = (HomeCardDetailsActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
