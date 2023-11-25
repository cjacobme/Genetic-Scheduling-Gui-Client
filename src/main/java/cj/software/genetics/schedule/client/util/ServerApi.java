package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostOutput;
import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolder;
import cj.software.genetics.schedule.client.entity.configuration.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServerApi {

    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private WebClient webClient;

    @Autowired
    private ConfigurationHolder configurationHolder;

    public SchedulingCreatePostOutput create(SchedulingCreatePostInput input, String correlationId) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        Server server = configurationHolder.getServer();
        String subPath = server.getCreateSubPath();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(subPath);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec
                .header("correlation-id", correlationId)
                .bodyValue(input);
        WebClient.ResponseSpec responseSpec = headersSpec.retrieve();
        Mono<ResponseEntity<SchedulingCreatePostOutput>> returned = responseSpec.toEntity(SchedulingCreatePostOutput.class);
        ResponseEntity<SchedulingCreatePostOutput> responseEntity = returned.block();
        if (responseEntity == null) {
            throw new NullPointerException("no response entity returned");
        }
        SchedulingCreatePostOutput result = responseEntity.getBody();
        logger.info("problem object returned from server");
        return result;
    }
}
