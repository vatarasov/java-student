package ru.vtarasov.java.student;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class Response {
    public static final int OK = 200;
    public static final String OK_DESCRIPTION = "Ok";

    public static final int CREATED = 201;
    public static final String CREATED_DESCRIPTION = "Created";

    public static final int NOT_FOUND = 404;
    public static final String NOT_FOUND_DESCRIPTION = "Not Found";

    public static final int METHOD_NOT_ALLOWED = 405;
    public static final String METHOD_NOT_ALLOWED_DESCRIPTION = "Method Not Allowed";

    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Internal Server Error";

    private final ResponseWriter writer;

    private int status;
    private String description;
    private Map<String, String> headers = new HashMap<>();

    public Response(ResponseWriter writer) {
        this.writer = writer;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void removeHeader(String name) {
        headers.remove(name);
    }

    public void clearHeaders() {
        headers.clear();
    }

    public void writeStartLineAndHeaders() throws IOException {
        writer.writeStartLine(status, description);
        writer.writeHeaders(headers);
        writer.flush();
    }

    public void writeBody(String body) throws IOException {
        writer.writeBody(body);
        writer.flush();
    }

    public void writeResponse(String body) throws IOException {
        writeStartLineAndHeaders();
        writeBody(body);
    }

    public void writeResponse() throws IOException {
        writeStartLineAndHeaders();
    }
}
