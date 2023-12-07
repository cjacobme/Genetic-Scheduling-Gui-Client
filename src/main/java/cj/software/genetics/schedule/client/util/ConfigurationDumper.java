package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.FitnessProcedure;
import cj.software.genetics.schedule.client.Constants;
import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolder;
import cj.software.genetics.schedule.client.entity.configuration.FitnessProcedureMapping;
import cj.software.genetics.schedule.client.entity.configuration.Server;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            logger.info("");
            logger.info("+++++     Mapping of Fitness procedures                                  +++++");
            log(configurationHolder.getFitnessProcedureMapping());
            logger.info("##############################################################################");

        }
    }

    private void log(Server server) {
        logger.info(STRING_FORMAT, "URL", server.getUrl());
        logger.info(STRING_FORMAT, "create sub path", server.getCreateSubPath());
        logger.info(STRING_FORMAT, "breed sub path", server.getBreedSubPath());
    }

    private void log(FitnessProcedureMapping fitnessProcedureMapping) {
        logger.info("---   table column titles");
        log(fitnessProcedureMapping.getColumnTitles());
        logger.info("---   labels");
        log(fitnessProcedureMapping.getLabels());
    }

    private void log(Map<FitnessProcedure, String> map) {
        List<FitnessProcedure> keys = new ArrayList<>(map.keySet());
        keys.sort((o1, o2) -> {
            CompareToBuilder builder = new CompareToBuilder()
                    .append(o1.toString(), o2.toString());
            int result = builder.build();
            return result;
        });
        for (FitnessProcedure key : keys) {
            String value = map.get(key);
            logger.info(STRING_FORMAT, key, value);
        }
    }
}
