package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

public class EditPriorityDetailsDialog extends Dialog<PriorityUiModel> {

    public EditPriorityDetailsDialog(
            ConfigurableApplicationContext context,
            Window owner,
            PriorityUiModel model) {
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        FxControllerAndView<EditPriorityDetailsController, DialogPane> controllerAndView =
                fxWeaver.load(EditPriorityDetailsController.class);
        Optional<DialogPane> optional = controllerAndView.getView();
        if (optional.isPresent()) {
            final EditPriorityDetailsController controller = controllerAndView.getController();
            PriorityUiModel edit = new PriorityUiModel(model);
            controller.setData(edit);
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            DialogPane dialogPane = optional.get();
            setDialogPane(dialogPane);
            setResultConverter(new MyResultConverter(controller));
        }
    }

    private static class MyResultConverter implements Callback<ButtonType, PriorityUiModel> {
        private final EditPriorityDetailsController controller;

        MyResultConverter(EditPriorityDetailsController controller) {
            this.controller = controller;
        }

        @Override
        public PriorityUiModel call(ButtonType buttonType) {
            PriorityUiModel result;
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData.isDefaultButton()) {
                result = controller.getPriorityUiModel();
            } else {
                result = null;
            }
            return result;
        }
    }
}
