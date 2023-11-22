package cj.software.genetics.schedule.client.entity;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TimeWithUnitTest {

    @Test
    void create123Seconds() {
        TimeWithUnit instance = new TimeWithUnit(Duration.ofSeconds(123));
        assertEquals(instance, 123, ChronoUnit.SECONDS);
    }

    @Test
    void create2Minutes() {
        TimeWithUnit instance = new TimeWithUnit(Duration.ofMinutes(2));
        assertEquals(instance, 2, ChronoUnit.MINUTES);
    }

    @Test
    void create4Hours() {
        TimeWithUnit instance = new TimeWithUnit(Duration.ofHours(4));
        assertEquals(instance, 4, ChronoUnit.HOURS);
    }

    private void assertEquals(TimeWithUnit instance, int expValue, ChronoUnit expUnit) {
        assertThat(instance).isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getTime()).as("time").isEqualTo(expValue);
        softy.assertThat(instance.getUnit()).as("unit").isEqualTo(expUnit);
        softy.assertAll();
    }
}
