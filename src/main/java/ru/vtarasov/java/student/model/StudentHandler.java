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
    private final ObjectMapper json;

    public StudentHandler(ServerContext context) {
        this.context = context;
        this.json = new ObjectMapper();
    }

    @Override
    protected void handleGet(Request request, Response response) throws Exception {
        String id = extractIdFromPath(request, PATH);
        Student student = registrationService().find(id).orElseThrow(StudentNotFoundException::new);
        String studentJson = json.writeValueAsString(student);

        response.setStatus(Response.OK);
        response.setDescription(Response.OK_DESCRIPTION);
        response.addHeader("Content-Type", "application/json");
        response.writeResponse(studentJson);
    }

    @Override
    protected void handlePost(Request request, Response response) throws Exception {
        String studentJson = request.getBody();
        Student student = json.readValue(studentJson, Student.class);
        student = registrationService().register(student);

        response.setStatus(Response.CREATED);
        response.setDescription(Response.CREATED_DESCRIPTION);
        response.addHeader("Location", context.getAddress() + PATH + "/" + student.getId());
        response.writeResponse();
    }

    @Override
    protected void handleDelete(Request request, Response response) throws Exception {
        String id = extractIdFromPath(request, PATH);
        Student student = registrationService().find(id).orElseThrow(StudentNotFoundException::new);
        registrationService().unregister(student);

        response.setStatus(Response.OK);
        response.setDescription(Response.OK_DESCRIPTION);
        response.writeResponse();
    }

    @Override
    protected void handleException(Request request, Response response, Exception e) throws IOException {
        if (e instanceof StudentNotFoundException) {
            response.setStatus(Response.NOT_FOUND);
            response.setDescription(Response.NOT_FOUND_DESCRIPTION);
            response.writeResponse();
            return;
        }
        super.handleException(request, response, e);
    }

    private StudentRegistrationService registrationService() {
        return context.getContainer().get(StudentRegistrationService.class);
    }
}
