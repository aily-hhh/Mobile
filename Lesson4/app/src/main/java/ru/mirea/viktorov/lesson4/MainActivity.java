package ru.mirea.viktorov.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ru.mirea.viktorov.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Boolean isAdd = true;
    private Boolean isFavorite = false;
    private Boolean isMix = false;
    private Boolean isPlay = false;
    private int repeat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd) {
                    binding.imgAddRemove.setImageResource(R.drawable.ic_close);
                } else {
                    binding.imgAddRemove.setImageResource(R.drawable.ic_add);
                }
                isAdd = !isAdd;
            }
        });

        binding.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite_border);
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.ic_favorite);
                }
                isFavorite = !isFavorite;
            }
        });

        binding.imgMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMix) {
                    binding.imgMix.setBackgroundColor(Color.rgb(255, 255, 255));
                } else {
                    binding.imgMix.setBackgroundColor(Color.rgb(180, 180, 180));
                }
                isMix = !isMix;
            }
        });

        binding.imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (repeat) {
                    case 0:
                        binding.imgRepeat.setBackgroundColor(Color.WHITE);
                        binding.imgRepeat.setImageResource(R.drawable.ic_repeat);
                        break;

                    case 1:
                        binding.imgRepeat.setBackgroundColor(Color.rgb(180, 180, 180));
                        break;

                    case 2:
                        binding.imgRepeat.setImageResource(R.drawable.ic_repeat_one);
                        break;
                }
                repeat = (repeat + 1) % 3;
            }
        });

        binding.fabPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {
                    binding.fabPlayStop.setImageResource(R.drawable.ic_play);
                } else {
                    binding.fabPlayStop.setImageResource(R.drawable.ic_pause);
                }
                isPlay = !isPlay;
            }
        });

        binding.fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Next", Toast.LENGTH_SHORT).show();
            }
        });

        binding.fabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Previous", Toast.LENGTH_SHORT).show();
            }
        });
    }
}