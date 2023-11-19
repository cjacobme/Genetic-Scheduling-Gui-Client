package cj.software.genetics.schedule.client.entity.ui;

import javafx.scene.paint.Color;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public class ColorPair implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @SuppressWarnings("java:S1948")     // we won't serialize into a binary file
    private Color foreground;

    @NotNull
    @SuppressWarnings("java:S1948")     // we won't serialize into a binary file
    private Color background;

    private ColorPair() {
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected ColorPair instance;

        protected Builder() {
            instance = new ColorPair();
        }

        public ColorPair build() {
            ColorPair result = instance;
            instance = null;
            return result;
        }

        public Builder withForeground(Color foreground) {
            instance.foreground = foreground;
            return this;
        }

        public Builder withBackground(Color background) {
            instance.background = background;
            return this;
        }
    }
}