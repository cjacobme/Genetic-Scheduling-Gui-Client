package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ColorsTableCellFactory implements Callback<TableColumn<PriorityUiModel, ColorPair>, TableCell<PriorityUiModel, ColorPair>> {

    @Override
    public TableCell<PriorityUiModel, ColorPair> call(TableColumn<PriorityUiModel, ColorPair> priorityUiModelColorPairTableColumn) {
        TableCell<PriorityUiModel, ColorPair> result = new ColorTableCell();
        return result;
    }
}
