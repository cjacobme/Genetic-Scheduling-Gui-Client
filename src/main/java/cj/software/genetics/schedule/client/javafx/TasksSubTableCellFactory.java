package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TasksSubTableCellFactory implements Callback<
        TableColumn<PriorityUiModel, ObservableList<TasksUiModel>>,
        TableCell<PriorityUiModel, ObservableList<TasksUiModel>>> {
    @Override
    public TableCell<PriorityUiModel, ObservableList<TasksUiModel>> call(
            TableColumn<PriorityUiModel, ObservableList<TasksUiModel>> priorityUiModelObservableListTableColumn) {
        TableCell<PriorityUiModel, ObservableList<TasksUiModel>> result = new TasksSubTableCell();
        return result;
    }
}
