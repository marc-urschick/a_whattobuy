package com.wgmc.whattobuy.feature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by proxie on 4.4.17.
 */
// standard functions of any sensor feature
    // this class must be root of all sensor features and implemented features
public class SensorFeature {
    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener eventHandler;

    private boolean featureEnabled;

    public SensorFeature(Context context, Sensor sensor, SensorEventListener eventHandler) {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensor;
        this.eventHandler = eventHandler;
        featureEnabled = true;
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

    public void registerFeature() {
        Log.d(getClass().getSimpleName(), "registering this Feature! (happened:" +
                manager.registerListener(eventHandler, sensor, SensorManager.SENSOR_DELAY_GAME) + ")");
    }

    public void unregisterFeature() {
        Log.d(getClass().getSimpleName(), "unregistering this Feature!");
        manager.unregisterListener(eventHandler);
    }

    protected SensorManager getManager() {
        return manager;
    }

    public boolean isFeatureEnabled() {
        return featureEnabled;
    }

    public void setFeatureEnabled(boolean featureEnabled) {
        this.featureEnabled = featureEnabled;
    }
}
