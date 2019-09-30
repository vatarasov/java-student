package ru.vtarasov.java.student;

import ru.vtarasov.java.student.model.StudentServer;

/**
 * @author vtarasov
 * @since 30.09.2019
 */
public class ServerRunner {
    public static void run(Server server) throws IllegalStateException {
        Thread thread = new Thread(() -> new StudentServer().run());
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Server didn't run");
        }
    }
}
