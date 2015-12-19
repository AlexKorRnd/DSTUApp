package com.example.alex.dstuapp.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.ui.BaseActivity;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.etLogin)
    EditText etLogin;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



}
