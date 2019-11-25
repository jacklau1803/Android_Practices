package com.jacklau1803.diditmove;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;


public class MyService extends Service implements SensorEventListener {

    private SensorManager sm;
    private Sensor accelerometer;
    private float currentMovement;
    private float lastMovement;
    int hasMoved = 0;
    int moved = 0;

    public MyService() {}


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI, new Handler());
        moved = intent.getIntExtra("moved", 0);
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xMotion = sensorEvent.values[0];
        float yMotion = sensorEvent.values[1];
        float zMotion = sensorEvent.values[2];

        lastMovement = currentMovement;
        currentMovement = (float) Math.sqrt(xMotion*xMotion + yMotion*yMotion + zMotion*zMotion);


        if(currentMovement == lastMovement){
            changeToMoved();
        }else{
            changeToNotMoved();
        }

    }



    private void changeToMoved(){
        hasMoved = 1;
        Intent localUpdate = new Intent();
        localUpdate.setAction("ActionTextView");
        this.sendBroadcast(localUpdate);
    }
    private void changeToNotMoved(){
        if(moved == 1){
            hasMoved = 0;
            moved =0;
        }

        if(hasMoved != 1){
            Intent localUpdate = new Intent();
            localUpdate.setAction("ActionTextView");
            this.sendBroadcast(localUpdate);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}