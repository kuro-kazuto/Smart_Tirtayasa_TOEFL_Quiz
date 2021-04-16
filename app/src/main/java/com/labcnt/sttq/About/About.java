package com.labcnt.sttq.About;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.labcnt.sttq.R;

public class About extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About Us");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView btn_dev1 = findViewById(R.id.dev1);
        btn_dev1.setOnClickListener(this);

        ImageView btn_dev2 = findViewById(R.id.dev2);
        btn_dev2.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dev1:
                Intent Intent1 = new Intent(About.this, Dev1.class);
                startActivity(Intent1);
                break;

            case R.id.dev2:
                Intent Intent2 = new Intent(About.this, Dev2.class);
                startActivity(Intent2);
                break;
        }
    }
}