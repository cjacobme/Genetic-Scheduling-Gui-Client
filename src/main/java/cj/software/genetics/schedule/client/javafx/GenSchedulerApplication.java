package cj.software.genetics.schedule.client.javafx;

import cj.software.genetics.schedule.client.GeneticScheduleSpringBootApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public class GenSchedulerApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(GeneticScheduleSpringBootApplication.class)
                .run(args);

    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent parent = Objects.requireNonNull(fxWeaver.loadView(SchedulingController.class));
        Scene scene = new Scene(parent);
        stage.setTitle("Scheduling with a genetic algorithm");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
