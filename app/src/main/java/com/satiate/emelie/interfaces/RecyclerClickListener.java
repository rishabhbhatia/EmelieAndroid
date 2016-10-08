package com.satiate.emelie.interfaces;

import android.view.View;

/**
 * Created by rishabh bhatia on 10/9/2016.
 */
public interface RecyclerClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
