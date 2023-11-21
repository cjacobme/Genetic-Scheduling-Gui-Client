package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("EditSchedulingProblem.fxml")
public class EditSchedulingProblemController {

    private SchedulingProblemUiModel model;

    public void setModel(SchedulingProblemUiModel model) {
        this.model = model;
    }

    public SchedulingProblemUiModel getModel() {
        return this.model;
    }
}
