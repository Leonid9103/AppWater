package ru.appwater.feature.presentation;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.appwater.common.WaterIntake;

public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateView(List<WaterIntake> list, int volumeWater);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToast(String message);
}
