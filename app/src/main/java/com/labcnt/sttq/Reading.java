package com.labcnt.sttq;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.labcnt.sttq.Adapter.ReadingAdapter;
import com.labcnt.sttq.Model.ReadingModel;
import com.labcnt.sttq.Passage.Passage1;
import com.labcnt.sttq.Passage.Passage2;
import com.labcnt.sttq.Passage.Passage3;
import com.labcnt.sttq.Passage.Passage4;
import com.labcnt.sttq.Passage.Passage5;

import java.util.ArrayList;


public class Reading extends AppCompatActivity {



//==================================================================================================
//============ INI ADALAH BAGIAN PROGRAM, JANGAN DIUBAH KECUALI OLEH DEVELOPER =====================
//================== DIBUAT OLEH : GALIH AJI PAMBUDI & DIMAS EMERALDO A ============================
//============= PROJECT KERJA PRAKTIK TAHUN 2021 JURUSAN TEKNIK ELEKTRO FT UNTIRTA =================
//==================================================================================================
    private RecyclerView mRecyclerView;
    private ReadingAdapter mAdapter;

    private ConstraintLayout mParentLayout;
    private TextView mScoreTextViewL, mScoreTextViewS, mScoreTextViewR, tvTimer;
    private TextView mRemaningQuestionsTextView;
    private TextView tvNIM;
    private int mTotalQuestions;
    private int mScore;
    private ReadingModel currentQuestion;

    Button btnNext, btnText;
    CountDownTimer countDownTimer;

