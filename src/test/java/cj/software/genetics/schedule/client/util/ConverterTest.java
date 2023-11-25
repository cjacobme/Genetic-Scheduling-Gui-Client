package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.ProblemPriority;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingProblem;
import cj.software.genetics.schedule.api.entity.SolutionSetup;
import cj.software.genetics.schedule.api.entity.Task;
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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Converter.class, SchedulingProblemService.class})
class ConverterTest {

    @Autowired
    private SchedulingProblemService schedulingProblemService;

    @Autowired
    private Converter converter;

    @Test
    void metadata() {
        Service service = Converter.class.getAnnotation(Service.class);
        assertThat(service).as("@Service").isNotNull();
    }

    @Test
    void convertStandard() {
        SchedulingProblemUiModel uiModel = schedulingProblemService.createDefault();

        SchedulingCreatePostInput converted = converter.toSchedulingProblemPostInput(uiModel);

        assertThat(converted).as("converted").isNotNull();
        SchedulingProblem schedulingProblem = converted.getSchedulingProblem();
        SolutionSetup solutionSetup = converted.getSolutionSetup();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(schedulingProblem).as("scheduling problem").isNotNull();
        softy.assertThat(solutionSetup).as("solution setup").isNotNull();
        softy.assertAll();
        assertSolutionSetup(solutionSetup, 100, 5);
        assertPrioritiesStandard(schedulingProblem);
    }

    @Test
    void convertOther() {
        SchedulingProblemUiModel uiModel = createOtherModel();

        SchedulingCreatePostInput converted = converter.toSchedulingProblemPostInput(uiModel);
        SchedulingProblem schedulingProblem = converted.getSchedulingProblem();
        SolutionSetup solutionSetup = converted.getSolutionSetup();
        assertSolutionSetup(solutionSetup, 80, 2);
        assertPrioritiesOther(schedulingProblem);
    }

    private SchedulingProblemUiModel createOtherModel() {
        IntegerProperty solutionsCount = new SimpleIntegerProperty(80);
        IntegerProperty workersCount = new SimpleIntegerProperty(2);
        PriorityUiModel priorityUiModel = createOtherPrio();
        ObservableList<PriorityUiModel> priorities = FXCollections.observableArrayList(priorityUiModel);
        IntegerProperty elitismCount = new SimpleIntegerProperty(2);
        IntegerProperty tournamentSize = new SimpleIntegerProperty(6);
        DoubleProperty mutationRate = new SimpleDoubleProperty(0.4);

        SchedulingProblemUiModel result = new SchedulingProblemUiModel(
                priorities, solutionsCount, workersCount, elitismCount, tournamentSize, mutationRate);
        return result;
    }

    private PriorityUiModel createOtherPrio() {
        TasksUiModel tasks0 = new TasksUiModel(new SimpleObjectProperty<>(TimeWithUnit.ofDays(2)), new SimpleIntegerProperty(4));
        ObservableList<TasksUiModel> tasks = FXCollections.observableArrayList(tasks0);
        PriorityUiModel result = new PriorityUiModel(
                new SimpleIntegerProperty(7),
                new SimpleIntegerProperty(150),
                new SimpleObjectProperty<>(new ColorPair(new SimpleObjectProperty<>(Color.CYAN), new SimpleObjectProperty<>(Color.ORANGE))),
                tasks);
        return result;
    }

    private void assertSolutionSetup(SolutionSetup solutionSetup, int expSolutionsCount, int expWorkersCount) {
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(solutionSetup.getSolutionCount()).as("solution count").isEqualTo(expSolutionsCount);
        softy.assertThat(solutionSetup.getWorkersPerSolutionCount()).as("workers count").isEqualTo(expWorkersCount);
        softy.assertAll();
    }

    private void assertPrioritiesStandard(SchedulingProblem schedulingProblem) {
        SortedSet<ProblemPriority> priorities = schedulingProblem.getPriorities();
        assertThat(priorities).hasSize(3);
        List<ProblemPriority> asList = new ArrayList<>(priorities);
        assertStandard0(asList.get(0));
        assertStandard1(asList.get(1));
        assertStandard2(asList.get(2));
    }

