package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingProblem;
import cj.software.genetics.schedule.api.entity.SolutionSetup;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Converter.class, SchedulingProblemService.class})
class ConverterTest {

    @Autowired
    private SchedulingProblemService schedulingProblemService;

    @Autowired
    private Converter converter;

    @Test
    void metadata() {
        Service service = Converter.class.getAnnotation(Service.class);
        assertThat(service).as("@Service").isNotNull();
    }

    @Test
    void convert() {
        SchedulingProblemUiModel uiModel = schedulingProblemService.createDefault();
        SchedulingCreatePostInput converted = converter.toSchedulingProblemPostInput(uiModel);
        assertThat(converted).as("converted").isNotNull();
        SchedulingProblem schedulingProblem = converted.getSchedulingProblem();
        SolutionSetup solutionSetup = converted.getSolutionSetup();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(schedulingProblem).as("scheduling problem").isNotNull();
        softy.assertThat(solutionSetup).as("solution setup").isNotNull();
        softy.assertAll();
    }
}
