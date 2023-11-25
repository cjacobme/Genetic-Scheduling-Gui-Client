package cj.software.genetics.schedule.client.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StringService {

    public String createCorrelationId() {
        String result = UUID.randomUUID().toString();
        return result;
    }
}
