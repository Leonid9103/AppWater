package ru.appwater.common;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WaterIntake {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long id;

    public String time;
    public String date;
    public String volume;

    public WaterIntake(String time, String date, String volume) {
        this.time = time;
        this.date = date;
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
