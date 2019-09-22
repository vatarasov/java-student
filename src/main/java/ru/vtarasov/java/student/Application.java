package ru.vtarasov.java.student;

import java.io.IOException;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        new Server().run();
    }
}
