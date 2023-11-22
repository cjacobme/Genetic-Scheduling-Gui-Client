package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.client.entity.TimeWithUnit;
import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SchedulingProblemService {
    public SchedulingProblemUiModel createDefault() {

        IntegerProperty solutionCount = new SimpleIntegerProperty(100);
        IntegerProperty workerCount = new SimpleIntegerProperty(5);
        ObservableList<PriorityUiModel> priorities = createPriorities();
        SchedulingProblemUiModel result = new SchedulingProblemUiModel(priorities, solutionCount, workerCount);
        return result;
    }

    private ObservableList<PriorityUiModel> createPriorities() {
        PriorityUiModel prio0 = createPrio0();
        PriorityUiModel prio1 = createPrio1();
        PriorityUiModel prio2 = createPrio2();
        ObservableList<PriorityUiModel> result = FXCollections.observableArrayList(prio0, prio1, prio2);
        return result;
    }

    private PriorityUiModel createPrio0() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofSeconds(10))), new SimpleIntegerProperty(17));
        TasksUiModel tasks1 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofSeconds(20))), new SimpleIntegerProperty(13));
        TasksUiModel tasks2 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofMinutes(1))), new SimpleIntegerProperty(10));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0, tasks1, tasks2);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(0),
                new SimpleIntegerProperty(500),
                new SimpleObjectProperty<>(ColorPair.builder().withBackground(Color.RED).withForeground(Color.BLACK).build()),
                tasks);
        return result;
    }

    private PriorityUiModel createPrio1() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofSeconds(10))), new SimpleIntegerProperty(10));
        TasksUiModel tasks1 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofSeconds(30))), new SimpleIntegerProperty(20));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0, tasks1);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(1),
                new SimpleIntegerProperty(100),
                new SimpleObjectProperty<>(ColorPair.builder().withBackground(Color.YELLOW).withForeground(Color.BLACK).build()),
                tasks);
        return result;
    }

    private PriorityUiModel createPrio2() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(new TimeWithUnit(Duration.ofSeconds(15))), new SimpleIntegerProperty(12));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(2),
                new SimpleIntegerProperty(150),
                new SimpleObjectProperty<>(ColorPair.builder().withBackground(Color.GREEN).withForeground(Color.YELLOW).build()),
                tasks);
        return result;
    }
}
