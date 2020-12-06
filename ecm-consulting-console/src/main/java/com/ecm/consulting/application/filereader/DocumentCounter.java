package com.ecm.consulting.application.filereader;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class DocumentCounter extends SimpleFileVisitor<Path> {

    public abstract void totalCountOfDocuments(Path directoryPath) throws IOException;

    @Override
    public abstract FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException;

}
