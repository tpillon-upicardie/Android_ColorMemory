package com.tpillon.colormemory4.Models.GameSettings;

/**
 * setting of a game
 */
public class GameSetting {
    private int  _minLightsCount;
    private int  _maxLightsCount;

    private int  _minBlocsCount;
    private int  _maxBlocsCount;

    private  int _lifeCount;
    private int _timeInSec;


    private int _currentLightsCount;
    private int _currentBlocsCount;

    private double _scoreMultiplier;

    protected GameSetting(){
    }
    public int getMinLightsCount() {
        return _minLightsCount;
    }

    public void setMinLightsCount(int _minLightsCount) {
        this._minLightsCount = _minLightsCount;
    }

    public int getMinBlocsCount() {
        return _minBlocsCount;
    }

    public void setMinBlocsCount(int _minBlocsCount) {
        this._minBlocsCount = _minBlocsCount;
    }

    public int getMaxLightsCount() {
        return _maxLightsCount;
    }

    public void setMaxLightsCount(int _maxLightsCount) {
        this._maxLightsCount = _maxLightsCount;
    }

    public int getMaxBlocsCount() {
        return _maxBlocsCount;
    }

    public void setMaxBlocsCount(int _maxBlocsCount) {
        this._maxBlocsCount = _maxBlocsCount;
    }

    public int getTimeInSec() {
        return _timeInSec;
    }

    public void setTimeInSec(int _timeInSec) {
        this._timeInSec = _timeInSec;
    }

    public int getLifeCount() {
        return _lifeCount;
    }

    public void setLifeCount(int _lifeCount) {
        this._lifeCount = _lifeCount;
    }

    public int getCurrentLightsCount() {
        return _currentLightsCount;
    }

    public void setCurrentLightsCount(int _currentLightsCount) {
        this._currentLightsCount = _currentLightsCount;
    }

    public int getCurrentBlocsCount() {
        return _currentBlocsCount;
    }

    public void setCurrentBlocsCount(int _currentBlocsCount) {
        this._currentBlocsCount = _currentBlocsCount;
    }

    public double getScoreMultiplier() {
        return _scoreMultiplier;
    }

    public void setScoreMultiplier(double _scoreMultiplier) {
        this._scoreMultiplier = _scoreMultiplier;
    }
}
