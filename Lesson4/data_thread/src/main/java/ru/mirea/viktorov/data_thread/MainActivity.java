package ru.mirea.viktorov.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button runOnUiThread = (Button) findViewById(R.id.runOnUiThread);
        Button postDelayed = (Button) findViewById(R.id.postDelayed);
        Button post = (Button) findViewById(R.id.post);

        runOnUiThread.setOnClickListener((view) -> {
            Thread thread = new Thread(() -> {
                // Запускается задачу в потоке пользовательского
                // интерфейса (в главном потоке в данном случае).
                // Если текущий поток им и является, то задача сразу же и выполняется,
                // но если происходит вызов из другого потока, то задача помещается в очередь
                runOnUiThread(() -> textView.setText("Запускает задачу в потоке пользовательского\n" +
                        "интерфейса (в главном потоке в данном случае).\n" +
                        "Если текущий поток им и является, то задача сразу же и выполняется,\n" +
                        "но если происходит вызов из другого потока, то задача помещается в очередь"));
            });
            thread.start();
        });

        postDelayed.setOnClickListener((view) -> {
            Thread thread = new Thread(() -> {
                // Добавляет задачу в очередь для запуска (задача запустится как можно скорее)
                textView.post(() -> textView.setText("Добавляет задачу в очередь для запуска (задача запустится как можно скорее)"));
            });
            thread.start();
        });

        post.setOnClickListener((view) -> {
            Thread thread = new Thread(() -> {
                // Добавляет задачу в очередь сообщений для запуска
                // по истечении указанного количества времени
                textView.postDelayed(() -> textView.setText("Добавляет задачу в очередь сообщений для запуска\n" +
                        "по истечении указанного количества времени"), 2000);
            });
            thread.start();
        });
    }
}