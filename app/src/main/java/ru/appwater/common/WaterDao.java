package ru.appwater.common;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WaterDao {
    @Insert
    void insertAll(WaterIntake waterIntake);

    @Update
    void updateAll(WaterIntake waterIntake);

    @Delete
    void deleteAll(WaterIntake waterIntake);

    @Query("SELECT * FROM waterIntake")
    List<WaterIntake> getAll();

}
