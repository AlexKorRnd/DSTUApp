package com.example.alex.dstuapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.network.RequestManager;
import com.example.alex.dstuapp.network.responses.LoginResponse;
import com.example.alex.dstuapp.utils.Prefs;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class StartActivity extends BaseActivity {

    public static final String LOG_TAG = "log_tag";

    @Bind(R.id.etLogin)
    EditText etLogin;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (Prefs.isLoggedIn()) {
            finish();
            startActivity(new Intent(this, NavigationDrawerActivity.class));
        }
    }

    @OnClick(R.id.log_in)
    void onLogInClick() {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        RequestManager.getInstance().getServiceMethods()
                .login(login, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse == null) {
                            Toast.makeText(StartActivity.this,
                                    getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (loginResponse.isSuccess()) {
                            // переходим на следующий экран
                            Prefs.setLoggedIn(true);
                            startActivity(new Intent(StartActivity.this, NavigationDrawerActivity.class));
                            finish();
                        } else {
                            showErrorToast(loginResponse.getMobileErr());
                        }

                        Prefs.setLoggedIn(true);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // выводим текстовое сообщение об ошибке
                        Toast.makeText(getBaseContext(), getString(R.string.error_unknown),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

}

