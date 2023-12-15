package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PriorityUiModel {

    private final IntegerProperty value;

    private final ObjectProperty<ColorPair> colorPair;

    private ObservableList<TasksUiModel> tasks;

    public PriorityUiModel(IntegerProperty value, ObjectProperty<ColorPair> colorPair, ObservableList<TasksUiModel> tasks) {
        this.value = value;
        this.colorPair = colorPair;
        this.tasks = tasks;
    }

    /**
     * copy constructor
     */
    public PriorityUiModel(PriorityUiModel source) {
        this.value = new SimpleIntegerProperty(source.getValue());
        this.colorPair = new SimpleObjectProperty<>(new ColorPair(source.getColorPair()));
        this.tasks = copyTasks(source.getTasks());
    }

    private ObservableList<TasksUiModel> copyTasks(ObservableList<TasksUiModel> source) {
        ObservableList<TasksUiModel> result = FXCollections.observableArrayList();
        for (TasksUiModel sourceEntry : source) {
            TasksUiModel copy = new TasksUiModel(sourceEntry);
            result.add(copy);
        }
        return result;
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
