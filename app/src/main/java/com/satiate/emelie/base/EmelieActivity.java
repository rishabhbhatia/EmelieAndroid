package com.satiate.emelie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class EmelieActivity extends AppCompatActivity implements EmelieActivityInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void removeFragment(FragmentManager fm, Fragment fragment) {
        try {
            fm.beginTransaction().remove(fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllFragments(EmelieActivity activity) {
        try {
            activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
