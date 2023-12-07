package cj.software.genetics.schedule.client.entity.configuration;

import cj.software.genetics.schedule.api.entity.FitnessProcedure;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FitnessProcedureMappingTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = FitnessProcedureMapping.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        FitnessProcedureMapping.Builder builder = FitnessProcedureMapping.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                FitnessProcedureMapping.class);

        FitnessProcedureMapping instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getColumnTitles()).as("table column titles").isEmpty();
        softy.assertThat(instance.getLabels()).as("labels").isEmpty();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        Map<FitnessProcedure, String> columnTitles = Map.of(FitnessProcedure.LATEST, "_lastest");
        Map<FitnessProcedure, String> labels = Map.of(FitnessProcedure.STD_DEVIATION, "_std");
        FitnessProcedureMapping instance = FitnessProcedureMapping.builder()
                .withColumnTitles(columnTitles)
                .withLabels(labels)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getColumnTitles()).as("column titles")
                .containsExactlyInAnyOrderEntriesOf(columnTitles);
        softy.assertThat(instance.getLabels()).as("labels")
                .containsExactlyInAnyOrderEntriesOf(labels);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        FitnessProcedureMapping instance = new FitnessProcedureMappingBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<FitnessProcedureMapping>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}