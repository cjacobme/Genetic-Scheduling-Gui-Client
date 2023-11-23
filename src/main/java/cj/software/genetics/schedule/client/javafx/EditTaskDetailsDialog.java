package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
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

public class EditTaskDetailsDialog extends Dialog<TasksUiModel> {
    public EditTaskDetailsDialog(
            ConfigurableApplicationContext context,
            Window owner,
            TasksUiModel model) {
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        FxControllerAndView<EditTaskDetailsController, DialogPane> controllerAndView =
                fxWeaver.load(EditTaskDetailsController.class);
        Optional<DialogPane> optional = controllerAndView.getView();
        if (optional.isPresent()) {
            final EditTaskDetailsController controller = controllerAndView.getController();
            controller.setData(model);
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            DialogPane dialogPane = optional.get();
            setDialogPane(dialogPane);
            setResultConverter(new MyResultConverter(controller));
        }
    }

    private static class MyResultConverter implements Callback<ButtonType, TasksUiModel> {
        private final EditTaskDetailsController controller;

        private MyResultConverter(EditTaskDetailsController controller) {
            this.controller = controller;
        }

        @Override
        public TasksUiModel call(ButtonType buttonType) {
            TasksUiModel result;
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData.isDefaultButton()) {
                result = controller.getData();
            } else {
                result = null;
            }
            return result;
        }
    }
}
