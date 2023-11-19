package cj.software.genetics.schedule.client;

import cj.software.genetics.schedule.client.javafx.GenSchedulerApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneticScheduleSpringBootApplication {
    public static void main(String[] args) {
        Application.launch(GenSchedulerApplication.class, args);
    }
}
