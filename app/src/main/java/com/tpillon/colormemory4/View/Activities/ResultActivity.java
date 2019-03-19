package com.tpillon.colormemory4.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tpillon.ColorMemory4.R;

public class ResultActivity extends AppCompatActivity {

    public static final String SCORE_KEY = "Score Key";
    public static final String IS_WIN_KEY = "Is Win Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle extra =intent.getExtras();
        double score = extra.getDouble(SCORE_KEY, -1);
        boolean isWin = extra.getBoolean(IS_WIN_KEY, false);

        TextView scoreLabel= findViewById(R.id.scoreLabel);
        scoreLabel.setText(Double.toString(score));

        TextView stateLabel= findViewById(R.id.stateLabel);
        if(isWin) {
            stateLabel.setText("congrat, you finish the mode !");
        } else {
            stateLabel.setText("Fail, try again !");
        }
    }

    public void onReturnClick(View view) {
        onBackPressed();
    }
}
