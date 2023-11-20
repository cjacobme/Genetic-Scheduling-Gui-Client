package cj.software.genetics.schedule.client.entity.configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

public class Server implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private URL url;

    @NotBlank
    private String createSubPath;

    @NotBlank
    private String breedSubPath;

    private Server() {
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getCreateSubPath() {
        return createSubPath;
    }

    public void setCreateSubPath(String createSubPath) {
        this.createSubPath = createSubPath;
    }

    public String getBreedSubPath() {
        return breedSubPath;
    }

    public void setBreedSubPath(String breedSubPath) {
        this.breedSubPath = breedSubPath;
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

        public Builder withCreateSubPath(String createSubPath) {
            instance.setCreateSubPath(createSubPath);
            return this;
        }

        public Builder withBreedSubPath(String breedSubPath) {
            instance.setBreedSubPath(breedSubPath);
            return this;
        }
    }
}