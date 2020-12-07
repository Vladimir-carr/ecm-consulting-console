package com.ecm.consulting.application.filereader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Starter {
    private static final Path path = Paths.get("Enter your root path here");

    public static void main(String[] args) {
        DocumentCounter documentCounter = new LocalCounter(path);
        try {
            documentCounter.totalCountOfDocuments(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
