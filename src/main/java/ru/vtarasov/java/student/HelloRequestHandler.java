package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class HelloRequestHandler implements RequestHandler {
    @Override
    public void handle(Request request, Response response) throws IOException {
        response.setStatus("200");
        response.setDescription("Ok");
        response.writeResponse("Hello World!");
    }
}
