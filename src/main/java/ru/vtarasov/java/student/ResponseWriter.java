package ru.vtarasov.java.student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class ResponseWriter {
    private final BufferedWriter writer;

    public ResponseWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void writeStartLine(int status, String description) throws IOException {
        writer.write("HTTP/1.1 ");
        writer.write(String.valueOf(status));
        if (description != null) {
            writer.write(" ");
            writer.write(description);
        }
        writer.newLine();
    }

    public void writeHeaders(Map<String, String> headers) throws IOException {
        for (Entry<String, String> header : headers.entrySet()) {
            writer.write(header.getKey());
            writer.write(": ");
            writer.write(header.getValue());
            writer.newLine();
        }
        writer.newLine();
    }

    public void writeBody(String body) throws IOException {
        writer.write(body);
    }

    public void flush() throws IOException {
        writer.flush();
    }
}
