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
    public void handle(Request request, Response response) throws Exception {
        final String method = request.getMethod();
        if (GET.equalsIgnoreCase(method)) {
            withExceptionHandling(request, response, (request1, response1) -> handleGet(request1, response1));
        } else if (POST.equalsIgnoreCase(method)) {
            withExceptionHandling(request, response, (request1, response1) -> handlePost(request1, response1));
        } else if (PUT.equalsIgnoreCase(method)) {
            withExceptionHandling(request, response, (request1, response1) -> handlePut(request1, response1));
        } else if (DELETE.equalsIgnoreCase(method)) {
            withExceptionHandling(request, response, (request1, response1) -> handleDelete(request1, response1));
        } else {
            handleUnknown(response);
        }
    }

    protected void handleGet(Request request, Response response) throws Exception {
        handleUnknown(response);
    }

    protected void handlePost(Request request, Response response) throws Exception {
        handleUnknown(response);
    }

    protected void handlePut(Request request, Response response) throws Exception {
        handleUnknown(response);
    }

    protected void handleDelete(Request request, Response response) throws Exception {
        handleUnknown(response);
    }

    private void handleUnknown(Response response) throws IOException {
        System.out.println("Method not allowed");

        response.setStatus(Response.METHOD_NOT_ALLOWED);
        response.setDescription(Response.METHOD_NOT_ALLOWED_DESCRIPTION);
        response.writeResponse();
    }

    private void withExceptionHandling(Request request, Response response, RequestHandler handler) throws IOException {
        try {
            handler.handle(request, response);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            handleException(request, response, e);
        }
    }

    protected void handleException(Request request, Response response, Exception e) throws IOException {
        e.printStackTrace();

        response.setStatus(Response.INTERNAL_SERVER_ERROR);
        response.setDescription(Response.INTERNAL_SERVER_ERROR_DESCRIPTION);
        response.writeResponse();
    }
}
