package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("EditSchedulingProblem.fxml")
public class EditSchedulingProblemController implements Initializable {

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

    private SchedulingProblemUiModel model;

    public void setModel(SchedulingProblemUiModel model) {
        this.model = model;
        tblPriorities.setItems(model.getPriorities());
    }

    public SchedulingProblemUiModel getModel() {
        return this.model;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcolPriorityValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tcolColors.setCellFactory(new ColorsTableCellFactory());
        tcolTasks.setCellFactory(new TasksSubTableCellFactory());
        tcolSlotCount.setCellValueFactory(new PropertyValueFactory<>("slotCount"));
        btnDelete.disableProperty().bind(Bindings.isEmpty(tblPriorities.getSelectionModel().getSelectedItems()));
        btnEdit.disableProperty().bind(Bindings.isEmpty(tblPriorities.getSelectionModel().getSelectedItems()));
    }

    @FXML
    public void addPriority() {

    }

    @FXML
    public void editPriority() {

    }

    private PriorityUiModel determineSelected() {
        TableView.TableViewSelectionModel<PriorityUiModel> selectionModel = tblPriorities.getSelectionModel();
        PriorityUiModel result = selectionModel.getSelectedItem();
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
                int priority = 0;
                for (PriorityUiModel item : items) {
                    item.setValue(priority);
                    priority++;
                }
            }
        }
    }
}
