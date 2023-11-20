package cj.software.genetics.schedule.client.entity.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@TestConfiguration
public class ConfigurationHolderTestConfiguration {
    @Bean(name = "configurationHolder")
    public ConfigurationHolder configurationHolder() throws MalformedURLException, URISyntaxException {
        ConfigurationHolder result = new ConfigurationHolderBuilder().build();
        return result;
    }
}