    private ArrayList<ReadingModel> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Reading");
        }

        btnText = findViewById(R.id.btnText);
       // btnText.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
         //       Intent pindahRead = new Intent(Reading.this, Text.class);
         //       startActivity(pindahRead);
          //  }
       // });

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NIM = tvNIM.getText().toString();
                String scoreListening = mScoreTextViewL.getText().toString();
                String scoreStructure = mScoreTextViewS.getText().toString();
                String scoreReading = mScoreTextViewR.getText().toString();
                Intent intent = new Intent(Reading.this, Final_score.class);
                intent.putExtra("NIM", NIM);
                intent.putExtra("scoreListening", scoreListening);
                intent.putExtra("scoreStructure", scoreStructure);
                intent.putExtra("scoreReading", scoreReading);
                startActivity(intent);
                countDownTimer.cancel();
            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Reading.this);

                //Memasang Title / Judul pada Custom Dialog
                dialog.setTitle("Reading Text");

                //Memasang Desain Layout untuk Custom Dialog
                dialog.setContentView(R.layout.dialog_layout);

                Button P1 = dialog.findViewById(R.id.passage1);
                Button P2 = dialog.findViewById(R.id.passage2);
                Button P3 = dialog.findViewById(R.id.passage3);
                Button P4 = dialog.findViewById(R.id.passage4);
                Button P5 = dialog.findViewById(R.id.passage5);

                P1.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    Intent p1 = new Intent(Reading.this, Passage1.class);
                    startActivity(p1);
                });
                P2.setOnClickListener(v12 -> {
                    dialog.dismiss();
                    Intent p2 = new Intent(Reading.this, Passage2.class);
                    startActivity(p2);
                });
                P3.setOnClickListener(v13 -> {
                    dialog.dismiss();
                    Intent p3 = new Intent(Reading.this, Passage3.class);
                    startActivity(p3);
                });
                P4.setOnClickListener(v14 -> {
                    dialog.dismiss();
                    Intent p4 = new Intent(Reading.this, Passage4.class);
                    startActivity(p4);
                });
                P5.setOnClickListener(v15 -> {
                    dialog.dismiss();
                    Intent p5 = new Intent(Reading.this, Passage5.class);
                    startActivity(p5);
                });



                dialog.show();
            }
        });


        addQuestion();


        tvNIM = findViewById(R.id.Nim);
        mScoreTextViewL = findViewById(R.id.scoreL);
        mScoreTextViewS = findViewById(R.id.scoreS);
        mScoreTextViewR = findViewById(R.id.scoreR);
        mParentLayout = findViewById(R.id.question_layout);
        mRemaningQuestionsTextView = findViewById(R.id.QuestionNumber);
        tvTimer = findViewById(R.id.Timer);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ReadingAdapter(this,questionsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Reading.this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalQuestions = questionsList.size();
        mScore = 0;
        displayScore();

        tvNIM.setText(getIntent().getStringExtra("NIM"));
        mScoreTextViewL.setText(getIntent().getStringExtra("scoreListening"));
        mScoreTextViewS.setText(getIntent().getStringExtra("scoreStructure"));

        timer();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //Toast.makeText(Listening.this,"You Click Back Button !, Are You Want To Cheating ?",Toast.LENGTH_SHORT).show();
        return;
    }

    public void displayScore() {
        String scoreString = "Score Reading: " + mScore;
        mScoreTextViewR.setText(scoreString);
        mRemaningQuestionsTextView.setText("Remaining Questions: " + mTotalQuestions--);
    }

    private void timer() {
        int milidetik = 1000; //jangan diubah
        int detik = 60; //jangan diubah
        int menit = 55;
        countDownTimer = new CountDownTimer(menit * detik * milidetik, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished/1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                String NIM = tvNIM.getText().toString();
                String scoreListening = mScoreTextViewL.getText().toString();
                String scoreStructure = mScoreTextViewS.getText().toString();
                String scoreReading = mScoreTextViewR.getText().toString();
                Intent intent = new Intent(Reading.this, Final_score.class);
                intent.putExtra("NIM", NIM);
                intent.putExtra("scoreListening", scoreListening);
                intent.putExtra("scoreStructure", scoreStructure);
                intent.putExtra("scoreReading", scoreReading);
                startActivity(intent);
            }
        }.start();
    }

    public void updateScore() {

        mScore++;
    }

    //--------------------------BANK SOAL READING SECTION (ISI SESUAI ARRAY)--------------------------
    private void addQuestion(){
        String A1 = getString(R.string.RA1); String B1 = getString(R.string.RB1); String C1 = getString(R.string.RC1); String D1 = getString(R.string.RD1); String CorrectAnsNo1 = getString(R.string.RAns1);//Nomer 1
        String A2 = getString(R.string.RA2); String B2 = getString(R.string.RB2); String C2 = getString(R.string.RC2); String D2 = getString(R.string.RD2); String CorrectAnsNo2 = getString(R.string.RAns2);//Nomer 2
        String A3 = getString(R.string.RA3); String B3 = getString(R.string.RB3); String C3 = getString(R.string.RC3); String D3 = getString(R.string.RD3); String CorrectAnsNo3 = getString(R.string.RAns3);//Nomer 3
        String A4 = getString(R.string.RA4); String B4 = getString(R.string.RB4); String C4 = getString(R.string.RC4); String D4 = getString(R.string.RD4); String CorrectAnsNo4 = getString(R.string.RAns4);//Nomer 4
        String A5 = getString(R.string.RA5); String B5 = getString(R.string.RB5); String C5 = getString(R.string.RC5); String D5 = getString(R.string.RD5); String CorrectAnsNo5 = getString(R.string.RAns5);//Nomer 5
        String A6 = getString(R.string.RA6); String B6 = getString(R.string.RB6); String C6 = getString(R.string.RC6); String D6 = getString(R.string.RD6); String CorrectAnsNo6 = getString(R.string.RAns6);//Nomer 6
        String A7 = getString(R.string.RA7); String B7 = getString(R.string.RB7); String C7 = getString(R.string.RC7); String D7 = getString(R.string.RD7); String CorrectAnsNo7 = getString(R.string.RAns7);//Nomer 7
        String A8 = getString(R.string.RA8); String B8 = getString(R.string.RB8); String C8 = getString(R.string.RC8); String D8 = getString(R.string.RD8); String CorrectAnsNo8 = getString(R.string.RAns8);//Nomer 8
        String A9 = getString(R.string.RA9); String B9 = getString(R.string.RB9); String C9 = getString(R.string.RC9); String D9 = getString(R.string.RD9); String CorrectAnsNo9 = getString(R.string.RAns9);//Nomer 9
        String A10 = getString(R.string.RA10); String B10 = getString(R.string.RB10); String C10 = getString(R.string.RC10); String D10 = getString(R.string.RD10); String CorrectAnsNo10 = getString(R.string.RAns10);//Nomer 10
        String A11 = getString(R.string.RA11); String B11 = getString(R.string.RB11); String C11 = getString(R.string.RC11); String D11 = getString(R.string.RD11); String CorrectAnsNo11 = getString(R.string.RAns11);//Nomer 11
        String A12 = getString(R.string.RA12); String B12 = getString(R.string.RB12); String C12 = getString(R.string.RC12); String D12 = getString(R.string.RD12); String CorrectAnsNo12 = getString(R.string.RAns12);//Nomer 12
        String A13 = getString(R.string.RA13); String B13 = getString(R.string.RB13); String C13 = getString(R.string.RC13); String D13 = getString(R.string.RD13); String CorrectAnsNo13 = getString(R.string.RAns13);//Nomer 13
        String A14 = getString(R.string.RA14); String B14 = getString(R.string.RB14); String C14 = getString(R.string.RC14); String D14 = getString(R.string.RD14); String CorrectAnsNo14 = getString(R.string.RAns14);//Nomer 14
        String A15 = getString(R.string.RA15); String B15 = getString(R.string.RB15); String C15 = getString(R.string.RC15); String D15 = getString(R.string.RD15); String CorrectAnsNo15 = getString(R.string.RAns15);//Nomer 15
        String A16 = getString(R.string.RA16); String B16 = getString(R.string.RB16); String C16 = getString(R.string.RC16); String D16 = getString(R.string.RD16); String CorrectAnsNo16 = getString(R.string.RAns16);//Nomer 16
        String A17 = getString(R.string.RA17); String B17 = getString(R.string.RB17); String C17 = getString(R.string.RC17); String D17 = getString(R.string.RD17); String CorrectAnsNo17 = getString(R.string.RAns17);//Nomer 17
        String A18 = getString(R.string.RA18); String B18 = getString(R.string.RB18); String C18 = getString(R.string.RC18); String D18 = getString(R.string.RD18); String CorrectAnsNo18 = getString(R.string.RAns18);//Nomer 18
        String A19 = getString(R.string.RA19); String B19 = getString(R.string.RB19); String C19 = getString(R.string.RC19); String D19 = getString(R.string.RD19); String CorrectAnsNo19 = getString(R.string.RAns19);//Nomer 19
        String A20 = getString(R.string.RA20); String B20 = getString(R.string.RB20); String C20 = getString(R.string.RC20); String D20 = getString(R.string.RD20); String CorrectAnsNo20 = getString(R.string.RAns20);//Nomer 20
        String A21 = getString(R.string.RA21); String B21 = getString(R.string.RB21); String C21 = getString(R.string.RC21); String D21 = getString(R.string.RD21); String CorrectAnsNo21 = getString(R.string.RAns21);//Nomer 21
        String A22 = getString(R.string.RA22); String B22 = getString(R.string.RB22); String C22 = getString(R.string.RC22); String D22 = getString(R.string.RD22); String CorrectAnsNo22 = getString(R.string.RAns22);//Nomer 22
        String A23 = getString(R.string.RA23); String B23 = getString(R.string.RB23); String C23 = getString(R.string.RC23); String D23 = getString(R.string.RD23); String CorrectAnsNo23 = getString(R.string.RAns23);//Nomer 23
        String A24 = getString(R.string.RA24); String B24 = getString(R.string.RB24); String C24 = getString(R.string.RC24); String D24 = getString(R.string.RD24); String CorrectAnsNo24 = getString(R.string.RAns24);//Nomer 24
        String A25 = getString(R.string.RA25); String B25 = getString(R.string.RB25); String C25 = getString(R.string.RC25); String D25 = getString(R.string.RD25); String CorrectAnsNo25 = getString(R.string.RAns25);//Nomer 25
        String A26 = getString(R.string.RA26); String B26 = getString(R.string.RB26); String C26 = getString(R.string.RC26); String D26 = getString(R.string.RD26); String CorrectAnsNo26 = getString(R.string.RAns26);//Nomer 26
        String A27 = getString(R.string.RA27); String B27 = getString(R.string.RB27); String C27 = getString(R.string.RC27); String D27 = getString(R.string.RD27); String CorrectAnsNo27 = getString(R.string.RAns27);//Nomer 27
        String A28 = getString(R.string.RA28); String B28 = getString(R.string.RB28); String C28 = getString(R.string.RC28); String D28 = getString(R.string.RD28); String CorrectAnsNo28 = getString(R.string.RAns28);//Nomer 28
        String A29 = getString(R.string.RA29); String B29 = getString(R.string.RB29); String C29 = getString(R.string.RC29); String D29 = getString(R.string.RD29); String CorrectAnsNo29 = getString(R.string.RAns29);//Nomer 29
        String A30 = getString(R.string.RA30); String B30 = getString(R.string.RB30); String C30 = getString(R.string.RC30); String D30 = getString(R.string.RD30); String CorrectAnsNo30 = getString(R.string.RAns30);//Nomer 30
        String A31 = getString(R.string.RA31); String B31 = getString(R.string.RB31); String C31 = getString(R.string.RC31); String D31 = getString(R.string.RD31); String CorrectAnsNo31 = getString(R.string.RAns31);//Nomer 31
        String A32 = getString(R.string.RA32); String B32 = getString(R.string.RB32); String C32 = getString(R.string.RC32); String D32 = getString(R.string.RD32); String CorrectAnsNo32 = getString(R.string.RAns32);//Nomer 32
        String A33 = getString(R.string.RA33); String B33 = getString(R.string.RB33); String C33 = getString(R.string.RC33); String D33 = getString(R.string.RD33); String CorrectAnsNo33 = getString(R.string.RAns33);//Nomer 33
        String A34 = getString(R.string.RA34); String B34 = getString(R.string.RB34); String C34 = getString(R.string.RC34); String D34 = getString(R.string.RD34); String CorrectAnsNo34 = getString(R.string.RAns34);//Nomer 34
        String A35 = getString(R.string.RA35); String B35 = getString(R.string.RB35); String C35 = getString(R.string.RC35); String D35 = getString(R.string.RD35); String CorrectAnsNo35 = getString(R.string.RAns35);//Nomer 35
        String A36 = getString(R.string.RA36); String B36 = getString(R.string.RB36); String C36 = getString(R.string.RC36); String D36 = getString(R.string.RD36); String CorrectAnsNo36 = getString(R.string.RAns36);//Nomer 36
        String A37 = getString(R.string.RA37); String B37 = getString(R.string.RB37); String C37 = getString(R.string.RC37); String D37 = getString(R.string.RD37); String CorrectAnsNo37 = getString(R.string.RAns37);//Nomer 37
        String A38 = getString(R.string.RA38); String B38 = getString(R.string.RB38); String C38 = getString(R.string.RC38); String D38 = getString(R.string.RD38); String CorrectAnsNo38 = getString(R.string.RAns38);//Nomer 38
        String A39 = getString(R.string.RA39); String B39 = getString(R.string.RB39); String C39 = getString(R.string.RC39); String D39 = getString(R.string.RD39); String CorrectAnsNo39 = getString(R.string.RAns39);//Nomer 39
        String A40 = getString(R.string.RA40); String B40 = getString(R.string.RB40); String C40 = getString(R.string.RC40); String D40 = getString(R.string.RD40); String CorrectAnsNo40 = getString(R.string.RAns40);//Nomer 40
        String A41 = getString(R.string.RA41); String B41 = getString(R.string.RB41); String C41 = getString(R.string.RC41); String D41 = getString(R.string.RD41); String CorrectAnsNo41 = getString(R.string.RAns41);//Nomer 41
        String A42 = getString(R.string.RA42); String B42 = getString(R.string.RB42); String C42 = getString(R.string.RC42); String D42 = getString(R.string.RD42); String CorrectAnsNo42 = getString(R.string.RAns42);//Nomer 42
        String A43 = getString(R.string.RA43); String B43 = getString(R.string.RB43); String C43 = getString(R.string.RC43); String D43 = getString(R.string.RD43); String CorrectAnsNo43 = getString(R.string.RAns43);//Nomer 43
        String A44 = getString(R.string.RA44); String B44 = getString(R.string.RB44); String C44 = getString(R.string.RC44); String D44 = getString(R.string.RD44); String CorrectAnsNo44 = getString(R.string.RAns44);//Nomer 44
        String A45 = getString(R.string.RA45); String B45 = getString(R.string.RB45); String C45 = getString(R.string.RC45); String D45 = getString(R.string.RD45); String CorrectAnsNo45 = getString(R.string.RAns45);//Nomer 45
        String A46 = getString(R.string.RA46); String B46 = getString(R.string.RB46); String C46 = getString(R.string.RC46); String D46 = getString(R.string.RD46); String CorrectAnsNo46 = getString(R.string.RAns46);//Nomer 46
        String A47 = getString(R.string.RA47); String B47 = getString(R.string.RB47); String C47 = getString(R.string.RC47); String D47 = getString(R.string.RD47); String CorrectAnsNo47 = getString(R.string.RAns47);//Nomer 47
        String A48 = getString(R.string.RA48); String B48 = getString(R.string.RB48); String C48 = getString(R.string.RC48); String D48 = getString(R.string.RD48); String CorrectAnsNo48 = getString(R.string.RAns48);//Nomer 48
        String A49 = getString(R.string.RA49); String B49 = getString(R.string.RB49); String C49 = getString(R.string.RC49); String D49 = getString(R.string.RD49); String CorrectAnsNo49 = getString(R.string.RAns49);//Nomer 49
        String A50 = getString(R.string.RA50); String B50 = getString(R.string.RB50); String C50 = getString(R.string.RC50); String D50 = getString(R.string.RD50); String CorrectAnsNo50 = getString(R.string.RAns50);//Nomer 50

        questionsList = new ArrayList<>();
        questionsList.add(new ReadingModel(R.string.QR1, A1, B1, C1, D1, CorrectAnsNo1));//1
        questionsList.add(new ReadingModel(R.string.QR2, A2, B2, C2, D2, CorrectAnsNo2));//2
        questionsList.add(new ReadingModel(R.string.QR3, A3, B3, C3, D3, CorrectAnsNo3));//3
        questionsList.add(new ReadingModel(R.string.QR4, A4, B4, C4, D4, CorrectAnsNo4));//4
        questionsList.add(new ReadingModel(R.string.QR5, A5, B5, C5, D5, CorrectAnsNo5));//5
        questionsList.add(new ReadingModel(R.string.QR6, A6, B6, C6, D6, CorrectAnsNo6));//6
        questionsList.add(new ReadingModel(R.string.QR7, A7, B7, C7, D7, CorrectAnsNo7));//7
        questionsList.add(new ReadingModel(R.string.QR8, A8, B8, C8, D8, CorrectAnsNo8));//8
        questionsList.add(new ReadingModel(R.string.QR9, A9, B9, C9, D9, CorrectAnsNo9));//9
        questionsList.add(new ReadingModel(R.string.QR10, A10, B10, C10, D10, CorrectAnsNo10));//10
        questionsList.add(new ReadingModel(R.string.QR11, A11, B11, C11, D11, CorrectAnsNo11));//11
        questionsList.add(new ReadingModel(R.string.QR12, A12, B12, C12, D12, CorrectAnsNo12));//12
        questionsList.add(new ReadingModel(R.string.QR13, A13, B13, C13, D13, CorrectAnsNo13));//13
        questionsList.add(new ReadingModel(R.string.QR14, A14, B14, C14, D14, CorrectAnsNo14));//14
        questionsList.add(new ReadingModel(R.string.QR15, A15, B15, C15, D15, CorrectAnsNo15));//15
        questionsList.add(new ReadingModel(R.string.QR16, A16, B16, C16, D16, CorrectAnsNo16));//16
        questionsList.add(new ReadingModel(R.string.QR17, A17, B17, C17, D17, CorrectAnsNo17));//17
        questionsList.add(new ReadingModel(R.string.QR18, A18, B18, C18, D18, CorrectAnsNo18));//18
        questionsList.add(new ReadingModel(R.string.QR19, A19, B19, C19, D19, CorrectAnsNo19));//19
        questionsList.add(new ReadingModel(R.string.QR20, A20, B20, C20, D20, CorrectAnsNo20));//20
        questionsList.add(new ReadingModel(R.string.QR21, A21, B21, C21, D21, CorrectAnsNo21));//21
        questionsList.add(new ReadingModel(R.string.QR22, A22, B22, C22, D22, CorrectAnsNo22));//22
        questionsList.add(new ReadingModel(R.string.QR23, A23, B23, C23, D23, CorrectAnsNo23));//23
        questionsList.add(new ReadingModel(R.string.QR24, A24, B24, C24, D24, CorrectAnsNo24));//24
        questionsList.add(new ReadingModel(R.string.QR25, A25, B25, C25, D25, CorrectAnsNo25));//25
        questionsList.add(new ReadingModel(R.string.QR26, A26, B26, C26, D26, CorrectAnsNo26));//26
        questionsList.add(new ReadingModel(R.string.QR27, A27, B27, C27, D27, CorrectAnsNo27));//27
        questionsList.add(new ReadingModel(R.string.QR28, A28, B28, C28, D28, CorrectAnsNo28));//28
        questionsList.add(new ReadingModel(R.string.QR29, A29, B29, C29, D29, CorrectAnsNo29));//29
        questionsList.add(new ReadingModel(R.string.QR30, A30, B30, C30, D30, CorrectAnsNo30));//30
        questionsList.add(new ReadingModel(R.string.QR31, A31, B31, C31, D31, CorrectAnsNo31));//31
        questionsList.add(new ReadingModel(R.string.QR32, A32, B32, C32, D32, CorrectAnsNo32));//32
        questionsList.add(new ReadingModel(R.string.QR33, A33, B33, C33, D33, CorrectAnsNo33));//33
        questionsList.add(new ReadingModel(R.string.QR34, A34, B34, C34, D34, CorrectAnsNo34));//34
        questionsList.add(new ReadingModel(R.string.QR35, A35, B35, C35, D35, CorrectAnsNo35));//35
        questionsList.add(new ReadingModel(R.string.QR36, A36, B36, C36, D36, CorrectAnsNo36));//36
        questionsList.add(new ReadingModel(R.string.QR37, A37, B37, C37, D37, CorrectAnsNo37));//37
        questionsList.add(new ReadingModel(R.string.QR38, A38, B38, C38, D38, CorrectAnsNo38));//38
        questionsList.add(new ReadingModel(R.string.QR39, A39, B39, C39, D39, CorrectAnsNo39));//39
        questionsList.add(new ReadingModel(R.string.QR40, A40, B40, C40, D40, CorrectAnsNo40));//40
        questionsList.add(new ReadingModel(R.string.QR41, A41, B41, C41, D41, CorrectAnsNo41));//41
        questionsList.add(new ReadingModel(R.string.QR42, A42, B42, C42, D42, CorrectAnsNo42));//42
        questionsList.add(new ReadingModel(R.string.QR43, A43, B43, C43, D43, CorrectAnsNo43));//43
        questionsList.add(new ReadingModel(R.string.QR44, A44, B44, C44, D44, CorrectAnsNo44));//44
        questionsList.add(new ReadingModel(R.string.QR45, A45, B45, C45, D45, CorrectAnsNo45));//45
        questionsList.add(new ReadingModel(R.string.QR46, A46, B46, C46, D46, CorrectAnsNo46));//46
        questionsList.add(new ReadingModel(R.string.QR47, A47, B47, C47, D47, CorrectAnsNo47));//47
        questionsList.add(new ReadingModel(R.string.QR48, A48, B48, C48, D48, CorrectAnsNo48));//48
        questionsList.add(new ReadingModel(R.string.QR49, A49, B49, C49, D49, CorrectAnsNo49));//49
        questionsList.add(new ReadingModel(R.string.QR50, A50, B50, C50, D50, CorrectAnsNo50));//50



    }

}
