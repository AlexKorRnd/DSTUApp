package com.example.alex.dstuapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class AlertDialogFragment extends BaseDialogFragment<AlertDialogFragment.AlertDialogListener> {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return buildDialog().create();
    }

    @Override
    protected void onNegativeButtonClicked() {
        dismiss();
        getListener().onNegativeButtonClicked(getRequestCode(), getParams());
    }

    protected void onPositiveButtonClicked() {
        dismiss();
        getListener().onPositiveButtonClicked(getRequestCode(), getParams());
    }

    public interface AlertDialogListener {
        void onPositiveButtonClicked(int requestCode, Bundle params);
        void onNegativeButtonClicked(int requestCode, Bundle params);
    }

    public static class AlertDialogBuilder extends BaseDialogBuilder<AlertDialogBuilder> {

        public AlertDialogBuilder(Context context) {
            super(context);
        }

        public AlertDialogFragment build() {
            return build(AlertDialogFragment.class);
        }

    }
}
