package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TimeWithUnit {

    private final IntegerProperty time;

    private final ObjectProperty<ChronoUnit> unit;

    public TimeWithUnit(Duration source) {
        String asString = source.toString();
        int length = asString.length();
        char last = asString.charAt(length - 1);
        ChronoUnit temporalUnit = switch (last) {
            case 'S' -> ChronoUnit.SECONDS;
            case 'M' -> ChronoUnit.MINUTES;
            case 'H' -> ChronoUnit.HOURS;
            default -> throw new IllegalArgumentException("could not interpret " + asString);
        };
        this.unit = new SimpleObjectProperty<>(temporalUnit);
        int value;
        if (ChronoUnit.MINUTES.equals(temporalUnit)) {
            int seconds = (int) source.get(ChronoUnit.SECONDS);
            value = seconds / 60;
        } else {
            value = (int) source.get(temporalUnit);
        }
        this.time = new SimpleIntegerProperty(value);
    }

    @Override
    public String toString() {
        String result = String.format("%d %s", getTime(), getUnit());
        return result;
    }

    public Duration toDuration() {
        Duration result = Duration.of(getTime(), getUnit());
        return result;
    }

    public int getTime() {
        return time.get();
    }

    public IntegerProperty timeProperty() {
        return time;
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public ChronoUnit getUnit() {
        return unit.get();
    }

    public ObjectProperty<ChronoUnit> unitProperty() {
        return unit;
    }

    public void setUnit(ChronoUnit unit) {
        this.unit.set(unit);
    }
}
