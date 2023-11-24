package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.client.entity.ui.SchedulingProblemUiModel;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public SchedulingCreatePostInput toSchedulingProblemPostInput(SchedulingProblemUiModel uiModel) {
        SchedulingCreatePostInput result = SchedulingCreatePostInput.builder().build();
        return result;
    }
}
