package cj.software.genetics.schedule.client.entity.configuration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationHolderTest {

    @Test
    void metadata() {
        Class<?>[] interfaces = ConfigurationHolder.class.getInterfaces();
        Configuration configuration = ConfigurationHolder.class.getAnnotation(Configuration.class);
        EnableConfigurationProperties enableConfigurationProperties = ConfigurationHolder.class.getAnnotation(EnableConfigurationProperties.class);
        ConfigurationProperties configurationProperties = ConfigurationHolder.class.getAnnotation(ConfigurationProperties.class);
        Validated validated = ConfigurationHolder.class.getAnnotation(Validated.class);
        EnableAspectJAutoProxy enableAspectJAutoProxy = ConfigurationHolder.class.getAnnotation(EnableAspectJAutoProxy.class);
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(interfaces).as("interfaces").contains(Serializable.class);
        softy.assertThat(configuration).as("@Configuration").isNotNull();
        softy.assertThat(enableConfigurationProperties).as("@EnableConfigurationProperties").isNotNull();
        softy.assertThat(configurationProperties).as("@ConfigurationProperties").isNotNull();
        softy.assertThat(validated).as("@Validated").isNotNull();
        softy.assertThat(enableAspectJAutoProxy).as("@EnableAspectJAutoProxy").isNotNull();
        softy.assertAll();
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        ConfigurationHolder.Builder builder = ConfigurationHolder.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                ConfigurationHolder.class);

        ConfigurationHolder instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getServer()).as("server").isNull();
        softy.assertThat(instance.getFitnessProcedureMapping()).as("fitness procedure mapping").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        Server server = Server.builder().build();
        FitnessProcedureMapping fitnessProcedureMapping = FitnessProcedureMapping.builder().build();
        ConfigurationHolder instance = ConfigurationHolder.builder()
                .withServer(server)
                .withFitnessProcedureMapping(fitnessProcedureMapping)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getServer()).as("server").isSameAs(server);
        softy.assertThat(instance.getFitnessProcedureMapping()).isSameAs(fitnessProcedureMapping);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() throws MalformedURLException, URISyntaxException {
        ConfigurationHolder instance = new ConfigurationHolderBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ConfigurationHolder>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}