package com.ecm.consulting.application.filereader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Starter {
    private static final Path PATH = Paths.get("Enter your root path here");

    public static void main(String[] args) {
        DocumentCounter documentCounter = new LocalCounter(PATH);
        try {
            documentCounter.totalCountOfDocuments(PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
