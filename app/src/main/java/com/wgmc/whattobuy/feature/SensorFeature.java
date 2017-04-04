package com.wgmc.whattobuy.feature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by proxie on 4.4.17.
 */

public class SensorFeature {
    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener eventHandler;

    public SensorFeature(Context context, Sensor sensor, SensorEventListener eventHandler) {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensor;
        this.eventHandler = eventHandler;
    }

    public SensorFeature(Context context) {
        this(context, null, null);
    }

    protected void setEventHandler(SensorEventListener eventHandler) {
        this.eventHandler = eventHandler;
    }

    protected void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public final void registerFeature() {
        manager.registerListener(eventHandler, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public final void unregisterFeature() {
        manager.unregisterListener(eventHandler);
    }

    protected SensorManager getManager() {
        return manager;
    }
}
