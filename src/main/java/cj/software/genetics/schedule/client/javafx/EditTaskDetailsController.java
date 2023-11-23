package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.api.entity.TimeUnit;
import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("EditTaskDetails.fxml")
public class EditTaskDetailsController implements Initializable {

    @Autowired
    private NumberStringConverter numberStringConverter;

    @FXML
    private TextField tfCount;

    @FXML
    private TextField tfDurationValue;

    @FXML
    private ComboBox<TimeUnit> cbDurationUnit;

    private IntegerProperty durationValue;

    private SimpleObjectProperty<TimeUnit> durationUnit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbDurationUnit.getItems().setAll(TimeUnit.values());
    }

    public void setData(TasksUiModel model) {
        Bindings.bindBidirectional(tfCount.textProperty(), model.countProperty(), numberStringConverter);
        TimeWithUnit duration = model.getDuration();
        this.durationValue = new SimpleIntegerProperty(duration.getTime());
        this.durationUnit = new SimpleObjectProperty<>(duration.getUnit());
        Bindings.bindBidirectional(tfDurationValue.textProperty(), this.durationValue, numberStringConverter);
        Bindings.bindBidirectional(cbDurationUnit.valueProperty(), durationUnit);
    }

    public TasksUiModel getData() {
        int count = Integer.parseInt(tfCount.getText());
        int dv = durationValue.get();
        TimeUnit du = durationUnit.get();
        TimeWithUnit duration = TimeWithUnit.builder().withTime(dv).withUnit(du).build();
        TasksUiModel result = new TasksUiModel(new SimpleObjectProperty<>(duration), new SimpleIntegerProperty(count));
        return result;
    }
}
