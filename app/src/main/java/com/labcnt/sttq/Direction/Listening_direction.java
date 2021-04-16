package com.labcnt.sttq.Direction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.labcnt.sttq.Final_score;
import com.labcnt.sttq.Listening;
import com.labcnt.sttq.R;

public class Listening_direction extends AppCompatActivity {

    Button agree;
    private TextView tvNIM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_direction);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//BLOCK SCREENSHOOT DAN RECORDER
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //SELALU POTRAIT
        agree = findViewById(R.id.agree);
        tvNIM = findViewById(R.id.Nim);

        tvNIM.setText(getIntent().getStringExtra("name"));
        onClick();
    }

    private void onClick() {
        agree.setOnClickListener(v -> {
            String NIM = tvNIM.getText().toString();
            Intent intent = new Intent(Listening_direction.this, Listening.class);
            intent.putExtra("NIM", NIM);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}