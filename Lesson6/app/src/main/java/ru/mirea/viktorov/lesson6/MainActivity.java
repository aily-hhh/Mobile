package ru.mirea.viktorov.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etGroup, etList, etFilm;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGroup = findViewById(R.id.etGroup);
        etList = findViewById(R.id.etList);
        etFilm = findViewById(R.id.etFilm);
        btnSave = findViewById(R.id.btnSave);

        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("GROUP", etGroup.getText().toString().trim());
                editor.putString("FILM", etFilm.getText().toString().trim());
                editor.putInt("NUMBER", Integer.parseInt(etList.getText().toString().trim()));
                editor.apply();
            }
        });

        String group = sharedPref.getString("GROUP", "");
        String film = sharedPref.getString("FILM", "");
        int numberInList = sharedPref.getInt("NUMBER", -1);

        etFilm.setText(film);
        etGroup.setText(group);
        etList.setText(numberInList);
    }
}