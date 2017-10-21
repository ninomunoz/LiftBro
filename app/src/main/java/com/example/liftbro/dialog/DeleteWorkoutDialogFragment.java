package com.example.liftbro.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/20/17.
 */

public class DeleteWorkoutDialogFragment extends DialogFragment {

    public static final String DELETE_WORKOUT_DIALOG_TAG = DeleteWorkoutDialogFragment.class.getSimpleName();

    private DeleteWorkoutListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (DeleteWorkoutListener)getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DeleteWorkoutListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_workout));
        builder.setMessage(getString(R.string.dlg_delete_workout_msg));

        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDelete();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    public interface DeleteWorkoutListener {
        void onDelete();
    }
}