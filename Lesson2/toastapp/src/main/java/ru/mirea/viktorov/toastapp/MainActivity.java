package ru.mirea.viktorov.toastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = editText.getText().toString().trim().length();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "«СТУДЕНТ № 5 ГРУППА БСБО-03-20 Количество символов - " + count,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}