    private void assertStandard0(ProblemPriority problemPriority) {
        Collection<Task> tasks = problemPriority.getTasks();
        List<Task> asList = new ArrayList<>(tasks);
        asList.sort((task1, task2) -> {
            CompareToBuilder builder = new CompareToBuilder()
                    .append(task1.getIdentifier(), task2.getIdentifier());
            int result = builder.build();
            return result;
        });
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(problemPriority.getValue()).isEqualTo(1);
        softy.assertThat(problemPriority.getTasks()).hasSize(40);
        softy.assertThat(problemPriority.getSlotCount()).isEqualTo(500);
        softy.assertAll();
        softy = new SoftAssertions();
        TimeWithUnit seconds10 = TimeWithUnit.ofSeconds(10);
        TimeWithUnit seconds20 = TimeWithUnit.ofSeconds(20);
        TimeWithUnit munites1 = TimeWithUnit.ofMinutes(1);
        for (int i = 0; i < 17; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 1);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(seconds10);
        }
        for (int i = 18; i < 30; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 1);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(seconds20);
        }
        for (int i = 30; i < 40; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 1);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(munites1);
        }
        softy.assertAll();
    }

    private void assertStandard1(ProblemPriority problemPriority) {
        Collection<Task> tasks = problemPriority.getTasks();
        List<Task> asList = new ArrayList<>(tasks);
        asList.sort((task1, task2) -> {
            CompareToBuilder builder = new CompareToBuilder()
                    .append(task1.getIdentifier(), task2.getIdentifier());
            int result = builder.build();
            return result;
        });
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(problemPriority.getValue()).isEqualTo(2);
        softy.assertThat(problemPriority.getTasks()).hasSize(30);
        softy.assertThat(problemPriority.getSlotCount()).isEqualTo(100);
        softy.assertAll();
        TimeWithUnit seconds10 = TimeWithUnit.ofSeconds(10);
        TimeWithUnit seconds30 = TimeWithUnit.ofSeconds(30);
        softy = new SoftAssertions();
        for (int i = 0; i < 10; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 41);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(seconds10);
        }
        for (int i = 10; i < 30; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 41);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(seconds30);
        }
        softy.assertAll();
    }

    private void assertStandard2(ProblemPriority problemPriority) {
        Collection<Task> tasks = problemPriority.getTasks();
        List<Task> asList = new ArrayList<>(tasks);
        asList.sort((task1, task2) -> {
            CompareToBuilder builder = new CompareToBuilder()
                    .append(task1.getIdentifier(), task2.getIdentifier());
            int result = builder.build();
            return result;
        });
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(problemPriority.getValue()).isEqualTo(3);
        softy.assertThat(problemPriority.getTasks()).hasSize(12);
        softy.assertAll();
        TimeWithUnit seconds15 = TimeWithUnit.ofSeconds(15);
        softy = new SoftAssertions();
        for (int i = 0; i < 12; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 71);
            softy.assertThat(task.getDuration()).as("duration #%d", i).isEqualTo(seconds15);
        }
        softy.assertAll();
    }

    private void assertPrioritiesOther(SchedulingProblem schedulingProblem) {
        SortedSet<ProblemPriority> priorities = schedulingProblem.getPriorities();
        assertThat(priorities).hasSize(1);
        ProblemPriority first = priorities.first();
        assertOther(first);
    }

    private void assertOther(ProblemPriority problemPriority) {
        Collection<Task> tasks = problemPriority.getTasks();
        List<Task> asList = new ArrayList<>(tasks);
        asList.sort((task1, task2) -> {
            CompareToBuilder builder = new CompareToBuilder()
                    .append(task1.getIdentifier(), task2.getIdentifier());
            int result = builder.build();
            return result;
        });
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(problemPriority.getValue()).as("priority value").isEqualTo(7);
        softy.assertThat(problemPriority.getTasks()).as("tasks").hasSize(4);
        softy.assertAll();
        TimeWithUnit days2 = TimeWithUnit.ofDays(2);
        softy = new SoftAssertions();
        for (int i = 0; i < 4; i++) {
            Task task = asList.get(i);
            softy.assertThat(task.getIdentifier()).as("identifier #%d", i).isEqualTo(i + 1);
            softy.assertThat(task.getDuration()).as("duriation #%d", i).isEqualTo(days2);
        }
        softy.assertAll();
    }

    @Test
    void uiPrioritiesToMapStandard() {
        SchedulingProblemUiModel schedulingProblemUiModel = schedulingProblemService.createDefault();
        Map<Integer, ColorPair> expected = Map.of(
                1, new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.RED)),
                2, new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.YELLOW)),
                3, new ColorPair(new SimpleObjectProperty<>(Color.YELLOW), new SimpleObjectProperty<>(Color.GREEN)));
        assertUiPrioritiesToMap(schedulingProblemUiModel, expected);
    }

    @Test
    void uiPrioritiesToMapOther() {
        SchedulingProblemUiModel schedulingProblemUiModel = createOtherModel();
        Map<Integer, ColorPair> expected = Map.of(
                7, new ColorPair(new SimpleObjectProperty<>(Color.CYAN), new SimpleObjectProperty<>(Color.ORANGE)));
        assertUiPrioritiesToMap(schedulingProblemUiModel, expected);
    }

    private void assertUiPrioritiesToMap(SchedulingProblemUiModel schedulingProblemUiModel, Map<Integer, ColorPair> expected) {
        Map<Integer, ColorPair> actual = converter.toPriorityColorPairMap(schedulingProblemUiModel);
        SoftAssertions softy = new SoftAssertions();
        for (Integer expKey : expected.keySet()) {
            softy.assertThat(actual).containsKey(expKey);
        }
        softy.assertAll();
        softy = new SoftAssertions();
        for (Map.Entry<Integer, ColorPair> expEntry : expected.entrySet()) {
            Integer key = expEntry.getKey();
            ColorPair expValue = expEntry.getValue();
            ColorPair actValue = actual.get(key);
            softy.assertThat(actValue.getForeground()).as("Foreground for %d", key).isEqualTo(expValue.getForeground());
            softy.assertThat(actValue.getBackground()).as("Background for %d", key).isEqualTo(expValue.getBackground());
        }
        softy.assertAll();
        assertThat(actual).hasSize(expected.size());
    }
}
