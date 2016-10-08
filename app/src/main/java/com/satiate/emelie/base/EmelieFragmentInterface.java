package com.satiate.emelie.base;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public interface EmelieFragmentInterface {

    String onBackPressed();

    void removeSelf(EmelieActivity activity, EmelieFragment fragment);
}
