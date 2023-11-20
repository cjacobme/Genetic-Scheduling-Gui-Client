package cj.software.genetics.schedule.client.entity.configuration;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerBuilder extends Server.Builder {
    public ServerBuilder() throws URISyntaxException, MalformedURLException {
        super.withUrl(new URI("https://www.nasa.gov").toURL())
                .withCreateSubPath("/schedule/create")
                .withBreedSubPath("/schedule/breed");
    }
}
