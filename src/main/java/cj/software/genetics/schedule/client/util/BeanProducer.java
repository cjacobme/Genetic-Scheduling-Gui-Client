package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolder;
import cj.software.genetics.schedule.client.entity.configuration.Server;
import javafx.util.converter.NumberStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;

@Component
public class BeanProducer {

    @Autowired
    private ConfigurationHolder configurationHolder;

    @Bean
    public NumberStringConverter numberStringConverter() {
        return new NumberStringConverter();
    }

    @Bean
    public WebClient webClient() {
        Server server = configurationHolder.getServer();
        URL url = server.getUrl();
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs
                        .defaultCodecs()
                        .maxInMemorySize(Integer.MAX_VALUE))
                .build();
        WebClient result = WebClient.builder()
                .baseUrl(url.toString())
                .exchangeStrategies(exchangeStrategies)
                .build();
        return result;
    }
}
