package com.jacklau1803.assignment1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sleeping extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
        Button ok = findViewById(R.id.bt_ok);
        Button delete = findViewById(R.id.bt_del_s);
        ok.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("flag", MODE_PRIVATE);
                sp.edit()
                        .putBoolean("s", false)
                        .commit();
                finish();
            }
        });

    }
}
