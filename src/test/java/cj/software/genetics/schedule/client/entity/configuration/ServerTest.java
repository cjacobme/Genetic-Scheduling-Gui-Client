package cj.software.genetics.schedule.client.entity.configuration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = Server.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Server.Builder builder = Server.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                Server.class);

        Server instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getUrl()).as("URL").isNull();
        softy.assertThat(instance.getCreateSubPath()).as("create sub path").isNull();
        softy.assertThat(instance.getBreedSubPath()).as("breed sub path").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() throws URISyntaxException, MalformedURLException {
        URL url = new URI("http://www.gippetnicht.de").toURL();
        String createSubPath = "_createSubPath";
        String breedSubPath = "_breedSubPath";
        Server instance = Server.builder()
                .withUrl(url)
                .withCreateSubPath(createSubPath)
                .withBreedSubPath(breedSubPath)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getUrl()).as("URL").isEqualTo(url);
        softy.assertThat(instance.getCreateSubPath()).as("create sub path").isEqualTo(createSubPath);
        softy.assertThat(instance.getBreedSubPath()).as("breed sub path").isEqualTo(breedSubPath);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() throws MalformedURLException, URISyntaxException {
        Server instance = new ServerBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Server>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}