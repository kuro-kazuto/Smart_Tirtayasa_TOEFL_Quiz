package com.labcnt.sttq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Final_score extends AppCompatActivity {
    private TextView tvFinalScoreL, tvNIM, tvFinalScoreR, tvFinalScoreS, tvFinalScoreT;
    private Button btnFinishTest;
    DatabaseReference databaseReference;

    double L;
    double Re;
    double S;
    double T;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalscore);

        databaseReference = FirebaseDatabase.getInstance().getReference("Score");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvFinalScoreL = findViewById(R.id.scoreL);
        tvFinalScoreR = findViewById(R.id.scoreR);
        tvFinalScoreS = findViewById(R.id.scoreS);
        btnFinishTest = findViewById(R.id.button);
        tvNIM = findViewById(R.id.NIM);
        tvFinalScoreT = findViewById(R.id.scoreT);


        tvNIM.setText(getIntent().getStringExtra("NIM"));
        tvFinalScoreL.setText("Final " + getIntent().getStringExtra("scoreListening"));
        tvFinalScoreR.setText("Final " + getIntent().getStringExtra("scoreReading"));
        tvFinalScoreS.setText("Final " + getIntent().getStringExtra("scoreStructure"));

        NilaiL();
        NilaiRe();
        NilaiS();

        T = ((L + S + Re)*10)/3;
        DecimalFormat df = new DecimalFormat("#");
        String hasil = df.format(T);

        tvFinalScoreT.setText("TOEFL SCORE: " + hasil);

        btnFinishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final_score.this, Splash_exit.class);
                startActivity(intent);
                updateDatabase();
            }
        });

        updateDatabase();

    }



    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(Final_score.this,"Your Test Is Finish, Please Click The Button For Update Score And Exit  ",Toast.LENGTH_SHORT).show();
        return;
    }

    private void updateDatabase() {
        String userName = tvNIM.getText().toString().trim();
        String scoreListening = tvFinalScoreL.getText().toString().trim();
        String scoreReading = tvFinalScoreR.getText().toString().trim();
        String scoreStructure = tvFinalScoreS.getText().toString().trim();
        String scoreTOEFL = tvFinalScoreT.getText().toString().trim();

        String id = databaseReference.push().getKey();

        Score user = new Score(id, userName, scoreListening, scoreStructure, scoreReading, scoreTOEFL);

        databaseReference.child(user.getUserName()).setValue(user);
    }

    private void NilaiS() {
        String NS = tvFinalScoreS.getText().toString();

        switch (NS){
            case "Final Score Structure: 0":
                S = 20;
                break;
            case "Final Score Structure: 1":
                S = 20;
                break;
            case "Final Score Structure: 2":
                S = 21;
                break;
            case "Final Score Structure: 3":
                S = 22;
                break;
            case "Final Score Structure: 4":
                S = 23;
                break;
            case "Final Score Structure: 5":
                S = 25;
                break;
            case "Final Score Structure: 6":
                S = 26;
                break;
            case "Final Score Structure: 7":
                S = 27;
                break;
            case "Final Score Structure: 8":
                S = 29;
                break;
            case "Final Score Structure: 9":
                S = 31;
                break;
            case "Final Score Structure: 10":
                S = 33;
                break;
            case "Final Score Structure: 11":
                S = 35;
                break;
            case "Final Score Structure: 12":
                S = 36;
                break;
            case "Final Score Structure: 13":
                S = 37;
                break;
            case "Final Score Structure: 14":
                S = 38;
                break;
            case "Final Score Structure: 15":
                S = 40;
                break;
            case "Final Score Structure: 16":
                S = 40;
                break;
            case "Final Score Structure: 17":
                S = 41;
                break;
            case "Final Score Structure: 18":
                S = 42;
                break;
            case "Final Score Structure: 19":
                S = 43;
                break;
            case "Final Score Structure: 20":
                S = 44;
                break;
            case "Final Score Structure: 21":
                S = 45;
                break;
            case "Final Score Structure: 22":
                S = 46;
                break;
            case "Final Score Structure: 23":
                S = 47;
                break;
            case "Final Score Structure: 24":
                S = 48;
                break;
            case "Final Score Structure: 25":
                S = 49;
                break;
            case "Final Score Structure: 26":
                S = 50;
                break;
            case "Final Score Structure: 27":
                S = 51;
                break;
            case "Final Score Structure: 28":
                S = 52;
                break;
            case "Final Score Structure: 29":
                S = 53;
                break;
            case "Final Score Structure: 30":
                S = 54;
                break;
            case "Final Score Structure: 31":
                S = 55;
                break;
            case "Final Score Structure: 32":
                S = 56;
                break;
            case "Final Score Structure: 33":
                S = 57;
                break;
            case "Final Score Structure: 34":
                S = 58;
                break;
            case "Final Score Structure: 35":
                S = 60;
                break;
            case "Final Score Structure: 36":
                S = 61;
                break;
            case "Final Score Structure: 37":
                S = 63;
                break;
            case "Final Score Structure: 38":
                S = 65;
                break;
            case "Final Score Structure: 39":
                S = 67;
                break;
            case "Final Score Structure: 40":
                S = 68;
                break;
        }
    }

    private void NilaiRe() {
        String NR = tvFinalScoreR.getText().toString();

        switch (NR){
            case "Final Score Reading: 0":
                Re = 22;
                break;
            case "Final Score Reading: 1":
                Re = 21;
                break;
            case "Final Score Reading: 2":
                Re = 22;
                break;
            case "Final Score Reading: 3":
                Re = 23;
                break;
            case "Final Score Reading: 4":
                Re = 23;
                break;
            case "Final Score Reading: 5":
                Re = 24;
                break;
            case "Final Score Reading: 6":
                Re = 25;
                break;
            case "Final Score Reading: 7":
                Re = 26;
                break;
            case "Final Score Reading: 8":
                Re = 28;
                break;
            case "Final Score Reading: 9":
                Re = 28;
                break;
            case "Final Score Reading: 10":
                Re = 29;
                break;
            case "Final Score Reading: 11":
                Re = 30;
                break;
            case "Final Score Reading: 12":
                Re = 31;
                break;
            case "Final Score Reading: 13":
                Re = 32;
                break;
            case "Final Score Reading: 14":
                Re = 34;
                break;
            case "Final Score Reading: 15":
                Re = 35;
                break;
            case "Final Score Reading: 16":
                Re = 36;
                break;
            case "Final Score Reading: 17":
                Re = 37;
                break;
            case "Final Score Reading: 18":
                Re = 38;
                break;
            case "Final Score Reading: 19":
                Re = 39;
                break;
            case "Final Score Reading: 20":
                Re = 40;
                break;
            case "Final Score Reading: 21":
                Re = 41;
                break;
            case "Final Score Reading: 22":
                Re = 42;
                break;
            case "Final Score Reading: 23":
                Re = 43;
                break;
            case "Final Score Reading: 24":
                Re = 43;
                break;
            case "Final Score Reading: 25":
                Re = 44;
                break;
            case "Final Score Reading: 26":
                Re = 45;
                break;
            case "Final Score Reading: 27":
                Re = 46;
                break;
            case "Final Score Reading: 28":
                Re = 46;
                break;
            case "Final Score Reading: 29":
                Re = 47;
                break;
            case "Final Score Reading: 30":
                Re = 48;
                break;
            case "Final Score Reading: 31":
                Re = 48;
                break;
            case "Final Score Reading: 32":
                Re = 49;
                break;
            case "Final Score Reading: 33":
                Re = 50;
                break;
            case "Final Score Reading: 34":
                Re = 51;
                break;
            case "Final Score Reading: 35":
                Re = 52;
                break;
            case "Final Score Reading: 36":
                Re = 52;
                break;
            case "Final Score Reading: 37":
                Re = 53;
                break;
            case "Final Score Reading: 38":
                Re = 54;
                break;
            case "Final Score Reading: 39":
                Re = 54;
                break;
            case "Final Score Reading: 40":
                Re = 55;
                break;
            case "Final Score Reading: 41":
                Re = 56;
                break;
            case "Final Score Reading: 42":
                Re = 57;
                break;
            case "Final Score Reading: 43":
                Re = 58;
                break;
            case "Final Score Reading: 44":
                Re = 59;
                break;
            case "Final Score Reading: 45":
                Re = 60;
                break;
            case "Final Score Reading: 46":
                Re = 61;
                break;
            case "Final Score Reading: 47":
                Re = 63;
                break;
            case "Final Score Reading: 48":
                Re = 65;
                break;
            case "Final Score Reading: 49":
                Re = 66;
                break;
            case "Final Score Reading: 50":
                Re = 67;
                break;
        }
    }

    private void NilaiL() {
        String NL = tvFinalScoreL.getText().toString();

        switch (NL){
            case "Final Score Listening: 0":
                L = 24;
                break;
            case "Final Score Listening: 1":
                L = 25;
                break;
            case "Final Score Listening: 2":
                L = 26;
                break;
            case "Final Score Listening: 3":
                L = 27;
                break;
            case "Final Score Listening: 4":
                L = 28;
                break;
            case "Final Score Listening: 5":
                L = 29;
                break;
            case "Final Score Listening: 6":
                L = 30;
                break;
            case "Final Score Listening: 7":
                L = 31;
                break;
            case "Final Score Listening: 8":
                L = 32;
                break;
            case "Final Score Listening: 9":
                L = 32;
                break;
            case "Final Score Listening: 10":
                L = 33;
                break;
            case "Final Score Listening: 11":
                L = 35;
                break;
            case "Final Score Listening: 12":
                L = 37;
                break;
            case "Final Score Listening: 13":
                L = 37;
                break;
            case "Final Score Listening: 14":
                L = 38;
                break;
            case "Final Score Listening: 15":
                L = 41;
                break;
            case "Final Score Listening: 16":
                L = 41;
                break;
            case "Final Score Listening: 17":
                L = 42;
                break;
            case "Final Score Listening: 18":
                L = 43;
                break;
            case "Final Score Listening: 19":
                L = 44;
                break;
            case "Final Score Listening: 20":
                L = 45;
                break;
            case "Final Score Listening: 21":
                L = 45;
                break;
            case "Final Score Listening: 22":
                L = 46;
                break;
            case "Final Score Listening: 23":
                L = 47;
                break;
            case "Final Score Listening: 24":
                L = 47;
                break;
            case "Final Score Listening: 25":
                L = 48;
                break;
            case "Final Score Listening: 26":
                L = 48;
                break;
            case "Final Score Listening: 27":
                L = 49;
                break;
            case "Final Score Listening: 28":
                L = 49;
                break;
            case "Final Score Listening: 29":
                L = 50;
                break;
            case "Final Score Listening: 30":
                L = 51;
                break;
            case "Final Score Listening: 31":
                L = 51;
                break;
            case "Final Score Listening: 32":
                L = 52;
                break;
            case "Final Score Listening: 33":
                L = 52;
                break;
            case "Final Score Listening: 34":
                L = 53;
                break;
            case "Final Score Listening: 35":
                L = 54;
                break;
            case "Final Score Listening: 36":
                L = 54;
                break;
            case "Final Score Listening: 37":
                L = 55;
                break;
            case "Final Score Listening: 38":
                L = 56;
                break;
            case "Final Score Listening: 39":
                L = 57;
                break;
            case "Final Score Listening: 40":
                L = 57;
                break;
            case "Final Score Listening: 41":
                L = 58;
                break;
            case "Final Score Listening: 42":
                L = 59;
                break;
            case "Final Score Listening: 43":
                L = 60;
                break;
            case "Final Score Listening: 44":
                L = 61;
                break;
            case "Final Score Listening: 45":
                L = 62;
                break;
            case "Final Score Listening: 46":
                L = 63;
                break;
            case "Final Score Listening: 47":
                L = 65;
                break;
            case "Final Score Listening: 48":
                L = 66;
                break;
            case "Final Score Listening: 49":
                L = 67;
                break;
            case "Final Score Listening: 50":
                L = 68;
                break;
        }
    }


}