package com.example.kai.icrowd;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, BasicFragment.newInstance())
                    .commit();
        }
    }
}
