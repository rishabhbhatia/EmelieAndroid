package com.satiate.emelie;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rishabh Bhatia on 10/9/2016.
 */

public class EmelieApplication extends Application {

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public Context getContext() {
        return context;
    }
}
