package ru.mirea.viktorov.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        SuperheroDao superheroDao = db.superheroDao();
        Superhero superhero = new Superhero();

        superhero.name = "FIBI";
        superhero.id = 0;
        superhero.magic = 57;
        superhero.mind = 72;
        superhero.power = 79;
        superhero.speed = 100;

        superheroDao.insert(superhero);
        Log.d(TAG, "Superhero insert");

        List<Superhero> superheroes = superheroDao.getAll();
        Log.d(TAG, "All count = " + superheroes.size());

        superhero = superheroDao.getById(0);
        Log.d(TAG, "getById()");

        superhero.mind = 99;
        superheroDao.update(superhero);
        Log.d(TAG, "Name = " + superhero.name +
                "\nMagic = " + superhero.magic +
                "\nMind = " + superhero.mind +
                "\nPower = " + superhero.power +
                "\nSpeed = " + superhero.speed
        );

        superheroDao.delete(superhero);
        Log.d(TAG, "Superhero deleted");
    }
}