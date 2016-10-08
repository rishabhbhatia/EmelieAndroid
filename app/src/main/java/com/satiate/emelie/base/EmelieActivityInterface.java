package com.satiate.emelie.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public interface EmelieActivityInterface {

    void removeFragment(FragmentManager fm, Fragment fragment);

    void removeAllFragments(EmelieActivity activity);
}
