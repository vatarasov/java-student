package ru.vtarasov.java.student.app;

import ru.vtarasov.java.student.model.StudentServer;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        new StudentServer().run();
    }
}
