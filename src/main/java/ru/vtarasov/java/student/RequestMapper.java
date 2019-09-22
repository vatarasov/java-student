package ru.vtarasov.java.student;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class RequestMapper {
    private final Map<String, RequestHandler> handlers = new HashMap<>();

    public void registerHandler(String path, RequestHandler handler) {
        handlers.put(path, handler);
    }

    public RequestHandler getHandler(String path) {
        return handlers.get(path);
    }
}
