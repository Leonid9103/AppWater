package ru.appwater.common;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WaterIntake.class},version = 2,exportSchema = false)
public abstract class DatabaseWater extends RoomDatabase {
    public abstract WaterDao waterDao();
}
