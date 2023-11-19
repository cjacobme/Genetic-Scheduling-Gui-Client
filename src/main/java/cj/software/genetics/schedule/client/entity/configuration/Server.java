package cj.software.genetics.schedule.client.entity.configuration;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

public class Server implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private URL url;

    private Server() {
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Server instance;

        protected Builder() {
            instance = new Server();
        }

        public Server build() {
            Server result = instance;
            instance = null;
            return result;
        }

        public Builder withUrl(URL url) {
            instance.setUrl(url);
            return this;
        }
    }
}