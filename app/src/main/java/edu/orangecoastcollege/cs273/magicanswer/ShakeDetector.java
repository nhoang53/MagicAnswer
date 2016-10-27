package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by nhoang53 on 10/27/2016.
 */

public class ShakeDetector implements SensorEventListener {

    // Constant to represent a shake threshold
    private static final float SHAKE_THRESHOLD = 25f; // popular shake

    // Constant to represent how long between shakes (milliseconds)
    private static final int SHAKE_TIME_LAPSE = 2000;

    // Constant to represent what was the last time the event occurred
    private long timeOfLastShakes;

    // Define a listener to register onShake events
    private OnShakeListener shakeListener;

    // Constructor to create a new ShakeDetector passing in an OnShaListener as argument:
    public ShakeDetector(OnShakeListener listener)
    {
        shakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // Determine if the event is an accelerometer
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            // Get the x, y, z values when this event occurs:
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // idea: get the distance = square root of (x2 - x1) ^2 + ....
            // Compare all 3 values against gravity
            // so the distance = (x2 - gravity)^2 + ...
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            // Compute sum of squares
            double vector = Math.pow(gForceX, 2.0) + Math.pow(gForceY, 2.0)
                            + Math.pow(gForceZ, 2.0);

            // Compute gForce, use float because android prefer
            float gForce = (float) Math.sqrt(vector);

            // Compare fForce against the threshold
            if(gForce > SHAKE_THRESHOLD){
                // Get the current time:
                long now = System.currentTimeMillis();
                // Compare to see if the current time is at least 2000 milliseconds
                // greater than the time of the last shake
                if(now - timeOfLastShakes >= SHAKE_TIME_LAPSE)
                {
                    timeOfLastShakes = now;

                    // Register a shake event!!!
                    shakeListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    // Define our own interface (method for other classes to implement
    // called onShake()
    // It's the responsibility of MagicAnswerActivity to implement this method

    public interface OnShakeListener{
        void onShake();
    }
}
