package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class HelloRequestHandler implements RequestHandler {
    @Override
    public void handle(Request request, Response response) throws IOException {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Method not allowed");
            response.setStatus("405");
            response.setDescription("Method Not Allowed");
            response.writeResponse("");
            return;
        }
        response.setStatus("200");
        response.setDescription("Ok");
        response.writeResponse("Hello World!");
    }
}
