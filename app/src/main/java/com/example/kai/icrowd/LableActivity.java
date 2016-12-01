package com.example.kai.icrowd;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.mark_container, MarkFragment.newInstance())
                    .commit();
        }
    }
}
