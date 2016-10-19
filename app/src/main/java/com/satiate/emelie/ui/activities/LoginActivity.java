package com.satiate.emelie.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.cv_login_username_holder)
    CardView cvLoginUsernameHolder;
    @BindView(R.id.cv_login_password_holder)
    CardView cvLoginPasswordHolder;
    @BindView(R.id.cb_login_password)
    CheckBox cbLoginPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_login_forgot_password)
    TextView tvLoginForgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple);
        ButterKnife.bind(this);

        //Hey Beautiful
    }

    @OnClick({R.id.bt_login_signup,R.id.cv_login_username_holder, R.id.cv_login_password_holder, R.id.cb_login_password, R.id.bt_login, R.id.tv_login_forgot_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_signup:
                SignUpFragment signUpFragment = new SignUpFragment();
                switchFragment(getSupportFragmentManager(), signUpFragment, "signup", Const.FRAGMENT_SWITCH_ADD);
                break;
            case R.id.cv_login_username_holder:
                break;
            case R.id.cv_login_password_holder:
                break;
            case R.id.cb_login_password:
                break;
            case R.id.bt_login:
                launchHomeScreen();
                break;
            case R.id.tv_login_forgot_password:
                break;
        }
    }

    private void launchHomeScreen()
    {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
