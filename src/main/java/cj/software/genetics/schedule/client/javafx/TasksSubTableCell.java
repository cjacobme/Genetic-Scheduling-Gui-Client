package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TasksSubTableCell extends TableCell<PriorityUiModel, ObservableList<TasksUiModel>> {
    @Override
    protected void updateItem(ObservableList<TasksUiModel> tasksUiModels, boolean empty) {
        super.updateItem(tasksUiModels, empty);
        if (!empty) {
            TableView<TasksUiModel> tableView = new TableView<>();
            tableView.setMaxHeight(150.0);
            tableView.setPrefHeight(150.0);
            ObservableList<TasksUiModel> entries = getTableView().getItems().get(getIndex()).getTasks();
            ObservableList<TableColumn<TasksUiModel, ?>> columns = tableView.getColumns();

            TableColumn<TasksUiModel, TimeWithUnit> tcolDuration = new TableColumn<>("Duration");
            tcolDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            columns.add(tcolDuration);

            TableColumn<TasksUiModel, Integer> tcolCount = new TableColumn<>("count");
            tcolCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            columns.add(tcolCount);

            tableView.setItems(entries);
            setGraphic(tableView);
        } else {
            setGraphic(null);
        }
        setText(null);
    }
}
