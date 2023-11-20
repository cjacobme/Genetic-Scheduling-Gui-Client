package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.time.Duration;

public class TasksUiModel {
    private final ObjectProperty<Duration> duration;

    private final IntegerProperty count;

    public TasksUiModel(ObjectProperty<Duration> duration, IntegerProperty count) {
        this.duration = duration;
        this.count = count;
    }

    public Duration getDuration() {
        return duration.get();
    }

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    }

    public void setDuration(Duration duration) {
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
