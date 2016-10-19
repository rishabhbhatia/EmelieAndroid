package com.satiate.emelie.base;

import android.support.v4.app.Fragment;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class EmelieFragment extends Fragment implements EmelieFragmentInterface {

    @Override
    public void onBackPressed() {}

    @Override
    public void removeSelf(EmelieActivity activity, EmelieFragment fragment) {
        try {
            activity.removeFragment(activity.getSupportFragmentManager(), fragment);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
