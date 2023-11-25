package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SchedulingProblemService.class)
class SchedulingProblemServiceTest {

    @Autowired
    private SchedulingProblemService schedulingProblemService;

    @Test
    void metadata() {
        Service service = SchedulingProblemService.class.getAnnotation(Service.class);
        assertThat(service).as("@Service").isNotNull();
    }

    @Test
    void createDefault() {
        SchedulingProblemUiModel created = schedulingProblemService.createDefault();
        assertThat(created).as("created").isNotNull();
        ObservableList<PriorityUiModel> priorities = created.getPriorities();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(created.getSolutionCount()).as("solution count").isEqualTo(100);
        softy.assertThat(created.getWorkerCount()).as("worker count").isEqualTo(5);
        softy.assertThat(priorities).as("priorities").hasSize(3);
        softy.assertAll();
        assertPrio0(priorities.get(0));
        assertPrio1(priorities.get(1));
        assertPrio2(priorities.get(2));
    }

    private void assertPrio0(PriorityUiModel model) {
        ObservableList<TasksUiModel> tasks = model.getTasks();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(model.getValue()).as("value").isEqualTo(1);
        softy.assertThat(model.getSlotCount()).as("slot count").isEqualTo(500);
        softy.assertThat(model.getColorPair())
                .as("color pair")
                .usingRecursiveComparison()
                .isEqualTo(new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.RED)));
        softy.assertThat(tasks).as("tasks").hasSize(3);
        softy.assertAll();
        softy = new SoftAssertions();
        assertTask(softy, 0, tasks.get(0), 17, Duration.ofSeconds(10));
        assertTask(softy, 1, tasks.get(1), 13, Duration.ofSeconds(20));
        assertTask(softy, 2, tasks.get(2), 10, Duration.ofMinutes(1));
        softy.assertAll();
    }

    private void assertPrio1(PriorityUiModel model) {
        ObservableList<TasksUiModel> tasks = model.getTasks();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(model.getValue()).as("value").isEqualTo(2);
        softy.assertThat(model.getSlotCount()).as("slot count").isEqualTo(100);
        softy.assertThat(model.getColorPair())
                .as("color pair")
                .usingRecursiveComparison()
                .isEqualTo(new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.YELLOW)));
        softy.assertThat(tasks).as("tasks").hasSize(2);
        softy.assertAll();
        softy = new SoftAssertions();
        assertTask(softy, 0, tasks.get(0), 10, Duration.ofSeconds(10));
        assertTask(softy, 1, tasks.get(1), 20, Duration.ofSeconds(30));
        softy.assertAll();
    }

    private void assertPrio2(PriorityUiModel model) {
        ObservableList<TasksUiModel> tasks = model.getTasks();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(model.getValue()).as("value").isEqualTo(3);
        softy.assertThat(model.getSlotCount()).as("slot count").isEqualTo(150);
        softy.assertThat(model.getColorPair())
                .as("color pair")
                .usingRecursiveComparison()
                .isEqualTo(new ColorPair(new SimpleObjectProperty<>(Color.YELLOW), new SimpleObjectProperty<>(Color.GREEN)));
        softy.assertThat(tasks).as("tasks").hasSize(1);
        softy.assertAll();
        softy = new SoftAssertions();
        assertTask(softy, 0, tasks.get(0), 12, Duration.ofSeconds(15));
        softy.assertAll();
    }

    private void assertTask(SoftAssertions softy, int index, TasksUiModel model, int expCount, Duration expDuration) {
        softy.assertThat(model.getCount()).as("count #%d", index).isEqualTo(expCount);
        softy.assertThat(model.getDuration().toDuration()).as("duration #%d", index).isEqualTo(expDuration);
    }
}
