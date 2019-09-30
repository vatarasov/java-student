package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 30.09.2019
 */
public class RequestHandlerBase implements RequestHandler {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    @Override
    public void handle(Request request, Response response) throws IOException {
        final String method = request.getMethod();
        if (GET.equalsIgnoreCase(method)) {
            handleGet(request, response);
        } else if (POST.equalsIgnoreCase(method)) {
            handlePost(request, response);
        } else if (PUT.equalsIgnoreCase(method)) {
            handlePut(request, response);
        } else if (DELETE.equalsIgnoreCase(method)) {
            handleDelete(request, response);
        } else {
            handleUnknown(response);
        }
    }

    protected void handleGet(Request request, Response response) throws IOException {
        handleUnknown(response);
    }

    protected void handlePost(Request request, Response response) throws IOException {
        handleUnknown(response);
    }

    protected void handlePut(Request request, Response response) throws IOException {
        handleUnknown(response);
    }

    protected void handleDelete(Request request, Response response) throws IOException {
        handleUnknown(response);
    }

    private void handleUnknown(Response response) throws IOException {
        System.out.println("Method not allowed");
        response.setStatus("405");
        response.setDescription("Method Not Allowed");
        response.writeResponse("");
    }
}
