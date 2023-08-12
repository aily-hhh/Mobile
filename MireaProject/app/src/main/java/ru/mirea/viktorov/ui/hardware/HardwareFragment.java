package ru.mirea.viktorov.ui.hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentHardwareBinding;


public class HardwareFragment extends Fragment implements SensorEventListener {

    private FragmentHardwareBinding binding;
    private TextView tvLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHardwareBinding.inflate(inflater, container, false);

        tvLight = binding.tvLight;

        SensorManager sensorManager = (SensorManager)requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor pressure = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);

        return binding.getRoot();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float valueLight = event.values[0];
            tvLight.setText("" + valueLight);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}