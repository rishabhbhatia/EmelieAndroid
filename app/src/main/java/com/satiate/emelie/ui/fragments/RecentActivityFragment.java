package com.satiate.emelie.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.satiate.emelie.base.EmelieFragment;

/**
 * Created by Rishabh Bhatia on 10/14/2016.
 */

public class RecentActivityFragment extends EmelieFragment{

    public static RecentActivityFragment newInstance() {
        return new RecentActivityFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onBackPressed() {
    }
}
