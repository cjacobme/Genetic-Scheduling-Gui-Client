package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class ColorPair {

    private final ObjectProperty<Color> foreground;

    private final ObjectProperty<Color> background;

    public ColorPair(ColorPair source) {
        this.foreground = new SimpleObjectProperty<>(source.getForeground());
        this.background = new SimpleObjectProperty<>(source.getBackground());
    }

    public ColorPair(ObjectProperty<Color> foreground, ObjectProperty<Color> background) {
        this.foreground = foreground;
        this.background = background;
    }

    public Color getForeground() {
        return foreground.get();
    }

    public ObjectProperty<Color> foregroundProperty() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground.set(foreground);
    }

    public Color getBackground() {
        return background.get();
    }

    public ObjectProperty<Color> backgroundProperty() {
        return background;
    }

    public void setBackground(Color background) {
        this.background.set(background);
    }
}