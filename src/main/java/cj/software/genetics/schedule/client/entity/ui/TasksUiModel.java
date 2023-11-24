package cj.software.genetics.schedule.client.entity.ui;

import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TasksUiModel {
    private final ObjectProperty<TimeWithUnit> duration;

    private final IntegerProperty count;

    public TasksUiModel(ObjectProperty<TimeWithUnit> duration, IntegerProperty count) {
        this.duration = duration;
        this.count = count;
    }

    /**
     * copy constructor
     */
    public TasksUiModel(TasksUiModel source) {
        this.duration = new SimpleObjectProperty<>(new TimeWithUnit(source.getDuration()));
        this.count = new SimpleIntegerProperty(source.getCount());
    }

    public TimeWithUnit getDuration() {
        return duration.get();
    }

    public ObjectProperty<TimeWithUnit> durationProperty() {
        return duration;
    }

    public void setDuration(TimeWithUnit duration) {
        this.duration.set(duration);
    }

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
