package com.satiate.emelie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.satiate.emelie.ui.fragments.HomeStoryCardFragment;
import com.satiate.emelie.utils.Const;

import java.util.List;

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
//            activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();

            if(fragments != null && fragments.size() != 0)
            {
                for(int i =0; i<fragments.size(); i++)
                {
                    Fragment fragment = fragments.get(i);

                    if(fragment == null) return;

                    if(fragment instanceof HomeStoryCardFragment)
                    {
                        Log.d(Const.TAG, "Found a story fragment");
                    }else
                    {
                        activity.getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
                        Log.d(Const.TAG, "removing a fragment");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
