package com.example.liftbro.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;

import com.example.liftbro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 10/22/17.
 */

public class EditExerciseDialogFragment extends DialogFragment {

    public static final String EDIT_EXERCISE_DIALOG_TAG = EditExerciseDialogFragment.class.getSimpleName();
    private static final String ARG_EXERCISE_ID = "exercise_id";
    private static final String ARG_EXERCISE_NAME = "exercise_name";
    private static final String ARG_EXERCISE_SETS = "exercise_sets";
    private static final String ARG_EXERCISE_REPS = "exercise_reps";
    private static final String ARG_EXERCISE_WEIGHT = "exercise_weight";
    private static final String ARG_EXERCISE_TIME = "exercise_time";

    private EditExerciseListener mListener;
    private String mExerciseName;
    private long mId;
    private int mSets;
    private int  mReps;
    private double mWeight;
    private int mTime;

    @Nullable @BindView(R.id.np_sets) NumberPicker npSets;
    @Nullable @BindView(R.id.np_reps) NumberPicker npReps;
    @Nullable @BindView(R.id.np_weight) NumberPicker npWeight;
    @Nullable @BindView(R.id.np_time) NumberPicker npTime;
    @Nullable @BindView(R.id.np_time_unit) NumberPicker npTimeUnit;

    public static EditExerciseDialogFragment newInstance(long id, String name, int sets, int reps, double weight, int time) {
        EditExerciseDialogFragment frag = new EditExerciseDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_EXERCISE_ID, id);
        args.putString(ARG_EXERCISE_NAME, name);
        args.putInt(ARG_EXERCISE_SETS, sets);
        args.putInt(ARG_EXERCISE_REPS, reps);
        args.putDouble(ARG_EXERCISE_WEIGHT, weight);
        args.putInt(ARG_EXERCISE_TIME, time);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mId = args.getLong(ARG_EXERCISE_ID);
        mExerciseName = args.getString(ARG_EXERCISE_NAME);
        mSets = args.getInt(ARG_EXERCISE_SETS);
        mReps = args.getInt(ARG_EXERCISE_REPS);
        mWeight = args.getDouble(ARG_EXERCISE_WEIGHT);
        mTime = args.getInt(ARG_EXERCISE_TIME);

        try {
            mListener = (EditExerciseListener)getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement EditExerciseListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mTime == 0) {
            final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_exercise_weight, null, false);
            ButterKnife.bind(this, view);

            npSets.setMinValue(1);
            npSets.setMaxValue(10);
            npSets.setValue(mSets);

            npReps.setMinValue(1);
            npReps.setMaxValue(50);
            npReps.setValue(mReps);

            final String[] weightValues = new String[101];
            for (int i = 0; i < weightValues.length; i++) {
                String weight = Integer.toString(i * 5);
                weightValues[i] = i == 0 ? "BW" : weight;
            }

            npWeight.setMinValue(0);
            npWeight.setMaxValue(100);
            npWeight.setDisplayedValues(weightValues);
            npWeight.setValue((int)mWeight == 0 ? 0 : (int)mWeight / 5);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(mExerciseName);
            builder.setView(view);

            builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mSets = npSets.getValue();
                    mReps = npReps.getValue();
                    int weightValue = npWeight.getValue();
                    mWeight = weightValue == 0 ? 0.0 : Double.parseDouble(weightValues[weightValue]);
                    mListener.onFinishEdit(mId, mSets, mReps, mWeight, mTime);
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
        else {
            final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_exercise_time, null, false);
            ButterKnife.bind(this, view);
            boolean isMinutes = mTime >= 60;

            npTime.setMinValue(1);
            npTime.setMaxValue(59);
            npTime.setValue(isMinutes ? mTime / 60 : mTime);

            npTimeUnit.setMinValue(0);
            npTimeUnit.setMaxValue(1);
            npTimeUnit.setValue(isMinutes ? 1 : 0);
            npTimeUnit.setDisplayedValues(getResources().getStringArray(R.array.np_time_array));

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(mExerciseName);
            builder.setView(view);

            builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mTime = npTime.getValue();
                    if (npTimeUnit.getValue() == 1) {
                        mTime *= 60;
                    }
                    mListener.onFinishEdit(mId, mSets, mReps, mWeight, mTime);
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
    }

    public interface EditExerciseListener {
        void onFinishEdit(long id, int sets, int reps, double weight, int time);
    }
}
