package com.labcnt.sttq.Direction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.labcnt.sttq.Listening;
import com.labcnt.sttq.R;
import com.labcnt.sttq.Reading;

public class Reading_direction extends AppCompatActivity {

    Button agree;
    private TextView mScoreTextViewL,mScoreTextViewS;
    private TextView tvNIM, tvScoreListening, tvScoreStructure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_direction);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//BLOCK SCREENSHOOT DAN RECORDER
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //SELALU POTRAIT
        agree = findViewById(R.id.agree);
        mScoreTextViewL = findViewById(R.id.scoreL);
        mScoreTextViewS = findViewById(R.id.scoreS);
        tvNIM = findViewById(R.id.Nim);
        tvScoreListening = findViewById(R.id.scoreL);
        tvScoreStructure = findViewById(R.id.scoreS);

        tvNIM.setText(getIntent().getStringExtra("NIM"));
        tvScoreListening.setText(getIntent().getStringExtra("scoreListening"));
        tvScoreStructure.setText(getIntent().getStringExtra("scoreStructure"));

        onClick();
    }

    private void onClick() {
        agree.setOnClickListener(v -> {

            String NIM = tvNIM.getText().toString();
            String scoreListening = mScoreTextViewL.getText().toString();
            String scoreStructure = mScoreTextViewS.getText().toString();
            Intent intent = new Intent(Reading_direction.this, Reading.class);
            intent.putExtra("NIM", NIM);
            intent.putExtra("scoreListening", scoreListening);
            intent.putExtra("scoreStructure", scoreStructure);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}