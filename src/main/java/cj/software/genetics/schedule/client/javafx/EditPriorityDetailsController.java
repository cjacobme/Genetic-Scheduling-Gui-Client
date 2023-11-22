package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;

import java.time.Duration;

@FxmlView("EditPriorityDetails.fxml")
public class EditPriorityDetailsController {

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
    public void colorsChanged() {

    }

}
