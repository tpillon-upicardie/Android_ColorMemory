package com.tpillon.colormemory4.Logic;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.Button;

import com.tpillon.ColorMemory4.R;
import com.tpillon.colormemory4.Models.GameSettings.GameSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTask extends AsyncTask<Integer,Integer,Void> {

    private static final int DEFAULT_COLOR = R.color.grayButton;
    /**
     * list with all colors for buttons
     */
    private static final  List<Integer> ALL_COLOR_IDS = new ArrayList<Integer>(){
        {
            add(R.color.blueButton);
            add(R.color.redButton);
            add(R.color.greenButton);
            add(R.color.yellowButton);
            add(R.color.purpleButton);
        }
    };

    /**
     * all buttons in the current fragment
     */
    private  final List<Button> _allButtons ;
    /**
     * all values shown
     * the values are all indexs of buttons in _allButtons
     * the indexs can be also used to collect colors in ALL_COLOR_IDS
     * values are removed one by one after good touch of button from the user
     */
    private final  List<Integer> _allValues = new ArrayList<>();

    /**
     * setting of the current game
     */
    private final GameSetting _setting;

    /**
     * task to manage timeout
     * if time in setting is upper than 0
     * this task is run during each user waiting
     */
    private final TimeoutTask  _taskOnTimeOut;

    /**
     * random to create random new value
     */
    private final Random _random;

    /**
     * ctor
     * @param allButtons : all buttons from the fragment
     * @param setting setting of the game
     * @param taskOnTimeOut task to run if timeout is set
     */
    public  GameTask(List<Button> allButtons, GameSetting setting, TimeoutTask taskOnTimeOut) {
        _allButtons = allButtons;
        _setting = setting;
        _taskOnTimeOut = taskOnTimeOut;
        _random = new Random();

    }

    /**
     * desactive all buttons before shown the wanted lights
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        desactiveAllButtons();
    }

    /**
     * background process to light random buttons
     * collect random value and put it in _allValues
     * light the associated button
     * wait time for user can see the light
     * recursive call if other light is wanted
     * @param lightCount : value [0] contains the count of light wanted
     */
    @Override
    protected Void doInBackground(Integer... lightCount) {

        int value = collectNewValue();
        // NOTE : apply light with UI thread
        publishProgress(value);

        // NOTE : wait time for user can see the light
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            // TODO
        }

        // NOTE : recursive call if other light is wanted
        int count = lightCount[0]- 1;
        if(count >0)
            doInBackground(count);

        // NOTE : default return because we are in AsyncTask
        //        so not use "void" but "Void" => so need return null;
        return  null;
    }

    /**
     * apply light with UI thread
     * get associated buttons and color
     * set background of the button with the color
     * put value in global values list
     * @param allValues : [0] index of the button and color in associated lists
     */
    @Override
    protected void onProgressUpdate(Integer... allValues) {
        super.onProgressUpdate(allValues);

        // NOTE : if previous buttons is shined
        //         switchOff it
        boolean havePrevious = getAllValues().isEmpty() == false;
        if(havePrevious) {
            int lastIndex = getAllValues().size() -1;
            int lastButton = getAllValues().get(lastIndex);
            switchOff(lastButton);
        }


        int value = allValues[0];
        light(value);
        getAllValues().add(value);
    }

    /**
     * after process active all buttons
     * apply timeout if needed
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        activeAllButtons();

        int time = _setting.getTimeInSec();
        if(time != -1){
            int timeoutInSec= time* _setting.getCurrentLightsCount();
            _taskOnTimeOut.execute(timeoutInSec);
        }
    }

    /**
     * function to cancel game
     * cancel timeout task too
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(_taskOnTimeOut != null) {
            _taskOnTimeOut.cancel(true);
        }
    }

    /**
     * swith off all buttons
     */
    public void desactiveAllButtons() {
        for (int index = 0; index<getAllButtons().size(); index++) {
            switchOff(index);
            disabled(index);
        }
    }


    /**
     * active all buttons
     */
    public void activeAllButtons() {
        for (int index = 0; index<getAllButtons().size(); index++) {
            light(index);
            enabled(index);
        }
    }

    /**
     * collect random value to swith on new buttons
     * check new value is different than previous
     * @return result can be used to collect button or color
     */
    private int collectNewValue() {
        int value;

        boolean isSameThanLast;
        int buttonsCount = getAllButtons().size();

        do {
            value = _random.nextInt(buttonsCount);
            isSameThanLast = getAllValues().size() != 0
                    && value == getAllValues().get(getAllValues().size()-1);
        }while (isSameThanLast);
        return value;
    }

    /**
     * enabled button allow user click to launch function
     * @param index of the wanted button in list
     */
    private void enabled(int index) {
        Button button =_allButtons.get(index);
        button.setEnabled(true);
    }

    /**
     * disabled button avoid to user click launch function
     * @param index of the wanted button in list
     */
    private void disabled(int index) {
        Button button =_allButtons.get(index);
        button.setEnabled(false);
    }

    /**
     * switch on wanted button
     * collect color in ALL_COLOR_IDS
     * @param value of the wanted button in list
     */
    private void light(int value){
        Button button = getAllButtons().get(value);
        int id = ALL_COLOR_IDS.get(value);
        int color = button.getResources().getColor(id);
        button.setBackgroundColor(color);

    }

    /**
     * switch off wanted button
     * @param value of the wanted button in list
     */
    private void switchOff(int value){

        Button lastButton = getAllButtons().get(value);
        Resources resources = lastButton.getResources();
        int gray = resources.getColor(DEFAULT_COLOR);
        lastButton.setBackgroundColor(gray);
    }

    public List<Integer> getAllValues(){
        return _allValues;
    }

    public List<Button> getAllButtons(){
        return _allButtons;
    }
}
