package com.tpillon.colormemory4.Logic;

import android.os.AsyncTask;
import android.widget.Toast;

import com.tpillon.colormemory4.View.Activities.MainActivity;


public class TimeoutTask extends AsyncTask<Integer,Integer,Void> {

    /**
     * associated activity
     */
    private final MainActivity _activity;

    public TimeoutTask(MainActivity activity){
        _activity=activity;
    }

    /**
     * update value label
     * @param allTimes : [0] time to show
     */
    @Override
    protected Void doInBackground(Integer... allTimes) {
        int time = allTimes[0];

        publishProgress(time);

        for(int i = time-1; i>=0; i--){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                // TODO
            }
            publishProgress(i);
        }

        // NOTE : default return because we are in AsyncTask
        //        so not use "void" but "Void" => so need return null;
        return null;
    }

    /**
     * update time label with UI Thread
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        _activity.setTime(values[0]);
    }

    /**
     * after task it time out remove life and show toast
     * after this, start need game
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        int life = _activity.getLifeCount();
        _activity.setLifeCount(life - 1);

        Toast.makeText(_activity, "time out, try again", Toast.LENGTH_SHORT).show();

        _activity.startGame();
    }
}
