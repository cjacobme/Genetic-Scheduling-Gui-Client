package cj.software.genetics.schedule.client.javafx.control;

import cj.software.genetics.schedule.api.entity.Solution;
import cj.software.genetics.schedule.api.entity.SolutionPriority;
import cj.software.genetics.schedule.api.entity.Task;
import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import cj.software.genetics.schedule.api.entity.Worker;
import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.javafx.ColorService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

public class SolutionPane extends Pane {

    private static final int LINE_GAP = 3;

    private static final double WORKER_LABEL_WIDTH = 50;

    private static final int ROW_HEIGHT = 35;

    private final Canvas canvas = new Canvas();

    private final IntegerProperty scale = new SimpleIntegerProperty(5);

    private final ObjectProperty<Solution> solution = new SimpleObjectProperty<>();

    private final ColorService colorService;

    private final List<Node> myChildren = new ArrayList<>();

    private Map<Integer, ColorPair> priorityColors;

    private final StringProperty status = new SimpleStringProperty();

    public SolutionPane(ColorService colorService) {
        this.colorService = colorService;
        getChildren().add(canvas);
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    public int getScale() {
        return scale.get();
    }

    public IntegerProperty scaleProperty() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale.set(scale);
        draw();
    }

    public Solution getSolution() {
        return solution.get();
    }

    public ObjectProperty<Solution> solutionProperty() {
        return solution;
    }

    public void setSolution(Solution solution, Map<Integer, ColorPair> priorityColors) {
        deleteOldChildren();
        this.priorityColors = priorityColors;
        this.solution.set(solution);
        draw();
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        try (FillSetter fillSetter = new FillSetter(gc, Color.LIGHTGRAY)) {
            gc.fillRect(0, 0, canvasWidth, canvasHeight);
            Solution sol = getSolution();
            if (sol != null) {
                drawSolution();
            }
        }
    }

    private void drawSolution() {
        double posY = 0.0;
        int index = 0;
        ObservableList<Node> children = getChildren();
        Solution local = getSolution();
        List<Worker> workers = local.getWorkers();
        for (Worker worker : workers) {
            Label workerLabel = createWorkerLabel(index, posY);
            children.add(workerLabel);
            myChildren.add(workerLabel);
            draw(worker.getPriorities(), posY, children);
            posY += ROW_HEIGHT + LINE_GAP;
            index++;
        }
    }

    private void draw(SortedSet<SolutionPriority> priorities, double posY, ObservableList<Node> children) {
        double posX = WORKER_LABEL_WIDTH + 20;
        int totalDuration = 0;
        DrawSolutionResult drawSolutionResult = new DrawSolutionResult(posX, totalDuration);
        for (SolutionPriority solutionPriority : priorities) {
            drawSolutionResult = draw(solutionPriority, drawSolutionResult, posY, children);
        }
    }

    private record DrawSolutionResult(double posX, int totalDurationSeconds) {

    }

    private DrawSolutionResult draw(SolutionPriority priority, DrawSolutionResult drawSolutionResult, double posY, ObservableList<Node> children) {
        double posX = drawSolutionResult.posX();
        int totalDuration = drawSolutionResult.totalDurationSeconds();
        SortedMap<Integer, Task> tasks = priority.getTasks();
        int priorityValue = priority.getValue();
        ColorPair colorPair = priorityColors.get(priorityValue);
        String style = colorService.constructStyle(colorPair);
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            Task task = entry.getValue();
            int identifier = task.getIdentifier();
            TimeWithUnit duration = task.getDuration();
            String text = String.format("%d (%s)", identifier, duration);
            Button button = new Button(text);
            button.setStyle(style);
            int durationSeconds = duration.toSeconds();
            totalDuration += durationSeconds;
            double width = durationSeconds * (double) getScale();
            button.setLayoutX(posX);
            button.setLayoutY(posY);
            button.setMinWidth(width);
            button.setPrefWidth(width);
            button.setMaxWidth(width);
            children.add(button);
            myChildren.add(button);
            int finalTotalDuration = totalDuration;
            button.setOnAction(event -> {
                StringBuilder sb = new StringBuilder(task.toString())
                        .append(String.format(" end / s = %d", finalTotalDuration));
                String message = sb.toString();
                setStatus(message);
            });
            posX += width;
        }
        DrawSolutionResult result = new DrawSolutionResult(posX, totalDuration);
        return result;
    }

    private Label createWorkerLabel(int index, double posY) {
        String text = String.format("Worker %d", index);
        Label result = new Label(text);
        result.setLayoutX(10);
        result.setLayoutY(posY);
        result.setMaxWidth(WORKER_LABEL_WIDTH);
        result.setMinWidth(WORKER_LABEL_WIDTH);
        result.setPrefWidth(WORKER_LABEL_WIDTH);
        result.setMaxHeight(ROW_HEIGHT);
        result.setMinHeight(ROW_HEIGHT);
        result.setPrefHeight(ROW_HEIGHT);
        return result;
    }

    private void deleteOldChildren() {
        ObservableList<Node> children = super.getChildren();
        for (Node child : myChildren) {
            children.remove(child);
        }
    }
}
