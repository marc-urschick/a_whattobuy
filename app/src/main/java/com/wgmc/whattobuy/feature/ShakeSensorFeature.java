package com.wgmc.whattobuy.feature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.wgmc.whattobuy.service.FeatureService;

/**
 * Created by proxie on 4.4.17.
 */

public class ShakeSensorFeature extends SensorFeature {
    public static final int SHAKE_HAPPENED = -7389;

    public ShakeSensorFeature(Context context) {
        super(context);
        setSensor(getManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        setEventHandler( new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                 FeatureService.getInstance().notifyObservers(SHAKE_HAPPENED);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        } );
    }
}
