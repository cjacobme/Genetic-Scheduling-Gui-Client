package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.BreedPostInput;
import cj.software.genetics.schedule.api.entity.FitnessProcedure;
import cj.software.genetics.schedule.api.entity.Population;
import cj.software.genetics.schedule.api.entity.ProblemPriority;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingProblem;
import cj.software.genetics.schedule.api.entity.SolutionSetup;
import cj.software.genetics.schedule.api.entity.Task;
import cj.software.genetics.schedule.api.entity.TimeWithUnit;
import cj.software.genetics.schedule.client.entity.ui.ColorPair;
import cj.software.genetics.schedule.client.entity.ui.PriorityUiModel;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import cj.software.genetics.schedule.client.entity.ui.TasksUiModel;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class Converter {

    public Map<Integer, ColorPair> toPriorityColorPairMap(SchedulingProblemUiModel schedulingProblemUiModel) {
        Map<Integer, ColorPair> result = new HashMap<>();
        ObservableList<PriorityUiModel> priorities = schedulingProblemUiModel.getPriorities();
        for (PriorityUiModel priority : priorities) {
            int value = priority.getValue();
            ColorPair colorPair = priority.getColorPair();
            result.put(value, colorPair);
        }
        return result;
    }

    public BreedPostInput toBreedPostInput(SchedulingProblemUiModel schedulingProblemUiModel, int numSteps, Population population) {
        int elitismCount = schedulingProblemUiModel.getElitismCount();
        int tournamentSize = schedulingProblemUiModel.getTournamentSize();
        double mutationRate = schedulingProblemUiModel.getMutationRate();
        BreedPostInput result = BreedPostInput.builder()
                .withNumSteps(numSteps)
                .withElitismCount(elitismCount)
                .withTournamentSize(tournamentSize)
                .withMutationRate(mutationRate)
                .withPopulation(population)
                .build();
        return result;
    }

    private static class Counter {

        private int startTaskIdentifier = 1;

        public int getStartTaskIdentifier() {
            return startTaskIdentifier;
        }

        public void setStartTaskIdentifier(int startTaskIdentifier) {
            this.startTaskIdentifier = startTaskIdentifier;
        }
    }

    public SchedulingCreatePostInput toSchedulingProblemPostInput(SchedulingProblemUiModel uiModel) {
        SchedulingProblem schedulingProblem = toSchedulingProblem(uiModel);
        SolutionSetup solutionSetup = toSolutionSetup(uiModel);
        SchedulingCreatePostInput result = SchedulingCreatePostInput.builder()
                .withSchedulingProblem(schedulingProblem)
                .withSolutionSetup(solutionSetup)
                .build();
        return result;
    }

    private SchedulingProblem toSchedulingProblem(SchedulingProblemUiModel uiModel) {
        Collection<ProblemPriority> problemPriorities = toProblemPriorities(uiModel.getPriorities());
        SchedulingProblem result = SchedulingProblem.builder()
                .withPriorities(problemPriorities)
                .build();
        return result;
    }

    private Collection<ProblemPriority> toProblemPriorities(ObservableList<PriorityUiModel> priorityUiModels) {
        int size = priorityUiModels.size();
        Collection<ProblemPriority> result = new ArrayList<>(size);
        Counter counter = new Counter();
        for (PriorityUiModel priorityUiModel : priorityUiModels) {
            ProblemPriority problemPriority = toProblemPriority(priorityUiModel, counter);
            result.add(problemPriority);
        }
        return result;
    }

    private ProblemPriority toProblemPriority(PriorityUiModel priorityUiModel, Counter counter) {
        int startTaskIdentifier = counter.getStartTaskIdentifier();
        int prioVal = priorityUiModel.getValue();
        Collection<Task> tasks = toTasks(priorityUiModel, startTaskIdentifier);
        counter.setStartTaskIdentifier(startTaskIdentifier + tasks.size());
        ProblemPriority result = ProblemPriority.builder()
                .withValue(prioVal)
                .withSlotCount(priorityUiModel.getSlotCount())
                .withTasks(tasks)
                .build();
        return result;
    }

    private Collection<Task> toTasks(PriorityUiModel priorityUiModel, int startTaskIdentifier) {
        int taskIdentifier = startTaskIdentifier;
        Collection<Task> result = new ArrayList<>();
        ObservableList<TasksUiModel> tasksUiModels = priorityUiModel.getTasks();
        for (TasksUiModel tasksUiModel : tasksUiModels) {
            int numTasks = tasksUiModel.getCount();
            TimeWithUnit duration = tasksUiModel.getDuration();
            for (int i = 0; i < numTasks; i++) {
                Task task = Task.builder().withDuration(duration).withIdentifier(taskIdentifier).build();
                result.add(task);
                taskIdentifier++;
            }
        }
        return result;
    }

    private SolutionSetup toSolutionSetup(SchedulingProblemUiModel uiModel) {
        int solutionCount = uiModel.getSolutionCount();
        int workerCount = uiModel.getWorkerCount();
        FitnessProcedure fitnessProcedure = uiModel.getFitnessProcedure();
        SolutionSetup result = SolutionSetup.builder()
                .withSolutionCount(solutionCount)
                .withWorkersPerSolutionCount(workerCount)
                .withFitnessProcedure(fitnessProcedure)
                .build();
        return result;
    }
}
