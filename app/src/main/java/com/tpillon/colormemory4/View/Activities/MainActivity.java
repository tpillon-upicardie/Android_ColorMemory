package com.tpillon.colormemory4.View.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tpillon.ColorMemory4.R;
import com.tpillon.colormemory4.Logic.GameTask;
import com.tpillon.colormemory4.Logic.TimeoutTask;
import com.tpillon.colormemory4.Models.GameSettings.GameSetting;
import com.tpillon.colormemory4.Models.GameSettings.SettingFactory;
import com.tpillon.colormemory4.View.Fragments.BaseBlocsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * get current mode
     */
    public static final String MODE_KEY = "Mode Key";

    /**
     * current game setting
     */
    private GameSetting _setting;

    /**
     * list of all the fragments can be shown in the view
     */
    private final List<BaseBlocsFragment> _allFragments;

    /**
     * task to light buttons
     */
    private GameTask _gameTask;

    /**
     * current life count
     */
    private int _lifeCount;

    /**
     * current score
     */
    private double _score;

    public MainActivity(){
        _allFragments = new ArrayList<BaseBlocsFragment>();
        //_allFragments.add(new FourButtonsFragment());
        //_allFragments.add(new FiveButtonsFragment());
        //_allFragment.add(new SixButtonsFragment());
        //_allFragment.add(new SevenButtonsFragment());
        //_allFragment.add(new HeightButtonsFragment());
        //_allFragment.add(new NineButtonsFragment());
        //_allFragment.add(new TenButtonsFragment());
        }

//onDestroyed() {
  //  setGameTask(null);
//}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NOTE : get current setting
        Intent intent = getIntent();
        int mode= intent.getIntExtra(MODE_KEY, SettingFactory.EASY_MODE);
        _setting = SettingFactory.GetByIndex(mode);
        _setting.setCurrentBlocsCount(_setting.getMinBlocsCount());
        _setting.setCurrentLightsCount(_setting.getMinLightsCount());

        int index = _setting.getCurrentBlocsCount() -4;
        showFragment(index);
        setScore(0.0);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // start game after view creation
        startGame();
    }

    /**
     * start nedd game
     * start new game task
     */
    public void startGame() {
        int index = _setting.getCurrentBlocsCount() -4;

        BaseBlocsFragment currentFragment = getAllFragments().get(index);
        List<Button> buttons = currentFragment.getAllButtons();

        TimeoutTask timerTask = new TimeoutTask(this);
        GameTask task = new GameTask(buttons, _setting, timerTask);
        task.execute(_setting.getCurrentLightsCount());
        setGameTask(task);
    }

    /**
     * function of all buttons in fragment
     * called when user click on block
     * @param view : button clicked
     */
    public void onButtonClick(View view){
        GameTask gameTask = getGameTask();

        // NOTE : values is empty : do nothing
        List<Integer> values = gameTask.getAllValues();
        if(values.isEmpty()){
            return;
        }

        int buttonIndex = values.get(0);
        Button wanted = gameTask.getAllButtons().get(buttonIndex);

        // NOTE : if user click on good button
        if(view == wanted){
            values.remove(0);

            // NOTE : if last button to click
            //        start new game or add new block
            if(values.isEmpty()) {
                int maxLights =_setting.getMaxLightsCount();
                int currentLights = _setting.getCurrentLightsCount();
                // NOTE : increment light count
                if(currentLights < maxLights) {
                    _setting.setCurrentLightsCount(currentLights +1);
                }
                // NOTE : increment bloc count
                else {
                    int minLights = _setting.getMinLightsCount();
                    int currentBlocs = _setting.getCurrentBlocsCount();

                    _setting.setCurrentLightsCount(minLights);
                    _setting.setCurrentBlocsCount(currentBlocs +1);

                    int fragmentIndex =_setting.getCurrentBlocsCount() -4;
                    // NOTE : if max block count
                    //        show result activity
                    if(fragmentIndex >= _allFragments.size()) {
                        showResultActivity(true);
                        return;
                    }

                    showFragment(fragmentIndex);
                }
                startGame();
                increaseScore();
            }
        }
        // NOTE : user click on bad button
        //       show toast
        //       remove one life
        //       start new game or show result view
        else {
            Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
            setLifeCount(getLifeCount()-1);

            // NOTE : if not dead start new game
            if(getLifeCount() > 0) {
                startGame();
            } else {
                showResultActivity(false);
            }
        }

    }

    /**
     * start new activity to show result
     * @param isWin : inform if game is win or not
     */
    private void showResultActivity(boolean isWin) {
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle extras = new Bundle();

        extras.putBoolean(ResultActivity.IS_WIN_KEY, isWin);
        extras.putDouble(ResultActivity.SCORE_KEY, _score);
        intent.putExtras(extras);

        startActivity(intent);
        finish();
    }

    /**
     * update score
     */
    private void increaseScore() {
        int level = _setting.getCurrentBlocsCount() - 3;
        double value = level * _setting.getScoreMultiplier();

        double newValue = getScore() + value;
        setScore(newValue);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setText(Double.toString(getScore()));
    }

    /**
     * collect fragment in the list by index
     * put the fragment in the view
     * replace the previous if existing
     *
     * @param fragmentIndex index to find fragment in list
     */
    private void showFragment(int fragmentIndex) {
        Fragment fragment = _allFragments.get(fragmentIndex);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .replace(R.id.formFragmentContainer, fragment)
                .commit();
        supportFragmentManager.executePendingTransactions();

        int maxLife = _setting.getMaxLightsCount();
        setLifeCount(maxLife);
    }

    private List<BaseBlocsFragment> getAllFragments() {
        return _allFragments;
    }

    public GameTask getGameTask() {
        return _gameTask;
    }

    public void setGameTask(GameTask gameTask) {
        // cancel previous game
        if(_gameTask != null)
            _gameTask.cancel(true);

        this._gameTask = gameTask;
    }

    public int getLifeCount() {
        return _lifeCount;
    }

    public void setLifeCount(int lifeCount) {
        _lifeCount = lifeCount;

        String text = getResources().getString(R.string.lifeCountText, lifeCount);
        TextView label = findViewById(R.id.lifeLabel);
        label.setText(text);
    }

    public void setTime(Integer value) {
        TextView timeLabel = findViewById(R.id.timeLabel);
        timeLabel.setText(value.toString());
    }

    public double getScore() {
        return _score;
    }

    public void setScore(double _score) {
        this._score = _score;
    }
}
