package ru.appwater.feature.presentation;

import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.appwater.common.DatabaseWater;
import ru.appwater.common.WaterApplication;
import ru.appwater.common.WaterDao;
import ru.appwater.common.WaterIntake;

import static ru.appwater.common.Constants.PREFERENCES_VOLUME;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private final android.content.SharedPreferences sharedPreferences;
    private int volumeWater;
    private List<WaterIntake> list = new ArrayList<>();

    public MainPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        load();
    }

    public ArrayList<String> date() {

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        ArrayList<String> date = new ArrayList<>();
        date.add(dateText);
        date.add(timeText);

        return date;
    }

    public void addWater(int value) {
        volumeWater = sharedPreferences.getInt(PREFERENCES_VOLUME, 0);
        volumeWater += value;
        list.add(new WaterIntake(date().get(1), date().get(0), "" + value));
        getViewState().updateView(list, volumeWater);
        update(volumeWater, "" + value);
    }

    public void delWater(int value) {
        volumeWater = sharedPreferences.getInt(PREFERENCES_VOLUME, 0);
        if (volumeWater == 0) {
            getViewState().showToast("Количество выпитой воды не записано, вычитать не из чего");
        } else {
            if ((volumeWater -= value) < 0) {
                getViewState().showToast("Количество выпитой воды меньше, чем вычитаемый объем");
            } else {
                list.add(new WaterIntake(date().get(1), date().get(0), "-" + value));
                getViewState().updateView(list, volumeWater);
                update(volumeWater, "-" + value);
            }
        }
    }

    private void update(int volumeWater, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFERENCES_VOLUME, volumeWater);
        editor.apply();

        new Thread(() -> {
            DatabaseWater databaseWater = WaterApplication.getInstance().getDatabaseWater();
            WaterDao waterDao = databaseWater.waterDao();
            WaterIntake waterIntake = new WaterIntake(date().get(1), date().get(0), value);
            waterIntake.setTime(date().get(1));
            waterIntake.setDate(date().get(0));
            waterIntake.setVolume(value);
            waterDao.insertAll(waterIntake);
        }).start();
    }

    private void load() {
        int volumeWater = sharedPreferences.getInt(PREFERENCES_VOLUME, 0);
        if (volumeWater != 0) {
            new Thread(() -> {
                List<WaterIntake> waterIntakesDb = getAll();
                getViewState().updateView(waterIntakesDb, volumeWater);
            }).start();
        }
    }

    private List<WaterIntake> getAll() {
        return WaterApplication.getInstance().getDatabaseWater().waterDao().getAll();
    }
}
