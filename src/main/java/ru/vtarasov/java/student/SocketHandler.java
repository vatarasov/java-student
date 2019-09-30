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
            BufferedReader reader =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), Charset.forName("utf-8")));

            Request request =
                new Request(
                    new RequestReader(reader));

            BufferedWriter writer =
                new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), Charset.forName("utf-8")));

            Response response =
                new Response(
                    new ResponseWriter(writer));

            RequestHandler requestHandler = context.getMapper().getHandler(request.getPath());
            if (requestHandler == null) {
                System.out.println("Handler not found for path " + request.getPath());
                response.setStatus("404");
                response.setDescription("Not Found");
                response.writeResponse("");
                return;
            }
            requestHandler.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
