package ru.mirea.viktorov.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Superhero {
    @PrimaryKey()
    public long id;
    public String name;
    public int magic;
    public int mind;
    public int power;
    public int speed;
}
