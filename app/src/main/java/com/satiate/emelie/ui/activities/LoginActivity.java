package com.satiate.emelie.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieActivity;
import com.satiate.emelie.ui.fragments.SignUpFragment;
import com.satiate.emelie.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rishabh Bhatia on 18/10/16.
 */

public class LoginActivity extends EmelieActivity {

    @BindView(R.id.ll_login_input_holder)
    LinearLayout llLoginInputHolder;
    @BindView(R.id.bt_login_signup)
    Button btLoginSignup;
    @BindView(R.id.ll_login_signup_holder)
    LinearLayout llLoginSignupHolder;
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple);
        ButterKnife.bind(this);

        //Hey Beautiful
    }

    @OnClick(R.id.bt_login_signup)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_signup :
                SignUpFragment signUpFragment = new SignUpFragment();
                switchFragment(getSupportFragmentManager(), signUpFragment, "signup", Const.FRAGMENT_SWITCH_ADD);
                break;
        }
    }
}
