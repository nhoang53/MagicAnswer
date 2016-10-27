package edu.orangecoastcollege.cs273.magicanswer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MagicAnswerActivity extends AppCompatActivity {

    MagicAnswer magicAnswer;
    private EditText questionEditText;
    private TextView answerTextView;

    // Reference to the SensorManager (manages ALL sensors on device)
    private SensorManager sensorManager;

    // Reference to the acceleremeter
    private Sensor accelerometer;

    // Reference to the shake detector
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_answer);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        questionEditText = (EditText) findViewById(R.id.questionEditText);

        // TASK 2: CREATE A NEW MAGIC ANSWER OBJECT
        magicAnswer = new MagicAnswer(this);

        // TASK 3: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            // This listener connect with the onShake on the ShakeDetector class
            @Override
            public void onShake() {
                // Get a random answer, put it in the answerTextView
                String answer = magicAnswer.getRandomAnswer();
                answerTextView.setText(answer);
            }
        });

    }

    // Don't create it on onCreate function because it will call only 1 time.
    @Override
    protected void onResume() {
        super.onResume();
        // Start the accelerometer
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }
}
