package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchedulingProblemUiModel {

    private ObservableList<PriorityUiModel> priorities;

    private final IntegerProperty solutionCount;

    private final IntegerProperty workerCount;

    public SchedulingProblemUiModel(ObservableList<PriorityUiModel> priorities, IntegerProperty solutionCount, IntegerProperty workerCount) {
        this.priorities = priorities;
        this.solutionCount = solutionCount;
        this.workerCount = workerCount;
    }

    /**
     * copy constructor
     */
    public SchedulingProblemUiModel(SchedulingProblemUiModel source) {
        this.solutionCount = new SimpleIntegerProperty(source.getSolutionCount());
        this.workerCount = new SimpleIntegerProperty(source.getWorkerCount());
        this.priorities = copyPriorities(source.getPriorities());
    }

    private ObservableList<PriorityUiModel> copyPriorities(ObservableList<PriorityUiModel> source) {
        ObservableList<PriorityUiModel> result = FXCollections.observableArrayList();
        for (PriorityUiModel priorityUiModel : source) {
            PriorityUiModel copy = new PriorityUiModel(priorityUiModel);
            result.add(copy);
        }
        return result;
    }

    public ObservableList<PriorityUiModel> getPriorities() {
        return priorities;
    }

    public void setPriorities(ObservableList<PriorityUiModel> priorities) {
        this.priorities = priorities;
    }

    public int getSolutionCount() {
        return solutionCount.get();
    }

    public IntegerProperty solutionCountProperty() {
        return solutionCount;
    }

    public void setSolutionCount(int solutionCount) {
        this.solutionCount.set(solutionCount);
    }

    public int getWorkerCount() {
        return workerCount.get();
    }

    public IntegerProperty workerCountProperty() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount.set(workerCount);
    }
}
