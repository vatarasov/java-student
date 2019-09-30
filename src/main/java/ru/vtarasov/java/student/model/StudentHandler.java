package ru.vtarasov.java.student.model;

/**
 *
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import ru.vtarasov.java.student.Request;
import ru.vtarasov.java.student.RequestHandlerBase;
import ru.vtarasov.java.student.Response;
import ru.vtarasov.java.student.ServerContext;

public class StudentHandler extends RequestHandlerBase {

    @RequiredArgsConstructor
    private final class StudentNotFoundException extends Exception {}

    public static final String PATH = "/student";

    private final ServerContext context;
    private final StudentRegistrationService service;
    private final ObjectMapper json;

    public StudentHandler(ServerContext context) {
        this.context = context;
        this.service = context.getContainer().get(StudentRegistrationService.class);
        this.json = new ObjectMapper();
    }

    @Override
    protected void handleGet(Request request, Response response) throws Exception {
        String id = getIdFromPath(request);
        Student student = service.find(id).orElseThrow(StudentNotFoundException::new);
        String studentJson = json.writeValueAsString(student);

        response.setStatus("200");
        response.setDescription("Ok");
        response.addHeader("Content-Type", "application/json");
        response.writeResponse(studentJson);
    }

    @Override
    protected void handlePost(Request request, Response response) throws Exception {
        String studentJson = request.getBody();
        Student student = json.readValue(studentJson, Student.class);
        student = service.register(student);

        response.setStatus("201");
        response.setDescription("Created");
        response.addHeader("Location", context.getAddress() + PATH + "/" + student.getId());
        response.writeResponse("");
    }

    @Override
    protected void handleDelete(Request request, Response response) throws Exception {
        String id = getIdFromPath(request);
        Student student = service.find(id).orElseThrow(StudentNotFoundException::new);
        service.unregister(student);

        response.setStatus("200");
        response.setDescription("Ok");
        response.writeResponse("");
    }

    @Override
    protected void handleException(Request request, Response response, Exception t) throws IOException {
        if (t instanceof StudentNotFoundException) {
            response.setStatus("404");
            response.setDescription("Not Found");
            response.writeResponse("");
            return;
        }
        super.handleException(request, response, t);
    }

    private String getIdFromPath(Request request) {
        return request.getPath().substring(request.getPath().indexOf(PATH) + PATH.length() + 1);
    }
}
