package ru.mirea.viktorov.favoritebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    EditText etUserBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etUserBook = findViewById(R.id.etUserBook);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView tvBookDev = findViewById(R.id.tvBookDev);
            String book = extras.getString(MainActivity.KEY);
            tvBookDev.setText(String.format("Любимая книга разработчика - %s", book));
        }
    }

    public void sendInfoAboutBook(View view) {
        String book = etUserBook.getText().toString().trim();
        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, book);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}