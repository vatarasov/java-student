package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public interface RequestHandler {
    void handle(Request request, Response response) throws IOException;
}
