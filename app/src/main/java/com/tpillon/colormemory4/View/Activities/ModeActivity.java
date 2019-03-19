package com.tpillon.colormemory4.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.tpillon.ColorMemory4.R;
import com.tpillon.colormemory4.Models.GameSettings.SettingFactory;

public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    /**
     * collect current mode and send to new activity
     */
    public void onStartGameClick(View view) {

        int mode = SettingFactory.EASY_MODE;

        RadioButton button = findViewById(R.id.hardRadioButton);

        if(button.isChecked())
            mode = SettingFactory.HARD_MODE;
        button = findViewById(R.id.expertRadioButton);
        if(button.isChecked())
            mode = SettingFactory.EXPERT_MODE;
        button = findViewById(R.id.chronoRadioButton);
        if(button.isChecked())
            mode = SettingFactory.CHRONO_MODE;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.MODE_KEY, mode);
        startActivity(intent);
    }
}
