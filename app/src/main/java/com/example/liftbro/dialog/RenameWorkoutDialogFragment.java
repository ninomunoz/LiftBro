package com.example.liftbro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/20/17.
 */

public class RenameWorkoutDialogFragment extends DialogFragment {

    public static final String RENAME_WORKOUT_DIALOG_TAG = RenameWorkoutDialogFragment.class.getSimpleName();
    private static final String ARG_WORKOUT_NAME = "WorkoutName";

    private RenameWorkoutListener mListener;
    private String mWorkoutName;

    public static RenameWorkoutDialogFragment newInstance(String workoutName) {
        RenameWorkoutDialogFragment frag = new RenameWorkoutDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_WORKOUT_NAME, workoutName);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWorkoutName = getArguments().getString(ARG_WORKOUT_NAME);

        try {
            mListener = (RenameWorkoutListener)getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement RenameWorkoutListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_rename_workout, null, false);
        final EditText etWorkoutName = view.findViewById(R.id.etWorkoutName);
        etWorkoutName.setText(mWorkoutName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.rename_workout));
        builder.setView(view);

        builder.setPositiveButton(getString(R.string.rename), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String workoutName = etWorkoutName.getText().toString();
                mListener.onRename(workoutName);
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
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

    public interface RenameWorkoutListener {
        void onRename(String newWorkoutName);
    }
}
