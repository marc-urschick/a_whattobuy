package com.wgmc.whattobuy.service;

import com.wgmc.whattobuy.feature.SensorFeature;
import com.wgmc.whattobuy.feature.ShakeSensorFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by proxie on 4.4.17.
 */
// service for administration of sensor features
    // handles registration and unregistration of features
public class FeatureService extends DefaultService {
    private static FeatureService instance;

    public static FeatureService getInstance() {
        if (instance == null) {
            instance = new FeatureService();
        }
        return instance;
    }

    private final Map<Class, SensorFeature> sensorFeatures;

    private FeatureService() {
        sensorFeatures = new HashMap<>();
        SettingsService.getInstance().addObserver(this);
    }

    public List<SensorFeature> getSensorFeatures() {
        return new ArrayList<>(sensorFeatures.values());
    }

    public boolean containsSensorFeature(SensorFeature o) {
        return sensorFeatures.containsValue(o);
    }

    public void addSensorFeature(SensorFeature sensorFeature) {
        sensorFeatures.put(sensorFeature.getClass(), sensorFeature);
    }

    public void removeSensorFeature(SensorFeature o) {
        sensorFeatures.remove(o.getClass());
    }

    public SensorFeature getSensorFeature(Class index) {
        return sensorFeatures.get(index);
    }

    public void registerSensorFeatures() {
        for (SensorFeature f : sensorFeatures.values()) {
            if (f.isFeatureEnabled())
                f.registerFeature();
        }
    }

    public void unregisterSensorFeatures() {
        for (SensorFeature f : sensorFeatures.values()) {
            f.unregisterFeature();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg.equals(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS)) {
            getSensorFeature(ShakeSensorFeature.class)
                    .setFeatureEnabled(
                            SettingsService.getInstance().getSetting(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS).equals(true)
                    );
        }
    }
}
