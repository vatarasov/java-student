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
    private final Server server;
    private final Socket socket;

    public SocketHandler(Server server, Socket socket) {
        this.server = server;
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

            RequestHandler requestHandler = server.getMapper().getHandler(request.getPath());
            if (requestHandler == null) {
                System.out.println("Handler not found for path " + request.getPath());
                return;
            }
            requestHandler.handle(request, response);

            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
