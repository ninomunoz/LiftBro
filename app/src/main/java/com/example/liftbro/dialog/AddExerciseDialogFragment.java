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
 * Created by i57198 on 10/24/17.
 */

public class AddExerciseDialogFragment extends DialogFragment {

    public static final String ADD_EXERCISE_DIALOG_TAG = AddExerciseDialogFragment.class.getSimpleName();
    private static final String ARG_EXERCISE_NAME = "exercise_name";
    private static final String ARG_IS_TIMED = "is_timed";

    private final int DEFAULT_SETS = 3;
    private final int DEFAULT_REPS = 10;
    private final double DEFAULT_WEIGHT = 100.0;
    private final int DEFAULT_TIME = 600;

    private AddExerciseListener mListener;
    private String mExerciseName;
    private boolean mIsTimed;

    @Nullable @BindView(R.id.np_sets) NumberPicker npSets;
    @Nullable @BindView(R.id.np_reps) NumberPicker npReps;
    @Nullable @BindView(R.id.np_weight) NumberPicker npWeight;
    @Nullable @BindView(R.id.np_time) NumberPicker npTime;
    @Nullable @BindView(R.id.np_time_unit) NumberPicker npTimeUnit;

    public static AddExerciseDialogFragment newInstance(String name, boolean isTimed) {
        AddExerciseDialogFragment frag = new AddExerciseDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXERCISE_NAME, name);
        args.putBoolean(ARG_IS_TIMED, isTimed);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mExerciseName = args.getString(ARG_EXERCISE_NAME);
        mIsTimed = args.getBoolean(ARG_IS_TIMED);

        try {
            mListener = (AddExerciseListener)getTargetFragment();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement AddExerciseListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (!mIsTimed) {
            final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_exercise_weight, null, false);
            ButterKnife.bind(this, view);

            npSets.setMinValue(1);
            npSets.setMaxValue(10);
            npSets.setValue(DEFAULT_SETS);

            npReps.setMinValue(1);
            npReps.setMaxValue(50);
            npReps.setValue(DEFAULT_REPS);

            final String[] weightValues = new String[101];
            for (int i = 0; i < weightValues.length; i++) {
                String weight = Integer.toString(i * 5);
                weightValues[i] = i == 0 ? "BW" : weight;
            }

            npWeight.setMinValue(0);
            npWeight.setMaxValue(100);
            npWeight.setDisplayedValues(weightValues);
            npWeight.setValue((int)DEFAULT_WEIGHT / 5);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(mExerciseName);
            builder.setView(view);

            builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int sets = npSets.getValue();
                    int reps = npReps.getValue();
                    int weightValue = npWeight.getValue();
                    double weight = weightValue == 0 ? 0.0 : Double.parseDouble(weightValues[weightValue]);
                    mListener.onFinishAdd(sets, reps, weight);
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

            npTime.setMinValue(1);
            npTime.setMaxValue(59);
            npTime.setValue(DEFAULT_TIME / 60);

            npTimeUnit.setMinValue(0);
            npTimeUnit.setMaxValue(1);
            npTimeUnit.setValue(1);
            npTimeUnit.setDisplayedValues(getResources().getStringArray(R.array.np_time_array));

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(mExerciseName);
            builder.setView(view);

            builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int time = npTime.getValue();
                    if (npTimeUnit.getValue() == 1) {
                        time *= 60;
                    }
                    mListener.onFinishAdd(time);
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

    public interface AddExerciseListener {
        void onFinishAdd(int sets, int reps, double weight);
        void onFinishAdd(int time);
    }
}