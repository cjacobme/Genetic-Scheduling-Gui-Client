package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolderTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConfigurationDumper.class)
@Import(ConfigurationHolderTestConfiguration.class)
class ConfigurationDumperTest {

    @Autowired
    private ConfigurationDumper configurationDumper;

    @Test
    void dumped() {
        assertThat(configurationDumper).isNotNull();    // just to have at least one assert check
    }
}
