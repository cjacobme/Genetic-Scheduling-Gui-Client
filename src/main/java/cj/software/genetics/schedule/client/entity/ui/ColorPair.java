package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public boolean equals(Object otherObject) {
        boolean result;
        if (otherObject instanceof ColorPair other) {
            EqualsBuilder builder = new EqualsBuilder()
                    .append(this.getBackground(), other.getBackground())
                    .append(this.getForeground(), other.getForeground());
            result = builder.build();
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
                .append(this.getBackground())
                .append(this.getForeground());
        int result = builder.build();
        return result;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("foreground", getForeground())
                .append("background", getBackground());
        String result = builder.build();
        return result;
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