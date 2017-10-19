package com.example.liftbro.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/19/17.
 */

public class AboutDialogFragment extends DialogFragment {

    public static final String ABOUT_DIALOG_TAG = AboutDialogFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null, false);
        TextView tvAbout = view.findViewById(R.id.tvAbout);
        tvAbout.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setView(view);
        return builder.create();
    }
}