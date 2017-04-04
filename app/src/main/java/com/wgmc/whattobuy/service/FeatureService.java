package com.wgmc.whattobuy.service;

import com.wgmc.whattobuy.feature.SensorFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proxie on 4.4.17.
 */

public class FeatureService extends DefaultService {
    private static FeatureService instance;

    public static FeatureService getInstance() {
        return instance;
    }

    public static void createInstance() {
        instance = new FeatureService();
    }

    public static void destroyInstance() {
        instance = null;
    }

    private final List<SensorFeature> sensorFeatures;

    private FeatureService() {
        sensorFeatures = new ArrayList<>();
    }

    public List<SensorFeature> getSensorFeatures() {
        return sensorFeatures;
    }

    public boolean containsSensorFeature(SensorFeature o) {
        return sensorFeatures.contains(o);
    }

    public boolean addSensorFeature(SensorFeature sensorFeature) {
        return sensorFeatures.add(sensorFeature);
    }

    public boolean removeSensorFeature(SensorFeature o) {
        return sensorFeatures.remove(o);
    }

    public SensorFeature getSensorFeature(int index) {
        return sensorFeatures.get(index);
    }

    public void registerSensorFeatures() {
        for (SensorFeature f : sensorFeatures) {
            f.registerFeature();
        }
    }

    public void unregisterSensorFeatures() {
        for (SensorFeature f : sensorFeatures) {
            f.unregisterFeature();
        }
    }
}
