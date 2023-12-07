package cj.software.genetics.schedule.client.entity.configuration;

import cj.software.genetics.schedule.api.entity.FitnessProcedure;

import java.util.Map;

public class FitnessProcedureMappingBuilder extends FitnessProcedureMapping.Builder {
    public FitnessProcedureMappingBuilder() {
        super.withColumnTitles(Map.of(
                        FitnessProcedure.LATEST, "Duration / s",
                        FitnessProcedure.STD_DEVIATION, "Standard Deviation"))
                .withLabels(Map.of(
                        FitnessProcedure.LATEST, "Duration",
                        FitnessProcedure.STD_DEVIATION, "Standard deviation"));
    }
}
