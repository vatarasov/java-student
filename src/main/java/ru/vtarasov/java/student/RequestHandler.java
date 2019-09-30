package ru.vtarasov.java.student;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public interface RequestHandler {
    void handle(Request request, Response response) throws Exception;

    default String extractIdFromPath(Request request, String path) {
        return request.getPath().substring(request.getPath().indexOf(path) + path.length() + 1);
    }
}
