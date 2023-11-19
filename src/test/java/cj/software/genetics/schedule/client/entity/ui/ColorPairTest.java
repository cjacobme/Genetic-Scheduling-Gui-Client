package cj.software.genetics.schedule.client.entity.ui;

import javafx.scene.paint.Color;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ColorPairTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = ColorPair.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        ColorPair.Builder builder = ColorPair.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                ColorPair.class);

        ColorPair instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getForeground()).as("foreground").isNull();
        softy.assertThat(instance.getBackground()).as("background").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        Color foreground = Color.GREEN;
        Color background = Color.BLACK;
        ColorPair instance = ColorPair.builder()
                .withForeground(foreground)
                .withBackground(background)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getForeground()).as("foreground").isEqualTo(foreground);
        softy.assertThat(instance.getBackground()).as("background").isEqualTo(background);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        ColorPair instance = new ColorPairBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ColorPair>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}