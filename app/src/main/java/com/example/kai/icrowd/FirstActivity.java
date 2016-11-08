package com.example.kai.icrowd;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar myActionBar=getSupportActionBar();
        if (myActionBar != null) {
            myActionBar.hide();
        }

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
        TextView tv = (TextView)findViewById(R.id.start_tv);
        tv.setTypeface(tf);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(FirstActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }
        }, 1800);
    }
}
