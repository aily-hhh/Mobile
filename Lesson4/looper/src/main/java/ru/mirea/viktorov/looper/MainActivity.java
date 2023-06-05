package ru.mirea.viktorov.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText tvAge, tvWork;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAge = (EditText) findViewById(R.id.tvAge);
        tvWork = (EditText) findViewById(R.id.tvWork);

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("KEY"));
            }
        };
        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();
    }

    public void btnClick(View view) {
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("KEY", "age=" + tvAge.getText().toString().trim() + ", work=" + tvWork.getText().toString().trim());
        msg.setData(bundle);
        myLooper.mHandler.sendMessage(msg);
    }
}