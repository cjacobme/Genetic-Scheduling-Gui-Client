package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@FxmlView("EditPriorityDetails.fxml")
public class EditPriorityDetailsController {

    @Autowired
    private ColorService colorService;

    @Autowired
    private NumberStringConverter numberStringConverter;

    @FXML
    private TextField tfPriority;

    @FXML
    private TextField tfNumSlots;

    @FXML
    private Button btnSample;

    @FXML
    private ColorPicker cpBackground;

    @FXML
    private ColorPicker cpForeground;

    @FXML
    private TableView<TasksUiModel> tblTasks;

    @FXML
    private TableColumn<TasksUiModel, Duration> tcolDuration;

    @FXML
    private TableColumn<TasksUiModel, Integer> tcolCount;

    @FXML
    private Button btnDeleteTask;

    @FXML
    private Button btnBlaBla;

    private PriorityUiModel priorityUiModel;

    @FXML
    public void colorsChanged() {
        Color background = cpBackground.getValue();
        Color foreground = cpForeground.getValue();
        ColorPair colorPair = new ColorPair(new SimpleObjectProperty<>(foreground), new SimpleObjectProperty<>(background));
        String style = colorService.constructStyle(colorPair);
        btnSample.setStyle(style);
    }

    public void setData(PriorityUiModel priorityUiModel) {
        this.priorityUiModel = priorityUiModel;
        Bindings.bindBidirectional(tfPriority.textProperty(), this.priorityUiModel.valueProperty(), numberStringConverter);
        Bindings.bindBidirectional(tfNumSlots.textProperty(), this.priorityUiModel.valueProperty(), numberStringConverter);
        ColorPair colorPair = priorityUiModel.getColorPair();
        cpBackground.valueProperty().bindBidirectional(colorPair.backgroundProperty());
        cpForeground.valueProperty().bindBidirectional(colorPair.foregroundProperty());
        String style = colorService.constructStyle(colorPair);
        btnSample.setStyle(style);
    }

    @FXML
    public void addTask() {

    }

    @FXML
    public void editTask() {

    }

    @FXML
    public void deleteTask() {

    }

    public PriorityUiModel getPriorityUiModel() {
        return priorityUiModel;
    }
}
