package com.satiate.emelie.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.satiate.emelie.R;
import com.satiate.emelie.base.EmelieFragment;
import com.satiate.emelie.ui.activities.HomeActivity;
import com.satiate.emelie.ui.activities.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rishabh Bhatia on 19/10/16.
 */

public class SignUpFragment extends EmelieFragment {

    @BindView(R.id.et_signup_fname)
    EditText etSignupFname;
    @BindView(R.id.et_signup_lname)
    EditText etSignupLname;
    @BindView(R.id.et_signup_uname)
    EditText etSignupUname;
    @BindView(R.id.et_signup_email)
    EditText etSignupEmail;
    @BindView(R.id.et_signup_pass)
    EditText etSignupPass;
    @BindView(R.id.et_signup_confirm_pass)
    EditText etSignupConfirmPass;
    @BindView(R.id.bt_signup)
    Button btSignup;
    @BindView(R.id.bt_signup_fb)
    Button btSignupFb;
    @BindView(R.id.bt_signup_google)
    Button btSignupGoogle;
    @BindView(R.id.ll_signup_input_holder)
    LinearLayout llSignupInputHolder;

    private LoginActivity loginActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof LoginActivity)
        {
            loginActivity = (LoginActivity) context;
        }
    }

    @OnClick({R.id.bt_signup, R.id.bt_signup_fb, R.id.bt_signup_google})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_signup:
                onBackPressed();
                break;
            case R.id.bt_signup_fb:
                onBackPressed();
                break;
            case R.id.bt_signup_google:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        removeSelf(loginActivity, SignUpFragment.this);
    }
}
