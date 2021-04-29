package com.testvagrant.optimus.dashboard.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonWriter {

    public <T> void write(T instance, String path) {
        String serialize = GsonParser.toInstance().serialize(instance);
        FileWriter file = null;
        try {
            file = new FileWriter(path.toString());
            file.write(serialize);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
