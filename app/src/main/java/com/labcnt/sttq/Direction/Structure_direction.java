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
import com.labcnt.sttq.Structure;

public class Structure_direction extends AppCompatActivity {

    Button agree;
    private TextView mScoreTextViewL;
    private TextView tvNIM, tvScoreListening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure_direction);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//BLOCK SCREENSHOOT DAN RECORDER
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //SELALU POTRAIT
        agree = findViewById(R.id.agree);
        mScoreTextViewL = findViewById(R.id.scoreL);
        tvNIM = findViewById(R.id.Nim);
        tvScoreListening = findViewById(R.id.ScoreL);

        tvNIM.setText(getIntent().getStringExtra("NIM"));
        tvScoreListening.setText(getIntent().getStringExtra("scoreListening"));


        onClick();
    }

    private void onClick() {
        agree.setOnClickListener(v -> {

            String NIM = tvNIM.getText().toString();
            String scoreListening = tvScoreListening.getText().toString();
            Intent intent = new Intent(Structure_direction.this, Structure.class);
            intent.putExtra("NIM", NIM);
            intent.putExtra("scoreListening", scoreListening);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}