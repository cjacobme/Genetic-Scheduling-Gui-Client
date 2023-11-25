package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.util.Converter;
import cj.software.genetics.schedule.client.util.SchedulingProblemService;
import cj.software.genetics.schedule.client.util.ServerApi;
import cj.software.genetics.schedule.client.util.StringService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FxmlView("Scheduling.fxml")
public class SchedulingController {
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private StringService stringService;

    @Autowired
    private SchedulingProblemService schedulingProblemService;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private Converter converter;

    @Autowired
    private ServerApi serverApi;

    @FXML
    public void exit() {
        logger.info("exiting now...");
        Platform.exit();
    }

    @FXML
    public void newProblem() {
        try {
            String correlationId = stringService.createCorrelationId();
            MDC.put("correlation-id", correlationId);
            Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            SchedulingProblemUiModel model = schedulingProblemService.createDefault();
            EditSchedulingProblemDialog dialog = new EditSchedulingProblemDialog(applicationContext, owner, model, correlationId);
            Optional<SchedulingProblemUiModel> optionalModel = dialog.showAndWait();
            if (optionalModel.isPresent()) {
                SchedulingProblemUiModel edited = optionalModel.get();
                SchedulingCreatePostInput postInput = converter.toSchedulingProblemPostInput(edited);
                serverApi.create(postInput, correlationId);
            } else {
                logger.info("dialog was cancelled");
            }
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
