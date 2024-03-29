package ru.vtarasov.java.student;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public abstract class Server implements ServerContext {
    private static final int PORT = 8080;
    private static final String ADDRESS = "http://localhost:" + PORT;

    private static final int THREADS_COUNT = 10;

    private final ServiceContainer serviceContainer = new ServiceContainer();
    private final RequestMapper requestMapper = new RequestMapper();

    public Server() {
        init();
    }

    protected abstract void init();

    @Override
    public String getAddress() {
        return ADDRESS;
    }

    @Override
    public RequestMapper getMapper() {
        return requestMapper;
    }

    @Override
    public ServiceContainer getContainer() {
        return serviceContainer;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    executorService.submit(() -> handleSocket(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSocket(Socket socket) {
        new SocketHandler(this, socket).handle();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
