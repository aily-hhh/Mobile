package ru.mirea.viktorov.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnDateDialog);
    }

    public void onClickShowDialog(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(
                getApplicationContext(),
                "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG
        ).show();
    }
    public void onCancelClicked() {
        Toast.makeText(
                getApplicationContext(),
                "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG
        ).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(
                getApplicationContext(),
                "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG
        ).show();
    }

    public void onClickShowTimeDialog(View view) {
        DialogFragment newFragment = new MyTimeDialogFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onDateClicked(int year, int month, int day) {
        Snackbar.make(btn, "" + day + "." + month + "." + year, Snackbar.LENGTH_LONG).show();
    }

    public void onClickShowDateDialog(View view) {
        DialogFragment newFragment = new MyDateDialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment dialogFragment = new MyProgressDialogFragment(this);
        dialogFragment.show();
    }
}