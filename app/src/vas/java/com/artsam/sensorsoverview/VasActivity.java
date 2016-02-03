package com.artsam.sensorsoverview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VasActivity extends MainActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private List<String> mSensorNames = new ArrayList<>();
    private Map<String, float[]> mValuesMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vas);

//        String[] catNames = getResources().getStringArray(R.array.cat_names);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> devSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 0; i < devSensors.size(); i++) {
            mSensorManager.registerListener(this, devSensors.get(i),
                    SensorManager.SENSOR_DELAY_UI);
            mSensorNames.add(devSensors.get(i).getName());
        }

        RecyclerView recView = (RecyclerView) findViewById(R.id.rv_sensor_readings);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(new SensorsRecAdapter(mSensorNames, mValuesMap));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!mValuesMap.containsKey(event.sensor.getName())){
            mValuesMap.put(event.sensor.getName(), event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        // unregister listener
        mSensorManager.unregisterListener(this);
        super.onStop();
    }
}
