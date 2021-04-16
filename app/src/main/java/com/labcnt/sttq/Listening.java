package com.labcnt.sttq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.labcnt.sttq.Adapter.ListeningAdapter;
import com.labcnt.sttq.Direction.Structure_direction;
import com.labcnt.sttq.Model.ListeningModel;
import com.labcnt.sttq.Model.StructureModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Listening extends AppCompatActivity {


//==================================================================================================
//============ INI ADALAH BAGIAN PROGRAM, JANGAN DIUBAH KECUALI OLEH DEVELOPER =====================
//================== DIBUAT OLEH : GALIH AJI PAMBUDI & DIMAS EMERALDO A ============================
//============= PROJECT KERJA PRAKTIK TAHUN 2021 JURUSAN TEKNIK ELEKTRO FT UNTIRTA =================
//==================================================================================================

    //VARIABEL UMUM
    private RecyclerView mRecyclerView;
    private ListeningAdapter mAdapter;
    private ConstraintLayout mParentLayout;
    private TextView mScoreTextView;
    private TextView mRemaningQuestionsTextView;
    private TextView tvNIM;
    private int mTotalQuestions;
    private int mScore;
    private ListeningModel currentQuestion;
    Button btnNext;
    CountDownTimer countDownTimer;
    private TextView tvTimer;
    private ArrayList<ListeningModel> questionsList;

    //VARIABEL MILIK PLUGIN SUARA
    ImageButton playbtn;
    MediaPlayer mPlayer;
    TextView songName, songStartTime, songAllTime;
    AppCompatSeekBar songPrgs;
    static int onTime = 0, startTime = 0, endTime = 0;
    Handler hdlr = new Handler();

    //METHOD UTAMA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//BLOCK SCREENSHOOT DAN RECORDER
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //SELALU POTRAIT

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Listening");

        }

        btnNext = findViewById(R.id.btnNext);
        tvTimer = findViewById(R.id.Timer);

        //ONCLICK NEXT SECTION BUTTON
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NIM = tvNIM.getText().toString();
                String scoreListening = mScoreTextView.getText().toString();
                Intent intent = new Intent(Listening.this, Structure_direction.class);
                intent.putExtra("NIM", NIM);
                intent.putExtra("scoreListening", scoreListening);
                startActivity(intent);
                countDownTimer.cancel();
                mPlayer.pause();
            }
        });


        addQuestion();


        tvNIM = findViewById(R.id.Nim);
        mScoreTextView = findViewById(R.id.scoreL);
        mParentLayout = findViewById(R.id.question_layout);
        mRemaningQuestionsTextView = findViewById(R.id.QuestionNumber);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ListeningAdapter(this,questionsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Listening.this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalQuestions = questionsList.size();
        mScore = 0;
        displayScore();

        tvNIM.setText(getIntent().getStringExtra("NIM"));

        timer();

        //BAGIAN PLUGIN SUARA----------------------------
        mPlayer = MediaPlayer.create(this, R.raw.test);

        playbtn = findViewById(R.id.bt_play);

        songStartTime = findViewById(R.id.tv_song_current_duration);
        songAllTime = findViewById(R.id.tv_song_total_duration);
        songPrgs = findViewById(R.id.seek_song_progressbar);

        endTime = mPlayer.getDuration();
        startTime = mPlayer.getCurrentPosition();
        if (onTime == 0) {
            songPrgs.setMax(endTime);
            onTime = 1;

        }

        songAllTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(endTime),
                TimeUnit.MILLISECONDS.toSeconds(endTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime))));
        songStartTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));
        songPrgs.setProgress(startTime);
        hdlr.postDelayed(UpdateSongTime, 100);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                    playbtn.setImageResource(R.drawable.ic_pause);

                } else {
                    mPlayer.pause();
                    playbtn.setImageResource(R.drawable.ic_play_arrow);

                }

            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            startTime = mPlayer.getCurrentPosition();
            songStartTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(startTime),
                    TimeUnit.MILLISECONDS.toSeconds(startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));
            songPrgs.setProgress(startTime);
            hdlr.postDelayed(this, 100);
        }
    };



    @Override
    public void onBackPressed() {

        return;
    }

    public void displayScore() {
        String scoreString = "Score Listening: " + mScore;
        mScoreTextView.setText(scoreString);
        mRemaningQuestionsTextView.setText("Remaining Questions: " + mTotalQuestions--);
    }

    private void timer() {
        int milidetik = 1000; //jangan diubah
        int detik = 60; //jangan diubah
        int menit = 35;
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
                String scoreListening = mScoreTextView.getText().toString();
                Intent intent = new Intent(Listening.this, Structure_direction.class);
                intent.putExtra("NIM", NIM);
                intent.putExtra("scoreListening", scoreListening);
                startActivity(intent);
                mPlayer.pause();
            }
        }.start();
    }

    public void updateScore() {
        mScore++;
    }

    //--------------------------BANK SOAL LISTENING SECTION (ISI SESUAI ARRAY)--------------------------
    private void addQuestion(){

        String a1 = getString(R.string.LA1); String b1 = getString(R.string.LB1); String c1 = getString(R.string.LC1); String d1 = getString(R.string.LD1); String ans1 = getString(R.string.LAns1);
        String a2 = getString(R.string.LA2); String b2 = getString(R.string.LB2); String c2 = getString(R.string.LC2); String d2 = getString(R.string.LD2); String ans2 = getString(R.string.LAns2);
        String a3 = getString(R.string.LA3); String b3 = getString(R.string.LB3); String c3 = getString(R.string.LC3); String d3 = getString(R.string.LD3); String ans3 = getString(R.string.LAns3);
        String a4 = getString(R.string.LA4); String b4 = getString(R.string.LB4); String c4 = getString(R.string.LC4); String d4 = getString(R.string.LD4); String ans4 = getString(R.string.LAns4);
        String a5 = getString(R.string.LA5); String b5 = getString(R.string.LB5); String c5 = getString(R.string.LC5); String d5 = getString(R.string.LD5); String ans5 = getString(R.string.LAns5);
        String a6 = getString(R.string.LA6); String b6 = getString(R.string.LB6); String c6 = getString(R.string.LC6); String d6 = getString(R.string.LD6); String ans6 = getString(R.string.LAns6);
        String a7 = getString(R.string.LA7); String b7 = getString(R.string.LB7); String c7 = getString(R.string.LC7); String d7 = getString(R.string.LD7); String ans7 = getString(R.string.LAns7);
        String a8 = getString(R.string.LA8); String b8 = getString(R.string.LB8); String c8 = getString(R.string.LC8); String d8 = getString(R.string.LD8); String ans8 = getString(R.string.LAns8);
        String a9 = getString(R.string.LA9); String b9 = getString(R.string.LB9); String c9 = getString(R.string.LC9); String d9 = getString(R.string.LD9); String ans9 = getString(R.string.LAns9);
        String a10 = getString(R.string.LA10); String b10 = getString(R.string.LB10); String c10 = getString(R.string.LC10); String d10 = getString(R.string.LD10); String ans10 = getString(R.string.LAns10);
        String a11 = getString(R.string.LA11); String b11 = getString(R.string.LB11); String c11 = getString(R.string.LC11); String d11 = getString(R.string.LD11); String ans11 = getString(R.string.LAns11);
        String a12 = getString(R.string.LA12); String b12 = getString(R.string.LB12); String c12 = getString(R.string.LC12); String d12 = getString(R.string.LD12); String ans12 = getString(R.string.LAns12);
        String a13 = getString(R.string.LA13); String b13 = getString(R.string.LB13); String c13 = getString(R.string.LC13); String d13 = getString(R.string.LD13); String ans13 = getString(R.string.LAns13);
        String a14 = getString(R.string.LA14); String b14 = getString(R.string.LB14); String c14 = getString(R.string.LC14); String d14 = getString(R.string.LD14); String ans14 = getString(R.string.LAns14);
        String a15 = getString(R.string.LA15); String b15 = getString(R.string.LB15); String c15 = getString(R.string.LC15); String d15 = getString(R.string.LD15); String ans15 = getString(R.string.LAns15);
        String a16 = getString(R.string.LA16); String b16 = getString(R.string.LB16); String c16 = getString(R.string.LC16); String d16 = getString(R.string.LD16); String ans16 = getString(R.string.LAns16);
        String a17 = getString(R.string.LA17); String b17 = getString(R.string.LB17); String c17 = getString(R.string.LC17); String d17 = getString(R.string.LD17); String ans17 = getString(R.string.LAns17);
        String a18 = getString(R.string.LA18); String b18 = getString(R.string.LB18); String c18 = getString(R.string.LC18); String d18 = getString(R.string.LD18); String ans18 = getString(R.string.LAns18);
        String a19 = getString(R.string.LA19); String b19 = getString(R.string.LB19); String c19 = getString(R.string.LC19); String d19 = getString(R.string.LD19); String ans19 = getString(R.string.LAns19);
        String a20 = getString(R.string.LA20); String b20 = getString(R.string.LB20); String c20 = getString(R.string.LC10); String d20 = getString(R.string.LD20); String ans20 = getString(R.string.LAns20);
        String a21 = getString(R.string.LA21); String b21 = getString(R.string.LB21); String c21 = getString(R.string.LC21); String d21 = getString(R.string.LD21); String ans21 = getString(R.string.LAns21);
        String a22 = getString(R.string.LA22); String b22 = getString(R.string.LB22); String c22 = getString(R.string.LC22); String d22 = getString(R.string.LD22); String ans22 = getString(R.string.LAns22);
        String a23 = getString(R.string.LA23); String b23 = getString(R.string.LB23); String c23 = getString(R.string.LC23); String d23 = getString(R.string.LD23); String ans23 = getString(R.string.LAns23);
        String a24 = getString(R.string.LA24); String b24 = getString(R.string.LB24); String c24 = getString(R.string.LC24); String d24 = getString(R.string.LD24); String ans24 = getString(R.string.LAns24);
        String a25 = getString(R.string.LA25); String b25 = getString(R.string.LB25); String c25 = getString(R.string.LC25); String d25 = getString(R.string.LD25); String ans25 = getString(R.string.LAns25);
        String a26 = getString(R.string.LA26); String b26 = getString(R.string.LB26); String c26 = getString(R.string.LC26); String d26 = getString(R.string.LD26); String ans26 = getString(R.string.LAns26);
        String a27 = getString(R.string.LA27); String b27 = getString(R.string.LB27); String c27 = getString(R.string.LC27); String d27 = getString(R.string.LD27); String ans27 = getString(R.string.LAns27);
        String a28 = getString(R.string.LA28); String b28 = getString(R.string.LB28); String c28 = getString(R.string.LC28); String d28 = getString(R.string.LD28); String ans28 = getString(R.string.LAns28);
        String a29 = getString(R.string.LA29); String b29 = getString(R.string.LB29); String c29 = getString(R.string.LC29); String d29 = getString(R.string.LD29); String ans29 = getString(R.string.LAns29);
        String a30 = getString(R.string.LA30); String b30 = getString(R.string.LB30); String c30 = getString(R.string.LC30); String d30 = getString(R.string.LD30); String ans30 = getString(R.string.LAns30);
        String a31 = getString(R.string.LA31); String b31 = getString(R.string.LB31); String c31 = getString(R.string.LC31); String d31 = getString(R.string.LD31); String ans31 = getString(R.string.LAns31);
        String a32 = getString(R.string.LA32); String b32 = getString(R.string.LB32); String c32 = getString(R.string.LC32); String d32 = getString(R.string.LD32); String ans32 = getString(R.string.LAns32);
        String a33 = getString(R.string.LA33); String b33 = getString(R.string.LB33); String c33 = getString(R.string.LC33); String d33 = getString(R.string.LD33); String ans33 = getString(R.string.LAns33);
        String a34 = getString(R.string.LA34); String b34 = getString(R.string.LB34); String c34 = getString(R.string.LC34); String d34 = getString(R.string.LD34); String ans34 = getString(R.string.LAns34);
        String a35 = getString(R.string.LA35); String b35 = getString(R.string.LB35); String c35 = getString(R.string.LC35); String d35 = getString(R.string.LD35); String ans35 = getString(R.string.LAns35);
        String a36 = getString(R.string.LA36); String b36 = getString(R.string.LB36); String c36 = getString(R.string.LC36); String d36 = getString(R.string.LD36); String ans36 = getString(R.string.LAns36);
        String a37 = getString(R.string.LA37); String b37 = getString(R.string.LB37); String c37 = getString(R.string.LC37); String d37 = getString(R.string.LD37); String ans37 = getString(R.string.LAns37);
        String a38 = getString(R.string.LA38); String b38 = getString(R.string.LB38); String c38 = getString(R.string.LC38); String d38 = getString(R.string.LD38); String ans38 = getString(R.string.LAns38);
        String a39 = getString(R.string.LA39); String b39 = getString(R.string.LB39); String c39 = getString(R.string.LC39); String d39 = getString(R.string.LD39); String ans39 = getString(R.string.LAns39);
        String a40 = getString(R.string.LA40); String b40 = getString(R.string.LB40); String c40 = getString(R.string.LC40); String d40 = getString(R.string.LD40); String ans40 = getString(R.string.LAns40);
        String a41 = getString(R.string.LA41); String b41 = getString(R.string.LB41); String c41 = getString(R.string.LC41); String d41 = getString(R.string.LD41); String ans41 = getString(R.string.LAns41);
        String a42 = getString(R.string.LA42); String b42 = getString(R.string.LB42); String c42 = getString(R.string.LC42); String d42 = getString(R.string.LD42); String ans42 = getString(R.string.LAns42);
        String a43 = getString(R.string.LA43); String b43 = getString(R.string.LB43); String c43 = getString(R.string.LC43); String d43 = getString(R.string.LD43); String ans43 = getString(R.string.LAns43);
        String a44 = getString(R.string.LA44); String b44 = getString(R.string.LB44); String c44 = getString(R.string.LC44); String d44 = getString(R.string.LD44); String ans44 = getString(R.string.LAns44);
        String a45 = getString(R.string.LA45); String b45 = getString(R.string.LB45); String c45 = getString(R.string.LC45); String d45 = getString(R.string.LD45); String ans45 = getString(R.string.LAns45);
        String a46 = getString(R.string.LA46); String b46 = getString(R.string.LB46); String c46 = getString(R.string.LC46); String d46 = getString(R.string.LD46); String ans46 = getString(R.string.LAns46);
        String a47 = getString(R.string.LA47); String b47 = getString(R.string.LB47); String c47 = getString(R.string.LC47); String d47 = getString(R.string.LD47); String ans47 = getString(R.string.LAns47);
        String a48 = getString(R.string.LA48); String b48 = getString(R.string.LB48); String c48 = getString(R.string.LC48); String d48 = getString(R.string.LD48); String ans48 = getString(R.string.LAns48);
        String a49 = getString(R.string.LA49); String b49 = getString(R.string.LB49); String c49 = getString(R.string.LC49); String d49 = getString(R.string.LD49); String ans49 = getString(R.string.LAns49);
        String a50 = getString(R.string.LA50); String b50 = getString(R.string.LB50); String c50 = getString(R.string.LC50); String d50 = getString(R.string.LD50); String ans50 = getString(R.string.LAns50);


        questionsList = new ArrayList<>();
        questionsList.add(new ListeningModel(R.string.LQuestion1, a1, b1, c1, d1, ans1)); //
        questionsList.add(new ListeningModel(R.string.LQuestion2, a2, b2, c2, d2, ans2)); //
        questionsList.add(new ListeningModel(R.string.LQuestion3, a3, b3, c3, d3, ans3)); //
        questionsList.add(new ListeningModel(R.string.LQuestion4, a4, b4, c4, d4, ans4)); //
        questionsList.add(new ListeningModel(R.string.LQuestion5, a5, b5, c5, d5, ans5)); //
        questionsList.add(new ListeningModel(R.string.LQuestion6, a6, b6, c6, d6, ans6)); //
        questionsList.add(new ListeningModel(R.string.LQuestion7, a7, b7, c7, d7, ans7)); //
        questionsList.add(new ListeningModel(R.string.LQuestion8, a8, b8, c8, d8, ans8)); //
        questionsList.add(new ListeningModel(R.string.LQuestion9, a9, b9, c9, d9, ans9)); //
        questionsList.add(new ListeningModel(R.string.LQuestion10, a10, b10, c10, d10, ans10)); //
        questionsList.add(new ListeningModel(R.string.LQuestion11, a11, b11, c11, d11, ans11)); //
        questionsList.add(new ListeningModel(R.string.LQuestion12, a12, b12, c12, d12, ans12)); //
        questionsList.add(new ListeningModel(R.string.LQuestion13, a13, b13, c13, d13, ans13)); //
        questionsList.add(new ListeningModel(R.string.LQuestion14, a14, b14, c14, d14, ans14)); //
        questionsList.add(new ListeningModel(R.string.LQuestion15, a15, b15, c15, d15, ans15)); //
        questionsList.add(new ListeningModel(R.string.LQuestion16, a16, b16, c16, d16, ans16)); //
        questionsList.add(new ListeningModel(R.string.LQuestion17, a17, b17, c17, d17, ans17)); //
        questionsList.add(new ListeningModel(R.string.LQuestion18, a18, b18, c18, d18, ans18)); //
        questionsList.add(new ListeningModel(R.string.LQuestion19, a19, b19, c19, d19, ans19)); //
        questionsList.add(new ListeningModel(R.string.LQuestion20, a20, b20, c20, d20, ans20)); //
        questionsList.add(new ListeningModel(R.string.LQuestion21, a21, b21, c21, d21, ans21)); //
        questionsList.add(new ListeningModel(R.string.LQuestion22, a22, b22, c22, d22, ans22)); //
        questionsList.add(new ListeningModel(R.string.LQuestion23, a23, b23, c23, d23, ans23)); //
        questionsList.add(new ListeningModel(R.string.LQuestion24, a24, b24, c24, d24, ans24)); //
        questionsList.add(new ListeningModel(R.string.LQuestion25, a25, b25, c25, d25, ans25)); //
        questionsList.add(new ListeningModel(R.string.LQuestion26, a26, b26, c26, d26, ans26)); //
        questionsList.add(new ListeningModel(R.string.LQuestion27, a27, b27, c27, d27, ans27)); //
        questionsList.add(new ListeningModel(R.string.LQuestion28, a28, b28, c28, d28, ans28)); //
        questionsList.add(new ListeningModel(R.string.LQuestion29, a29, b29, c29, d29, ans29)); //
        questionsList.add(new ListeningModel(R.string.LQuestion30, a30, b30, c30, d30, ans30)); //
        questionsList.add(new ListeningModel(R.string.LQuestion31, a31, b31, c31, d31, ans31)); //
        questionsList.add(new ListeningModel(R.string.LQuestion32, a32, b32, c32, d32, ans32)); //
        questionsList.add(new ListeningModel(R.string.LQuestion33, a33, b33, c33, d33, ans33)); //
        questionsList.add(new ListeningModel(R.string.LQuestion34, a34, b34, c34, d34, ans34)); //
        questionsList.add(new ListeningModel(R.string.LQuestion35, a35, b35, c35, d35, ans35)); //
        questionsList.add(new ListeningModel(R.string.LQuestion36, a36, b36, c36, d36, ans36)); //
        questionsList.add(new ListeningModel(R.string.LQuestion37, a37, b37, c37, d37, ans37)); //
        questionsList.add(new ListeningModel(R.string.LQuestion38, a38, b38, c38, d38, ans38)); //
        questionsList.add(new ListeningModel(R.string.LQuestion39, a39, b39, c39, d39, ans39)); //
        questionsList.add(new ListeningModel(R.string.LQuestion40, a40, b40, c40, d40, ans40)); //
        questionsList.add(new ListeningModel(R.string.LQuestion41, a41, b41, c41, d41, ans41)); //
        questionsList.add(new ListeningModel(R.string.LQuestion42, a42, b42, c42, d42, ans42)); //
        questionsList.add(new ListeningModel(R.string.LQuestion43, a43, b43, c43, d43, ans43)); //
        questionsList.add(new ListeningModel(R.string.LQuestion44, a44, b44, c44, d44, ans44)); //
        questionsList.add(new ListeningModel(R.string.LQuestion45, a45, b45, c45, d45, ans45)); //
        questionsList.add(new ListeningModel(R.string.LQuestion46, a46, b46, c46, d46, ans46)); //
        questionsList.add(new ListeningModel(R.string.LQuestion47, a47, b47, c47, d47, ans47)); //
        questionsList.add(new ListeningModel(R.string.LQuestion48, a48, b48, c48, d48, ans48)); //
        questionsList.add(new ListeningModel(R.string.LQuestion49, a49, b49, c49, d49, ans49)); //
        questionsList.add(new ListeningModel(R.string.LQuestion50, a50, b50, c50, d50, ans50)); //



        //------------------------------------------------------------------------------------------

    }


}
