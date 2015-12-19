package com.example.alex.dstuapp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.dialogs.AlertDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements
        AlertDialogFragment.AlertDialogListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    protected void showInDevelopingDialog() {
        showAlertDialog(R.string.dialog_in_developing);
    }

    protected void showAlertDialog(@Nullable String title, @NonNull String message) {
        showAlertDialog(title, message, 0);
    }

    protected void showAlertDialog(@Nullable String title, @NonNull String message, int requestCode) {
        final AlertDialogFragment.AlertDialogBuilder dialogBuilder = new AlertDialogFragment.AlertDialogBuilder(this)
                .setMessage(message)
                .setRequestCode(requestCode)
                .setCancelable(true);
                //.setPositiveButtonText(R.string.ok);
        if (title != null) {
            dialogBuilder.setTitle(title);
        }
        dialogBuilder
                .build()
                .show(getSupportFragmentManager(), null);
    }

    protected void showAlertDialog(@StringRes int title, @StringRes int message) {
        showAlertDialog(getString(title), getString(message));
    }

    protected void showAlertDialog(@StringRes int message) {
        showAlertDialog(null, getString(message));
    }

    protected void showAlertDialog(@NonNull String message) {
        showAlertDialog(null, message);
    }

    protected void showAlertDialogWithRequestCode(@StringRes int title, @StringRes int message, int requestCode) {
        showAlertDialog(getString(title), getString(message), requestCode);
    }

    protected void showAlertDialogWithRequestCode(@StringRes int message, int requestCode) {
        showAlertDialog(null, getString(message), requestCode);
    }

    protected void showAlertDialogWithRequestCode(@NonNull String message, int requestCode) {
        showAlertDialog(null, message, requestCode);
    }


    @Override
    public void onPositiveButtonClicked(int requestCode, Bundle params) {

    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Bundle params) {

    }

    protected String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "auth_00" :
                return getString(R.string.error_authorization);
            case "auth_01" :
                return getString(R.string.error_you_has_not_authorized);
            default:
                return getString(R.string.error_unknown);
        }
    }

    protected void showErrorToast(String errorCode) {
        Toast.makeText(this, getErrorMessage(errorCode), Toast.LENGTH_LONG)
                .show();
    }
}
