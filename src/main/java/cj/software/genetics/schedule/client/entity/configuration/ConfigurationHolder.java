package cj.software.genetics.schedule.client.entity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cj.software.genetics.schedule.client")
@Validated
@EnableAspectJAutoProxy
public class ConfigurationHolder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    private Server server;

    @NotNull
    @Valid
    private FitnessProcedureMapping fitnessProcedureMapping;

    ConfigurationHolder() {
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public FitnessProcedureMapping getFitnessProcedureMapping() {
        return fitnessProcedureMapping;
    }

    public void setFitnessProcedureMapping(FitnessProcedureMapping fitnessProcedureMapping) {
        this.fitnessProcedureMapping = fitnessProcedureMapping;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected ConfigurationHolder instance;

        protected Builder() {
            instance = new ConfigurationHolder();
        }

        public ConfigurationHolder build() {
            ConfigurationHolder result = instance;
            instance = null;
            return result;
        }

        public Builder withServer(Server server) {
            instance.setServer(server);
            return this;
        }

        public Builder withFitnessProcedureMapping(FitnessProcedureMapping fitnessProcedureMapping) {
            instance.setFitnessProcedureMapping(fitnessProcedureMapping);
            return this;
        }
    }
}