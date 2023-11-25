package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class SchedulingProblemService {
    public SchedulingProblemUiModel createDefault() {

        IntegerProperty solutionCount = new SimpleIntegerProperty(100);
        IntegerProperty workerCount = new SimpleIntegerProperty(5);
        ObservableList<PriorityUiModel> priorities = createPriorities();
        IntegerProperty elitismCount = new SimpleIntegerProperty(3);
        IntegerProperty tournamentSize = new SimpleIntegerProperty(10);
        DoubleProperty mutationRate = new SimpleDoubleProperty(0.1);
        SchedulingProblemUiModel result = new SchedulingProblemUiModel(
                priorities,
                solutionCount,
                workerCount,
                elitismCount,
                tournamentSize,
                mutationRate);
        return result;
    }

    private ObservableList<PriorityUiModel> createPriorities() {
        PriorityUiModel prio0 = createPrio1();
        PriorityUiModel prio1 = createPrio2();
        PriorityUiModel prio2 = createPrio3();
        ObservableList<PriorityUiModel> result = FXCollections.observableArrayList(prio0, prio1, prio2);
        return result;
    }

    private PriorityUiModel createPrio1() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofSeconds(10)), new SimpleIntegerProperty(17));
        TasksUiModel tasks1 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofSeconds(20)), new SimpleIntegerProperty(13));
        TasksUiModel tasks2 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofMinutes(1)), new SimpleIntegerProperty(10));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0, tasks1, tasks2);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(500),
                new SimpleObjectProperty<>(new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.RED))),
                tasks);
        return result;
    }

    private PriorityUiModel createPrio2() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofSeconds(10)), new SimpleIntegerProperty(10));
        TasksUiModel tasks1 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofSeconds(30)), new SimpleIntegerProperty(20));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0, tasks1);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(2),
                new SimpleIntegerProperty(100),
                new SimpleObjectProperty<>(new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.YELLOW))),
                tasks);
        return result;
    }

    private PriorityUiModel createPrio3() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofSeconds(15)), new SimpleIntegerProperty(12));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(3),
                new SimpleIntegerProperty(150),
                new SimpleObjectProperty<>(new ColorPair(new SimpleObjectProperty<>(Color.YELLOW), new SimpleObjectProperty<>(Color.GREEN))),
                tasks);
        return result;
    }
}
