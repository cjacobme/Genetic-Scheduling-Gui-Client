package cj.software.genetics.schedule.client.entity.configuration;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ConfigurationHolderBuilder extends ConfigurationHolder.Builder {
    public ConfigurationHolderBuilder() throws MalformedURLException, URISyntaxException {
        super.withServer(new ServerBuilder().build())
                .withFitnessProcedureMapping(new FitnessProcedureMappingBuilder().build());
    }
}
