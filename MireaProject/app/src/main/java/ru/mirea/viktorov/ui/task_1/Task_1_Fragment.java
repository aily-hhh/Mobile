package ru.mirea.viktorov.ui.task_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.mirea.viktorov.R;

public class Task_1_Fragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_1, container, false);
        // textView - label
        textView = view.findViewById(R.id.textViewTask1);

        // Inflate the layout for this fragment
        return view;
    }
}