package com.wgmc.whattobuy.feature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.wgmc.whattobuy.service.FeatureService;

import java.util.Date;
import java.util.Locale;

/**
 * Created by proxie on 4.4.17.
 */
// class is used for managing shake events when the device is shaked
public class ShakeSensorFeature extends SensorFeature {
    public static final int SHAKE_HAPPENED = -7389;

    public ShakeSensorFeature(Context context) {
        super(context);
        setSensor(getManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        setEventHandler(new SensorEventListener() {
            /** Minimum movement force to consider. */
            private static final int MIN_FORCE = 10;

            /** Minimum time between two events so it does notify */
            private static final long MIN_TIME_BETWEEN_EVENTS = 450; // ms

            /**
             * Minimum times in a shake gesture that the direction of movement needs to
             * change.
             */
            private static final int MIN_DIRECTION_CHANGE = 3;

            /** Maximum pause between movements. */
            private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;

            /** Maximum allowed time for shake gesture. */
            private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;

            /** Time when the gesture started. */
            private long mFirstDirectionChangeTime = 0;

            /** Time when the last movement started. */
            private long mLastDirectionChangeTime;

            /** How many movements are considered so far. */
            private int mDirectionChangeCount = 0;

            /** The last x position. */
            private float lastX = 0;

            /** The last y position. */
            private float lastY = 0;

            /** The last z position. */
            private float lastZ = 0;

            private long lastTime = System.currentTimeMillis();

            @Override
            public void onSensorChanged(SensorEvent se) {
                // following code needs to be there so events do occur
                // dont know why this is so but without no events will come at all
                double random = Math.random();
                random *= 14;
                random = Math.sqrt(Math.abs(random));
//                System.out.println(random);
                // get sensor data
                float x = se.values[SensorManager.DATA_X];
                float y = se.values[SensorManager.DATA_Y];
                float z = se.values[SensorManager.DATA_Z];

                // calculate movement
                float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

                if (totalMovement > MIN_FORCE) {

                    // get time
                    long now = System.currentTimeMillis();

                    // store first movement time
                    if (mFirstDirectionChangeTime == 0) {
                        mFirstDirectionChangeTime = now;
                        mLastDirectionChangeTime = now;
                    }

                    // check if the last movement was not long ago
                    long lastChangeWasAgo = now - mLastDirectionChangeTime;
                    long lastEvent = now - lastTime;
                    if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE && lastEvent > MIN_TIME_BETWEEN_EVENTS) {

                        // store movement data
                        mLastDirectionChangeTime = now;
                        mDirectionChangeCount++;


                        // store last sensor data
                        lastX = x;
                        lastY = y;
                        lastZ = z;

                        // check how many movements are so far
                        if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

                            // check total duration
                            long totalDuration = now - mFirstDirectionChangeTime;
                            if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
                                lastTime = System.currentTimeMillis();
                                onMySensorChanged(se);
                                resetShakeParameters();
                            }
                        }

                    } else {
                        resetShakeParameters();
                    }
                }
            }

            /**
             * Resets the shake parameters to their default values.
             */
            private void resetShakeParameters() {
                mFirstDirectionChangeTime = 0;
                mDirectionChangeCount = 0;
                mLastDirectionChangeTime = 0;
                lastX = 0;
                lastY = 0;
                lastZ = 0;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onMySensorChanged(SensorEvent event) {
                Log.d(getClass().getSimpleName(), "Shake event occurred");
                FeatureService.getInstance().notifyObservers(SHAKE_HAPPENED);
            }
        });
    }

    public ShakeSensorFeature() {
        super(null);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ShakeSensorFeature;
    }
}
