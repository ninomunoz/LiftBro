package com.example.liftbro.async;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.liftbro.data.LiftContract;
import com.example.liftbro.fragment.AddExerciseFragment;

/**
 * Created by i57198 on 10/24/17.
 */

public class FilterExercisesTask extends AsyncTask<Long, Void, Cursor> {

    AddExerciseFragment mFrag;

    public FilterExercisesTask(AddExerciseFragment frag) {
        mFrag = frag;
    }

    @Override
    protected Cursor doInBackground(Long... ids) {
        String selection = LiftContract.ExerciseEntry.COLUMN_MUSCLEGROUP_ID + " = ?";
        String[] selectionArgs = { ids[0].toString() };

        Cursor cursor = mFrag.getActivity().getContentResolver().query(
                LiftContract.ExerciseEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                LiftContract.ExerciseEntry.COLUMN_NAME
        );

        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mFrag.loadExercises(cursor);
    }
}