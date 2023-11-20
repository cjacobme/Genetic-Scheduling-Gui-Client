package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public class PriorityUiModel {

    private final IntegerProperty value;

    private final IntegerProperty slotCount;

    private final ObjectProperty<ColorPair> colorPair;

    private ObservableList<TasksUiModel> tasks;

    public PriorityUiModel(IntegerProperty value, IntegerProperty slotCount, ObjectProperty<ColorPair> colorPair, ObservableList<TasksUiModel> tasks) {
        this.value = value;
        this.slotCount = slotCount;
        this.colorPair = colorPair;
        this.tasks = tasks;
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getSlotCount() {
        return slotCount.get();
    }

    public IntegerProperty slotCountProperty() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount.set(slotCount);
    }

    public ColorPair getColorPair() {
        return colorPair.get();
    }

    public ObjectProperty<ColorPair> colorPairProperty() {
        return colorPair;
    }

    public void setColorPair(ColorPair colorPair) {
        this.colorPair.set(colorPair);
    }

    public ObservableList<TasksUiModel> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<TasksUiModel> tasks) {
        this.tasks = tasks;
    }
}
