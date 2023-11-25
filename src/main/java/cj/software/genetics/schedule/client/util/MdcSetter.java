package cj.software.genetics.schedule.client.util;

import org.slf4j.MDC;

public class MdcSetter implements AutoCloseable {

    private final String oldValue;

    private final String key;

    public MdcSetter(String key, String value) {
        this.key = key;
        this.oldValue = MDC.get(key);
        MDC.put(key, value);
    }

    @Override
    public void close() {
        MDC.put(key, oldValue);
    }
}
