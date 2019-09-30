package ru.vtarasov.java.student;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vtarasov
 * @since 30.09.2019
 */
public class ServiceContainer {
    private final Map<Class, Object> services = new ConcurrentHashMap<>();

    public <T> void register(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }

    public <T> void unregister(Class<T> serviceClass) {
        services.remove(serviceClass);
    }

    public <T> T get(Class<T> serviceClass) {
        return (T)services.get(serviceClass);
    }
}
