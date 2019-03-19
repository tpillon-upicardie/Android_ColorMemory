package com.tpillon.colormemory4.Models.GameSettings;

/**
 * factory to create setting of game
 */
public class SettingFactory {

    public static final int EASY_MODE = 0;
    public static final int CHRONO_MODE = 1;
    public static final int EXPERT_MODE = 2;
    public static final int HARD_MODE = 3;

    public static GameSetting getEasyMode(){

        GameSetting setting = new GameSetting();
        setting.setMinBlocsCount(4);
        setting.setMaxBlocsCount(10);

        setting.setMinLightsCount(1);
        setting.setMaxLightsCount(3);

        setting.setLifeCount(3);
        setting.setTimeInSec(-1);
        setting.setScoreMultiplier(1);

        return setting;
    }


    public static  GameSetting getHardMode(){

        GameSetting setting = new GameSetting();
        setting.setMinBlocsCount(4);
        setting.setMaxBlocsCount(10);

        setting.setMinLightsCount(3);
        setting.setMaxLightsCount(8);

        setting.setLifeCount(3);
        setting.setTimeInSec(-1);
        setting.setScoreMultiplier(1.5);

        return setting;
    }


    public static GameSetting getExpertMode(){

        GameSetting setting = new GameSetting();
        setting.setMinBlocsCount(4);
        setting.setMaxBlocsCount(10);

        setting.setMinLightsCount(4);
        setting.setMaxLightsCount(10);

        setting.setLifeCount(3);
        setting.setTimeInSec(-1);
        setting.setScoreMultiplier(2);

        return setting;
    }



    public static GameSetting getChronoMode(){

        GameSetting setting = new GameSetting();
        setting.setMinBlocsCount(4);
        setting.setMaxBlocsCount(10);

        setting.setMinLightsCount(1);
        setting.setMaxLightsCount(6);

        setting.setLifeCount(3);
        setting.setTimeInSec(2);
        setting.setScoreMultiplier(1.5);

        return setting;
    }

    public static GameSetting GetByIndex(int mode) {
        switch (mode){
            case EASY_MODE:
                return getEasyMode();
            case HARD_MODE:
                return  getHardMode();
            case EXPERT_MODE:
                return getExpertMode();
            case CHRONO_MODE:
                return  getChronoMode();
            default:
                return getEasyMode();
        }
    }
}
