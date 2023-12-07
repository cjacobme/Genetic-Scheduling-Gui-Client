package cj.software.genetics.schedule.client.entity.configuration;

import cj.software.genetics.schedule.api.entity.FitnessProcedure;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class FitnessProcedureMapping implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private final Map<FitnessProcedure, String> columnTitles = new EnumMap<>(FitnessProcedure.class);

    @NotEmpty
    private final Map<FitnessProcedure, String> labels = new EnumMap<>(FitnessProcedure.class);

    private FitnessProcedureMapping() {
    }

    public Map<FitnessProcedure, String> getColumnTitles() {
        return Collections.unmodifiableMap(columnTitles);
    }

    public void setColumnTitles(Map<FitnessProcedure, String> columnTitles) {
        this.columnTitles.clear();
        if (columnTitles != null) {
            this.columnTitles.putAll(columnTitles);
        }
    }

    public Map<FitnessProcedure, String> getLabels() {
        return Collections.unmodifiableMap(labels);
    }

    public void setLabels(Map<FitnessProcedure, String> labels) {
        this.labels.clear();
        if (labels != null) {
            this.labels.putAll(labels);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected FitnessProcedureMapping instance;

        protected Builder() {
            instance = new FitnessProcedureMapping();
        }

        public FitnessProcedureMapping build() {
            FitnessProcedureMapping result = instance;
            instance = null;
            return result;
        }

        public Builder withColumnTitles(Map<FitnessProcedure, String> columnTitles) {
            instance.setColumnTitles(columnTitles);
            return this;
        }

        public Builder withLabels(Map<FitnessProcedure, String> labels) {
            instance.setLabels(labels);
            return this;
        }
    }
}