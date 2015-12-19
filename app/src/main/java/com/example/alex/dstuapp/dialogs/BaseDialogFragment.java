package com.example.alex.dstuapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

public class BaseDialogFragment<T> extends DialogFragment {

    static final String PARAM_REQUEST_CODE = "PARAM_REQUEST_CODE";
    static final String PARAM_POSITIVE_BUTTON_TEXT = "PARAM_POSITIVE_BUTTON_TEXT";
    static final String PARAM_NEGATIVE_BUTTON_TEXT = "PARAM_NEGATIVE_BUTTON_TEXT";
    static final String PARAM_TITLE = "PARAM_TITLE";
    static final String PARAM_MESSAGE = "PARAM_MESSAGE";
    static final String PARAM_PARAMS = "PARAM_PARAMS";
    static final String PARAM_CANCELABLE = "PARAM_CANCELABLE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(getArguments().getBoolean(PARAM_CANCELABLE));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return buildDialog().create();
    }

    protected AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString(PARAM_TITLE))
                .setMessage(getArguments().getString(PARAM_MESSAGE));

        String positiveButtonText = getArguments().getString(PARAM_POSITIVE_BUTTON_TEXT);
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText, (dialog, which) -> {
                onPositiveButtonClicked();
            });
        }

        String negativeButtonText = getArguments().getString(PARAM_NEGATIVE_BUTTON_TEXT);
        if (!TextUtils.isEmpty(negativeButtonText)) {
            builder.setNegativeButton(negativeButtonText, (dialog, which) -> {
                onNegativeButtonClicked();
            });
        }
        return builder;
    }

    protected T getListener() {
        return (T) (getTargetFragment() != null ? getTargetFragment() : getActivity());
    }

    protected int getRequestCode() {
        return getArguments().getInt(PARAM_REQUEST_CODE);
    }

    protected void onNegativeButtonClicked() {
        dismiss();
    }

    protected void onPositiveButtonClicked() {
        dismiss();
    }

    protected Bundle getParams() {
        return getArguments().getBundle(PARAM_PARAMS);
    }

    public static class BaseDialogBuilder<T extends BaseDialogBuilder> {

        protected final Context context;
        protected Bundle args;
        protected Fragment targetFragment;

        public BaseDialogBuilder(Context context) {
            this.context = context;
            args = new Bundle();
        }

        protected T self() {
            return (T) this;
        }

        public T setRequestCode(int requestCode) {
            args.putInt(PARAM_REQUEST_CODE, requestCode);
            return self();
        }

        public T setTargetFragment(Fragment fragment) {
            targetFragment = fragment;
            return self();
        }

        public T setTitle(String title) {
            args.putString(PARAM_TITLE, title);
            return self();
        }

        public T setTitle(@StringRes int title) {
            return setTitle(context.getString(title));
        }

        public T setMessage(String message) {
            args.putString(PARAM_MESSAGE, message);
            return self();
        }

        public T setMessage(@StringRes int message) {
            return setMessage(context.getString(message));
        }

        public T setPositiveButtonText(String text) {
            args.putString(PARAM_POSITIVE_BUTTON_TEXT, text);
            return self();
        }

        public T setPositiveButtonText(@StringRes int text) {
            return setPositiveButtonText(context.getString(text));
        }

        public T setNegativeButtonText(String text) {
            args.putString(PARAM_NEGATIVE_BUTTON_TEXT, text);
            return self();
        }

        public T setNegativeButtonText(@StringRes int text) {
            return setNegativeButtonText(context.getString(text));
        }

        public T setParams(Bundle params) {
            args.putBundle(PARAM_PARAMS, params);
            return self();
        }

        public T setCancelable(boolean cancelable) {
            args.putBoolean(PARAM_CANCELABLE, cancelable);
            return self();
        }

        public <D extends Fragment> D build(Class<D> dialogClass) {
            try {
                D result = dialogClass.newInstance();
                result.setArguments(args);
                if (targetFragment != null) {
                    result.setTargetFragment(targetFragment, 0);
                }
                return result;
            } catch (java.lang.InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

    }

}
