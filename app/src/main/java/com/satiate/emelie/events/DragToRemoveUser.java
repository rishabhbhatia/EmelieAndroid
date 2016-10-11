package com.satiate.emelie.events;

import android.support.v4.app.Fragment;

/**
 * Created by Rishabh Bhatia on 10/11/2016.
 */

public class DragToRemoveUser {

    private Fragment fragment;

    public DragToRemoveUser(Fragment fragment)
    {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
