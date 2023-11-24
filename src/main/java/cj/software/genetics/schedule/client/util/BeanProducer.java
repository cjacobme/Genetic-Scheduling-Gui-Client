package cj.software.genetics.schedule.client.util;

import javafx.util.converter.NumberStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanProducer {

    @Bean
    public NumberStringConverter numberStringConverter() {
        return new NumberStringConverter();
    }
}
