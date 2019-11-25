package com.jacklau1803.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button coding = findViewById(R.id.bt_coding);
        Button eating = findViewById(R.id.bt_eating);
        Button sleeping = findViewById(R.id.bt_sleeping);
        boolean c = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("c", true);
        boolean e = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("e", true);
        boolean s = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("s", true);
        if(!c)
            coding.setVisibility(View.INVISIBLE);
        if(!e)
            eating.setVisibility(View.INVISIBLE);
        if(!s)
            sleeping.setVisibility(View.INVISIBLE);
        Button exit = findViewById(R.id.bt_exit);
        coding.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, coding.class);
                startActivity(intent1);
            }
        });
        eating.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Eating.class);
                startActivity(intent1);
            }
        });
        sleeping.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Sleeping.class);
                startActivity(intent1);
            }
        });
        exit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button coding = findViewById(R.id.bt_coding);
        Button eating = findViewById(R.id.bt_eating);
        Button sleeping = findViewById(R.id.bt_sleeping);
        boolean c = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("c", true);
        boolean e = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("e", true);
        boolean s = (boolean) getSharedPreferences("flag", MODE_PRIVATE)
                .getBoolean("s", true);
        if(!c)
            coding.setVisibility(View.INVISIBLE);
        if(!e)
            eating.setVisibility(View.INVISIBLE);
        if(!s)
            sleeping.setVisibility(View.INVISIBLE);
    }
}