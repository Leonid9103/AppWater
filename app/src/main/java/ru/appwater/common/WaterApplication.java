package ru.appwater.common;

import android.app.Application;

import androidx.room.Room;

public class WaterApplication extends Application {
    public static WaterApplication instance;
    private DatabaseWater databaseWater;

    @Override
    public void onCreate(){
        super.onCreate();

        instance = this;
        databaseWater = Room.databaseBuilder(this, DatabaseWater.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static WaterApplication getInstance(){return instance;}

    public DatabaseWater getDatabaseWater(){return databaseWater;}
}
