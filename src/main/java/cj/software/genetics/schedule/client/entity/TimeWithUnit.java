package cj.software.genetics.schedule.client.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeWithUnit {

    private static final Pattern PATTERN = Pattern.compile("^PT(\\d+)(S|H|M)$");

    private final IntegerProperty time;

    private final ObjectProperty<ChronoUnit> unit;

    public TimeWithUnit(Duration source) {
        String asString = source.toString();
        Matcher matcher = PATTERN.matcher(asString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("could not interpret " + asString);
        }
        String valueStr = matcher.group(1);
        int value = Integer.parseInt(valueStr);
        String unitString = matcher.group(2);
        ChronoUnit chronoUnit = switch (unitString) {
            case "S" -> ChronoUnit.SECONDS;
            case "M" -> ChronoUnit.MINUTES;
            case "H" -> ChronoUnit.HOURS;
            default -> throw new IllegalArgumentException("unknown: " + unitString);
        };
        this.time = new SimpleIntegerProperty(value);
        this.unit = new SimpleObjectProperty<>(chronoUnit);
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
