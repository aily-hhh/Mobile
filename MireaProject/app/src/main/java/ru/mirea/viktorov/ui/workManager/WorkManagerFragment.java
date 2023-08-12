package ru.mirea.viktorov.ui.workManager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentWorkManagerBinding;


public class WorkManagerFragment extends Fragment {

    private FragmentWorkManagerBinding binding;
    private Button buttonWorkManager;
    private TextView textViewWorkManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkManagerBinding.inflate(inflater, container, false);
        buttonWorkManager = binding.buttonWorkManager;
        textViewWorkManager = binding.textViewWorkManager;

        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(BaseWorker.class)
                        .addTag("Work")
                        .build();

        buttonWorkManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager
                        .getInstance(requireActivity().getApplicationContext())
                        .enqueue(uploadWorkRequest);
            }
        });

        return binding.getRoot();
    }
}