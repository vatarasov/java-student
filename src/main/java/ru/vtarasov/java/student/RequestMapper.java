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

    public void unregisterHandler(String path) {
        handlers.remove(path);
    }

    public RequestHandler getHandler(String path) {
        path = trimEndSlash(path);
        path = trimQuery(path);
        return handler(path);
    }

    private String trimEndSlash(String path) {
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    private String trimQuery(String path) {
        int queryIndex = path.indexOf("?");
        return queryIndex != -1 ? path.substring(0, queryIndex) : path;
    }

    private RequestHandler handler(String path) {
        RequestHandler handler = handlers.get(path);
        if (handler != null) {
            return handler;
        }
        int lastSlashIndex = path.lastIndexOf("/");
        if (lastSlashIndex == -1 || lastSlashIndex == 0) {
            return null;
        }
        path = path.substring(0, lastSlashIndex);
        return handlers.get(path);
    }
}
