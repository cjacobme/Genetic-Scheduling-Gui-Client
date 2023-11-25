package cj.software.genetics.schedule.client.entity.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchedulingProblemUiModel {

    private ObservableList<PriorityUiModel> priorities;

    private final IntegerProperty solutionCount;

    private final IntegerProperty workerCount;

    private final IntegerProperty elitismCount;

    private final IntegerProperty tournamentSize;

    private final DoubleProperty mutationRate;

    public SchedulingProblemUiModel(
            ObservableList<PriorityUiModel> priorities,
            IntegerProperty solutionCount,
            IntegerProperty workerCount,
            IntegerProperty elitismCount,
            IntegerProperty tournameSize,
            DoubleProperty mutationRate) {
        this.priorities = priorities;
        this.solutionCount = solutionCount;
        this.workerCount = workerCount;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournameSize;
        this.mutationRate = mutationRate;
    }

    /**
     * copy constructor
     */
    public SchedulingProblemUiModel(SchedulingProblemUiModel source) {
        this.solutionCount = new SimpleIntegerProperty(source.getSolutionCount());
        this.workerCount = new SimpleIntegerProperty(source.getWorkerCount());
        this.priorities = copyPriorities(source.getPriorities());
        this.elitismCount = new SimpleIntegerProperty(source.getElitismCount());
        this.tournamentSize = new SimpleIntegerProperty(source.getTournamentSize());
        this.mutationRate = new SimpleDoubleProperty(source.getMutationRate());
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

    public int getElitismCount() {
        return elitismCount.get();
    }

    public IntegerProperty elitismCountProperty() {
        return elitismCount;
    }

    public void setElitismCount(int elitismCount) {
        this.elitismCount.set(elitismCount);
    }

    public int getTournamentSize() {
        return tournamentSize.get();
    }

    public IntegerProperty tournamentSizeProperty() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize.set(tournamentSize);
    }

    public double getMutationRate() {
        return mutationRate.get();
    }

    public DoubleProperty mutationRateProperty() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate.set(mutationRate);
    }
}
