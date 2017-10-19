package com.example.liftbro.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/19/17.
 */

public class AddWorkoutDialogFragment extends DialogFragment {

    public static final String ADD_WORKOUT_DIALOG_TAG = AddWorkoutDialogFragment.class.getSimpleName();

    private AddWorkoutListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_workout, null, false);
        final EditText etWorkoutName = view.findViewById(R.id.etWorkoutName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Workout");
        builder.setView(view);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String workoutName = etWorkoutName.getText().toString();
                mListener.onAdd(workoutName);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        Dialog dlg = builder.create();
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etWorkoutName, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        return dlg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof AddWorkoutListener)) {
            throw new ClassCastException(context.toString() + " must implement AddWorkoutListener");
        }
        mListener = (AddWorkoutListener)context;
    }

    public interface AddWorkoutListener {
        void onAdd(String workoutName);
    }
}