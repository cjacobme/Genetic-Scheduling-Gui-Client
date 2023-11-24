package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
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

public class EditSchedulingProblemDialog extends Dialog<SchedulingProblemUiModel> {

    public EditSchedulingProblemDialog(
            ConfigurableApplicationContext context,
            Window owner,
            SchedulingProblemUiModel model) {
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        FxControllerAndView<EditSchedulingProblemController, DialogPane> controllerAndView =
                fxWeaver.load(EditSchedulingProblemController.class);
        Optional<DialogPane> optionalDialogPane = controllerAndView.getView();
        if (optionalDialogPane.isPresent()) {
            final EditSchedulingProblemController controller = controllerAndView.getController();
            controller.setModel(model);
            super.initOwner(owner);
            super.initModality(Modality.APPLICATION_MODAL);
            DialogPane dialogPane = optionalDialogPane.get();
            super.setDialogPane(dialogPane);
            super.setResultConverter(new MyResultConverter(controller));
        }
    }

    private static class MyResultConverter implements Callback<ButtonType, SchedulingProblemUiModel> {

        private final EditSchedulingProblemController controller;

        MyResultConverter(EditSchedulingProblemController controller) {
            this.controller = controller;
        }

        @Override
        public SchedulingProblemUiModel call(ButtonType buttonType) {
            SchedulingProblemUiModel result;
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData.isDefaultButton()) {
                result = controller.getModel();
            } else {
                result = null;
            }
            return result;
        }
    }
}
