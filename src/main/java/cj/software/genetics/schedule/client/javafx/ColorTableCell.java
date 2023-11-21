package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ColorTableCell extends TableCell<PriorityUiModel, ColorPair> {
    private static final ColorService COLOR_SERVICE = new ColorService();

    @Override
    protected void updateItem(ColorPair colorPair, boolean empty) {
        super.updateItem(colorPair, empty);
        if (!empty) {
            Button button = new Button("Sample");
            ColorPair retrieved = getTableView().getItems().get(getIndex()).getColorPair();
            String style = COLOR_SERVICE.constructStyle(retrieved);
            button.setStyle(style);
            setGraphic(button);
        } else {
            setGraphic(null);
        }
        setText(null);
    }
}
