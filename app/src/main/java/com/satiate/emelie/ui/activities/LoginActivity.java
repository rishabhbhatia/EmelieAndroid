package com.satiate.emelie.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shipsy on 18/10/16.
 */

public class LoginActivity extends EmelieActivity {

    @BindView(R.id.ll_login_input_holder)
    LinearLayout llLoginInputHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple);
        ButterKnife.bind(this);

        //Hey Beautiful
    }
}
