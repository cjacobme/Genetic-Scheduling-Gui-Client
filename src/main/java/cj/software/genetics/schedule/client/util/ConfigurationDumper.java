package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.client.Constants;
import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolder;
import cj.software.genetics.schedule.client.entity.configuration.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationDumper implements InitializingBean {

    private static final String STRING_FORMAT = "%50.50s = %s";

    private static final String INT_FORMAT = "%50.50s = %d";

    @SuppressWarnings("java:S3749") // for Loggers OK
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private ConfigurationHolder configurationHolder;

    @Override
    public void afterPropertiesSet() {
        try (MdcSetter mdcSetter = new MdcSetter(Constants.CORRELATION_ID_KEY, "config")) {
            logger.info("##############################################################################");
            logger.info("#####     List of all properties                                         #####");
            logger.info("");
            logger.info("+++++     Server                                                         +++++");
            log(configurationHolder.getServer());
            logger.info(INT_FORMAT, "The answer to everything is", 42);
            logger.info("##############################################################################");

        }
    }

    private void log(Server server) {
        logger.info(STRING_FORMAT, "URL", server.getUrl());
        logger.info(STRING_FORMAT, "create sub path", server.getCreateSubPath());
        logger.info(STRING_FORMAT, "breed sub path", server.getBreedSubPath());
    }
}
