package cj.software.genetics.schedule.client.entity.ui;

import javafx.scene.paint.Color;

public class ColorPairBuilder extends ColorPair.Builder {
    public ColorPairBuilder() {
        super.withBackground(Color.ORANGE)
                .withForeground(Color.BLACK);
    }
}
