package cj.software.genetics.schedule.client.javafx.control;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class FillSetter implements AutoCloseable {

    private final GraphicsContext gc;

    private final Paint oldFill;

    public FillSetter(GraphicsContext gc, Paint newFill) {
        this.gc = gc;
        this.oldFill = gc.getFill();
        gc.setFill(newFill);
    }

    @Override
    public void close() {
        gc.setFill(oldFill);
    }
}
