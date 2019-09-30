package ru.vtarasov.java.student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class SocketHandler {
    private final ServerContext context;
    private final Socket socket;

    public SocketHandler(ServerContext context, Socket socket) {
        this.context = context;
        this.socket = socket;
    }

    public void handle() {
        try {
            Request request = createRequest();
            Response response = createResponse();

            RequestHandler requestHandler = getHandler(request);
            if (requestHandler == null) {
                handlerNotFound(request, response);
                return;
            }
            requestHandler.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request createRequest() throws IOException {
        BufferedReader reader =
            new BufferedReader(
                new InputStreamReader(socket.getInputStream(), Charset.forName("utf-8")));

        return new Request(
            new RequestReader(reader));
    }

    private Response createResponse() throws IOException {
        BufferedWriter writer =
            new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), Charset.forName("utf-8")));

        return new Response(
            new ResponseWriter(writer));
    }

    private RequestHandler getHandler(Request request) {
        return context.getMapper().getHandler(request.getPath());
    }

    private void handlerNotFound(Request request, Response response) throws IOException {
        System.out.println("Handler not found for path " + request.getPath());

        response.setStatus(Response.NOT_FOUND);
        response.setDescription(Response.NOT_FOUND_DESCRIPTION);
        response.writeResponse();
    }
}
