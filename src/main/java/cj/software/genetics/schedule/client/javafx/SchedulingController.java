package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.api.entity.BreedPostInput;
import cj.software.genetics.schedule.api.entity.BreedPostOutput;
import cj.software.genetics.schedule.api.entity.Population;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostOutput;
import cj.software.genetics.schedule.api.entity.Solution;
import cj.software.genetics.schedule.client.Constants;
import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.javafx.control.SolutionPane;
import cj.software.genetics.schedule.client.util.Converter;
import cj.software.genetics.schedule.client.util.SchedulingProblemService;
import cj.software.genetics.schedule.client.util.ServerApi;
import cj.software.genetics.schedule.client.util.StringService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("Scheduling.fxml")
public class SchedulingController implements Initializable {
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

    @Autowired
    private ColorService colorService;

    private final ObjectProperty<Population> population = new SimpleObjectProperty<>();

    private SchedulingProblemUiModel schedulingProblemUiModel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Slider spScale;

    @FXML
    private Label lbScale;

    @FXML
    private TextField tfStatus;

    @FXML
    private Spinner<Integer> spNumCycles;

    @FXML
    private TextField tfCycleNo;

    @FXML
    private TableView<Solution> tblSolutions;

    @FXML
    private TableColumn<Solution, String> tcolCycle;

    @FXML
    private TableColumn<Solution, Long> tcolDuration;

    @FXML
    private Button btnSingleStep;

    @FXML
    private Button btnMultipleSteps;

    private Map<Integer, ColorPair> priorityColors;

    @FXML
    public void exit() {
        logger.info("exiting now...");
        Platform.exit();
    }

    @FXML
    public void newProblem() {
        try {
            String correlationId = stringService.createCorrelationId();
            MDC.put(Constants.CORRELATION_ID_KEY, correlationId);
            Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            SchedulingProblemUiModel model = schedulingProblemService.createDefault();
            EditSchedulingProblemDialog dialog = new EditSchedulingProblemDialog(applicationContext, owner, model, correlationId);
            Optional<SchedulingProblemUiModel> optionalModel = dialog.showAndWait();
            if (optionalModel.isPresent()) {
                this.schedulingProblemUiModel = optionalModel.get();
                this.priorityColors = converter.toPriorityColorPairMap(schedulingProblemUiModel);
                SchedulingCreatePostInput postInput = converter.toSchedulingProblemPostInput(schedulingProblemUiModel);
                SchedulingCreatePostOutput schedulingCreatePostOutput = serverApi.create(postInput, correlationId);
                Population returnedPopulation = schedulingCreatePostOutput.getPopulation();
                setPopulation(returnedPopulation);
                Stage stage = (Stage) btnMultipleSteps.getScene().getWindow();
                stage.setTitle(String.format("Scheduling Problem %s", correlationId));
                btnMultipleSteps.requestFocus();
            } else {
                logger.info("dialog was cancelled");
            }
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void singleStep() {
        try {
            BreedPostInput breedPostInput = converter.toBreedPostInput(this.schedulingProblemUiModel, 1, this.getPopulation());
            String correlationId = MDC.get(Constants.CORRELATION_ID_KEY);
            BreedPostOutput breedPostOutput = serverApi.breed(breedPostInput, correlationId);
            this.setPopulation(breedPostOutput.getPopulation());
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void multipleSteps() {
        try {
            int numCycles = spNumCycles.getValue();
            BreedPostInput breedPostInput = converter.toBreedPostInput(this.schedulingProblemUiModel, numCycles, this.getPopulation());
            String correlationId = MDC.get(Constants.CORRELATION_ID_KEY);
            BreedPostOutput breedPostOutput = serverApi.breed(breedPostInput, correlationId);
            this.setPopulation(breedPostOutput.getPopulation());
        } catch (RuntimeException exception) {
            logger.error(exception.getMessage(), exception);
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    public Population getPopulation() {
        return population.get();
    }

    public ObjectProperty<Population> populationProperty() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population.set(population);
        int generationStep = population.getGenerationStep();
        String formatted = String.format("%d", generationStep);
        tfCycleNo.setText(formatted);
        List<Solution> solutions = population.getSolutions();
        tblSolutions.getItems().setAll(solutions);
        if (!solutions.isEmpty()) {
            tblSolutions.getSelectionModel().select(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SolutionPane solutionPane = new SolutionPane(colorService);
        scrollPane.setContent(solutionPane);
        tfStatus.textProperty().bind(solutionPane.statusProperty());
        ObjectProperty<Population> property = populationProperty();
        spNumCycles.disableProperty().bind(Bindings.isNull(property));
        btnSingleStep.disableProperty().bind(Bindings.isNull(property));
        btnMultipleSteps.disableProperty().bind(Bindings.isNull(property));
        tcolCycle.setCellValueFactory(new PropertyValueFactory<>("generationStep"));
        String tableColStyle = "-fx-alignment: CENTER-RIGHT;-fx-font-family: Monospaced Regular;";
        tcolCycle.setStyle(tableColStyle);
        tcolDuration.setCellValueFactory(new PropertyValueFactory<>("durationInSeconds"));
        tcolDuration.setStyle(tableColStyle);
        spNumCycles.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 30));
        int scaleValue = spScale.valueProperty().intValue();
        lbScale.setText(String.format("%d", scaleValue));
        tblSolutions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                solutionPane.setSolution(newValue, priorityColors));
        spScale.valueProperty().addListener((observableValue, numbOldValue, numbNewValue) -> {
            if (numbNewValue != null) {
                int newValue = numbNewValue.intValue();
                lbScale.setText(String.format("%d", newValue));
                solutionPane.setScale(newValue);
                solutionPane.setSolution(null, priorityColors);
                Population local = getPopulation();
                if (local != null) {
                    int selectedIndex = tblSolutions.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        Solution solution = local.getSolutions().get(selectedIndex);
                        solutionPane.setSolution(solution, priorityColors);
                    }
                }
            }
        });
    }
}
