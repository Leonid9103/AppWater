package ru.appwater.feature.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.appwater.R;
import ru.appwater.common.ListAdapter;
import ru.appwater.common.WaterIntake;
import ru.appwater.feature.presentation.MainPresenter;
import ru.appwater.feature.presentation.MainView;

import static ru.appwater.common.Constants.APP_PREFERENCES;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private RecyclerView recyclerView;

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    public MainPresenter providePresenter() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(APP_PREFERENCES, this.MODE_PRIVATE);
        return new MainPresenter(sharedPreferences);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        TextView addWater = findViewById(R.id.addWater);
        TextView delWater = findViewById(R.id.delWater);
        EditText editWater = findViewById(R.id.editWater);

        addWater.setOnClickListener(v -> {
            if (editWater.length() != 0) {
                int volume = Integer.parseInt(editWater.getText().toString());
                mainPresenter.addWater(volume);
                editWater.getText().clear();
            } else {
                showToast(getString(R.string.notCorrect));
            }
        });

        delWater.setOnClickListener(v -> {
            if (editWater.length() != 0) {
                int volume = Integer.parseInt(editWater.getText().toString());
                mainPresenter.delWater(volume);
                editWater.getText().clear();
            } else {
                showToast(getString(R.string.notCorrect));
            }
        });
    }

    @Override
    public void updateView(List<WaterIntake> list, int volumeWater) {
        ListAdapter listAdapter = new ListAdapter(list);
        recyclerView.setAdapter(listAdapter);
        TextView volume = findViewById(R.id.volume);
        volume.setText(volumeWater + "");
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        TextView v = toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

}