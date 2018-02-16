package com.example.liftbro.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.liftbro.R;
import com.example.liftbro.util.Analytics;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 2/15/18.
 */

public class LiftFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_STOPWATCH_TIME = "stopwatchTime";
    private static final String ARG_IS_STOPWATCH_RUNNING = "isStopwatchRunning";
    private static final String ARG_TIME_WHEN_STOPPED = "timeWhenStopped";

    @BindView(R.id.chronometer)
    Chronometer mChronometer;
    @BindView(R.id.tv_start) TextView tvStart;
    @BindView(R.id.tv_stop) TextView tvStop;

    private long mTimeWhenStopped = 0;
    private boolean mIsStopwatchRunning;

    public static LiftFragment newInstance() {
        return new LiftFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lift, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            mTimeWhenStopped = savedInstanceState.getLong(ARG_TIME_WHEN_STOPPED);
            mIsStopwatchRunning = savedInstanceState.getBoolean(ARG_IS_STOPWATCH_RUNNING);

            if (mIsStopwatchRunning) {
                startStopwatch(savedInstanceState.getLong(ARG_STOPWATCH_TIME));
            }
            else {
                mChronometer.setBase(SystemClock.elapsedRealtime() + mTimeWhenStopped);
            }
        }

        tvStart.setOnClickListener(this);
        tvStart.setTextColor(Color.GREEN);
        tvStop.setTextColor(Color.RED);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO: Create menu with 'End Workout' option
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: handle 'End Workout'
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_STOPWATCH_TIME, mChronometer.getBase());
        outState.putBoolean(ARG_IS_STOPWATCH_RUNNING, mIsStopwatchRunning);
        outState.putLong(ARG_TIME_WHEN_STOPPED, mTimeWhenStopped);
    }


    private void startStopwatch(long base) {
        mChronometer.setBase(base);
        mChronometer.start();
        mIsStopwatchRunning = true;
        tvStart.setTextColor(Color.DKGRAY);
        tvStart.setOnClickListener(null);
        tvStop.setText(getString(R.string.stop));
        tvStop.setOnClickListener(this);
    }

    private void stopStopwatch() {
        tvStop.setText(getString(R.string.reset));
        tvStart.setTextColor(Color.GREEN);
        mTimeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
        mIsStopwatchRunning = false;
    }

    private void resetStopwatch() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mTimeWhenStopped = 0;
        tvStop.setOnClickListener(null);
    }

    // View.OnClickListener implementation
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startStopwatch(SystemClock.elapsedRealtime() + mTimeWhenStopped);
                Analytics.logEventStartTimer(getActivity());
                break;
            case R.id.tv_stop:
                if (tvStop.getText().toString().equals(getString(R.string.stop))) { // stop
                    stopStopwatch();
                }
                else { // reset
                    resetStopwatch();
                }
                tvStart.setOnClickListener(this);
                break;
        }
    }
}
