package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import javafx.util.converter.NumberStringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("EditPriorityDetails.fxml")
public class EditPriorityDetailsController implements Initializable {

    @Autowired
    private ColorService colorService;

    @Autowired
    private NumberStringConverter numberStringConverter;

    @Autowired
    private ConfigurableApplicationContext context;

    @FXML
    private TextField tfPriority;

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
    private Button btnEditTask;

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
        ColorPair colorPair = priorityUiModel.getColorPair();
        cpBackground.valueProperty().bindBidirectional(colorPair.backgroundProperty());
        cpForeground.valueProperty().bindBidirectional(colorPair.foregroundProperty());
        String style = colorService.constructStyle(colorPair);
        btnSample.setStyle(style);
        tblTasks.setItems(priorityUiModel.getTasks());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcolDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tcolCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        ObservableList<TasksUiModel> selectionModel = tblTasks.getSelectionModel().getSelectedItems();
        btnDeleteTask.disableProperty().bind(Bindings.isEmpty(selectionModel));
        btnEditTask.disableProperty().bind(Bindings.isEmpty(selectionModel));
    }

    @FXML
    public void addTask() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ObservableList<TasksUiModel> items = tblTasks.getItems();
        EditTaskDetailsDialog dialog = new EditTaskDetailsDialog(context, owner, null);
        Optional<TasksUiModel> returned = dialog.showAndWait();
        if (returned.isPresent()) {
            TasksUiModel edited = returned.get();
            items.add(edited);
        }
    }

    @FXML
    public void editTask() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ObservableList<TasksUiModel> items = tblTasks.getItems();
        int selectedIndex = determineSelectedIndex();
        TasksUiModel selected = items.get(selectedIndex);
        TasksUiModel edit = new TasksUiModel(selected);
        EditTaskDetailsDialog dialog = new EditTaskDetailsDialog(context, owner, edit);
        Optional<TasksUiModel> returned = dialog.showAndWait();
        if (returned.isPresent()) {
            TasksUiModel edited = returned.get();
            items.set(selectedIndex, edited);
        }
    }

    @FXML
    public void deleteTask() {
        ObservableList<TasksUiModel> items = tblTasks.getItems();
        int selectedIndex = determineSelectedIndex();
        TasksUiModel selected = items.get(selectedIndex);
        String question = String.format("Do you really want to delete this tasks setup?%n" +
                "\tindex: %d%n" +
                "\tnumber of tasks: %d%n" +
                "\tduration: %s", selectedIndex, selected.getCount(), selected.getDuration());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);
        Optional<?> optional = alert.showAndWait();
        if (optional.isPresent()) {
            ButtonType response = alert.getResult();
            if (ButtonType.YES.equals(response)) {
                items.remove(selectedIndex);
            }
        }
    }

    private int determineSelectedIndex() {
        TableView.TableViewSelectionModel<TasksUiModel> selectionModel = tblTasks.getSelectionModel();
        int result = selectionModel.getSelectedIndex();
        return result;
    }

    public PriorityUiModel getPriorityUiModel() {
        return priorityUiModel;
    }
}
