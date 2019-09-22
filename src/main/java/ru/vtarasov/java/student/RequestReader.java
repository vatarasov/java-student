package ru.vtarasov.java.student;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author vtarasov
 * @since 22.09.2019
 */
public class RequestReader {
    private final BufferedReader reader;

    public RequestReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String readStartLine() throws IOException {
        return reader.readLine();
    }

    public Map<String, String> readHeaders() throws IOException {
        Map headers = new HashMap<>();

        String headerLine = reader.readLine();
        while (headerLine != null && !headerLine.isEmpty()) {
            StringTokenizer tok = new StringTokenizer(headerLine, ":");
            String name = tok.nextToken();
            String value = tok.nextToken();
            headers.put(name.trim(), value.trim());
            headerLine = reader.readLine();
        }

        return headers;
    }

    public String readBody() throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine())!= null) {
            if (builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(line);
        }
        return builder.toString();
    }
}
