package com.jacklau1803.diditmove;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
        private Handler mHandler = new Handler();
        TextView status;
        Button clear;
        Button exit;
        boolean moved = false;
        private BroadcastReceiver changeTV;

        IntentFilter newIntentFilter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            status = findViewById(R.id.status);
            clear = findViewById(R.id.clear);
            exit = findViewById(R.id.exit);


            final Intent newIntent = new Intent(this, MyService.class);
            newIntentFilter = new IntentFilter();
            newIntentFilter.addAction("ActionTextView");


            newIntent.putExtra("moved", 1);
            startService(newIntent);
            status.setText("Everything was quiet.");

            mHandler.postDelayed(new Runnable() {
                public void run() {


                }
            }, 10000);



            startService(newIntent);

            changeTV = new BroadcastReceiver(){
                @Override
                public void onReceive(Context context, Intent intent) {
                    moved = true;
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status.setText("The phone moved!");
                }
            };

            registerReceiver(changeTV, newIntentFilter);
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newIntent.putExtra("value", 1);
                    startService(newIntent);
                    status.setText("Everything was quiet.");
                }
            });

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAffinity();
                }

            });

        }

        @Override
        protected void onResume() {
            super.onResume();
            registerReceiver(changeTV, newIntentFilter);
        }

        @Override
        protected void onStart() {
            super.onStart();

        }
        @Override
        protected void onPause() {
            super.onPause();

            unregisterReceiver(changeTV);
        }
}