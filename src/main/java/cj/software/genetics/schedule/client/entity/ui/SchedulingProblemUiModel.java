package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.IntegerProperty;
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
