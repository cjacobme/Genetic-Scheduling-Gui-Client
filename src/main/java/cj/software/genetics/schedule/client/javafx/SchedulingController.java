package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.util.SchedulingProblemService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("Scheduling.fxml")
public class SchedulingController implements Initializable {

    @Autowired
    private SchedulingProblemService schedulingProblemService;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void exit() {
        logger.info("exiting now...");
        Platform.exit();
    }

    @FXML
    public void newProblem() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        SchedulingProblemUiModel model = schedulingProblemService.createDefault();
        EditSchedulingProblemDialog dialog = new EditSchedulingProblemDialog(applicationContext, owner, model);
        Optional<SchedulingProblemUiModel> optionalModel = dialog.showAndWait();
        if (optionalModel.isPresent()) {
            logger.warn("not yet implemented: show edited model");
        } else {
            logger.info("dialog was cancelled");
        }
    }
}
