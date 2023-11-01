package ru.mirea.viktorov.ui.task_2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import ru.mirea.viktorov.R;
import ru.mirea.viktorov.databinding.FragmentTask2Binding;


public class Task_2_Fragment extends Fragment {

    private FragmentTask2Binding binding;
    private Boolean isState = true;
    private PackageManager packageManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageManager = requireActivity().getPackageManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTask2Binding.inflate(inflater, container, false);

        binding.packageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
                for (ApplicationInfo packageInfo : packages) {
                    System.out.println(packageInfo.packageName);
                    if (packageInfo.packageName.equals("any desk")) {
                        isState = false;
                    }
                }

                if (isState) {
                    Toast.makeText(getContext(), "Ok", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Package \"any desk\" найден", Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {}
                    }, 2000);
                    requireActivity().finish();
                }
            }
        });

        return binding.getRoot();
    }
}