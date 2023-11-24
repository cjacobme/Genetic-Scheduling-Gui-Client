package cj.software.genetics.schedule.client.util;

import cj.software.genetics.schedule.api.entity.SchedulingCreatePostInput;
import cj.software.genetics.schedule.api.entity.SchedulingCreatePostOutput;
import cj.software.genetics.schedule.client.entity.configuration.ConfigurationHolder;
import cj.software.genetics.schedule.client.entity.configuration.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServerApi {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ConfigurationHolder configurationHolder;

    public SchedulingCreatePostOutput create(SchedulingCreatePostInput input) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.post();
        Server server = configurationHolder.getServer();
        String subPath = server.getCreateSubPath();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(subPath);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(input);
        WebClient.ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
        Mono<SchedulingCreatePostOutput> returned = responseSpec.bodyToMono(SchedulingCreatePostOutput.class);
        SchedulingCreatePostOutput result = returned.block();
        return result;
    }
}
