package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import cj.software.genetics.schedule.client.javafx.control.ColorsTableCellFactory;
import cj.software.genetics.schedule.client.javafx.control.TasksSubTableCellFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
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
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("EditSchedulingProblem.fxml")
public class EditSchedulingProblemController implements Initializable {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private NumberStringConverter numberStringConverter;

    @FXML
    private TableView<PriorityUiModel> tblPriorities;

    @FXML
    private TableColumn<PriorityUiModel, IntegerProperty> tcolPriorityValue;

    @FXML
    private TableColumn<PriorityUiModel, ColorPair> tcolColors;

    @FXML
    private TableColumn<PriorityUiModel, ObservableList<TasksUiModel>> tcolTasks;

    @FXML
    private TableColumn<PriorityUiModel, IntegerProperty> tcolSlotCount;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField tfSolutionsCount;

    @FXML
    private TextField tfWorkersCount;

    @FXML
    private TextField tfElitismCount;

    @FXML
    private TextField tfTournamentSize;

    @FXML
    private Spinner<Double> spMutationRate;

    private SchedulingProblemUiModel model;

    public void setModel(SchedulingProblemUiModel model) {
        this.model = model;
        tblPriorities.setItems(model.getPriorities());
        Bindings.bindBidirectional(tfSolutionsCount.textProperty(), model.solutionCountProperty(), numberStringConverter);
        Bindings.bindBidirectional(tfWorkersCount.textProperty(), model.workerCountProperty(), numberStringConverter);
        Bindings.bindBidirectional(tfElitismCount.textProperty(), model.elitismCountProperty(), numberStringConverter);
        Bindings.bindBidirectional(tfTournamentSize.textProperty(), model.tournamentSizeProperty(), numberStringConverter);
        spMutationRate.getValueFactory().valueProperty().bindBidirectional(model.mutationRateProperty().asObject());
    }

    public SchedulingProblemUiModel getModel() {
        SchedulingProblemUiModel result = this.model;
        /*
        because there is a bug in JavaFX's spinner which does not bind the values properly when the upper/lower limit
        is selected:
         */
        double mutationRate = spMutationRate.getValue();
        result.setMutationRate(mutationRate);
        return result;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcolPriorityValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tcolColors.setCellFactory(new ColorsTableCellFactory());
        tcolTasks.setCellFactory(new TasksSubTableCellFactory());
        tcolSlotCount.setCellValueFactory(new PropertyValueFactory<>("slotCount"));
        btnDelete.disableProperty().bind(Bindings.isEmpty(tblPriorities.getSelectionModel().getSelectedItems()));
        btnEdit.disableProperty().bind(Bindings.isEmpty(tblPriorities.getSelectionModel().getSelectedItems()));
        TextField spinnerTf = spMutationRate.editorProperty().get();
        spinnerTf.setAlignment(Pos.CENTER_RIGHT);
        spinnerTf.setFont(tfTournamentSize.getFont());
    }

    @FXML
    public void addPriority() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ObservableList<PriorityUiModel> items = tblPriorities.getItems();
        int size = items.size();
        IntegerProperty valueProperty = new SimpleIntegerProperty(size + 1);
        IntegerProperty slotCountProperty = new SimpleIntegerProperty();
        ColorPair colorPair = new ColorPair(new SimpleObjectProperty<>(Color.BLACK), new SimpleObjectProperty<>(Color.WHITE));
        ObjectProperty<ColorPair> colorPairProperty = new SimpleObjectProperty<>(colorPair);
        ObservableList<TasksUiModel> tasksList = FXCollections.observableArrayList();
        PriorityUiModel priorityUiModel = new PriorityUiModel(
                valueProperty,
                slotCountProperty,
                colorPairProperty,
                tasksList);
        EditPriorityDetailsDialog dialog = new EditPriorityDetailsDialog(applicationContext, owner, priorityUiModel);
        Optional<PriorityUiModel> returned = dialog.showAndWait();
        if (returned.isPresent()) {
            PriorityUiModel edited = returned.get();
            items.add(edited);
        }
    }

    @FXML
    public void editPriority() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        ObservableList<PriorityUiModel> items = tblPriorities.getItems();
        int selectedIndex = determineSelectedIndex();
        PriorityUiModel selected = items.get(selectedIndex);
        PriorityUiModel edit = new PriorityUiModel(selected);
        EditPriorityDetailsDialog dialog = new EditPriorityDetailsDialog(applicationContext, owner, edit);
        Optional<PriorityUiModel> returned = dialog.showAndWait();
        if (returned.isPresent()) {
            PriorityUiModel edited = returned.get();
            items.set(selectedIndex, edited);
        }
    }

    private PriorityUiModel determineSelected() {
        TableView.TableViewSelectionModel<PriorityUiModel> selectionModel = tblPriorities.getSelectionModel();
        PriorityUiModel result = selectionModel.getSelectedItem();
        return result;
    }

    private int determineSelectedIndex() {
        TableView.TableViewSelectionModel<PriorityUiModel> selectionModel = tblPriorities.getSelectionModel();
        int result = selectionModel.getSelectedIndex();
        return result;
    }

    @FXML
    public void deletePriority() {
        PriorityUiModel selected = determineSelected();
        String question = String.format("Do you really want to delete this priority with value %d?", selected.getValue());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);
        Optional<?> optional = alert.showAndWait();
        if (optional.isPresent()) {
            ButtonType response = alert.getResult();
            if (ButtonType.YES.equals(response)) {
                ObservableList<PriorityUiModel> items = tblPriorities.getItems();
                selected.getTasks().clear();
                items.remove(selected);
                int priority = 1;
                for (PriorityUiModel item : items) {
                    item.setValue(priority);
                    priority++;
                }
            }
        }
    }
}
