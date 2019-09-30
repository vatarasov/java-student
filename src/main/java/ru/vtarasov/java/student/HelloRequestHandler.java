package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class HelloRequestHandler extends RequestHandlerBase {
    @Override
    protected void handleGet(Request request, Response response) throws IOException {
        response.setStatus("200");
        response.setDescription("Ok");
        response.writeResponse("Hello World!");
    }
}
