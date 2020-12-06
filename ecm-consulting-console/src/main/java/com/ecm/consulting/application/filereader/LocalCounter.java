package com.ecm.consulting.application.filereader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.MAX_VALUE;

public class LocalCounter extends DocumentCounter {

    private static final Logger LOG = LoggerFactory.getLogger(LocalCounter.class);

    private Map<String, Integer> result = new HashMap<>();

    public LocalCounter(Path directoryPath) {
    }

    public LocalCounter(Map<String, Integer> result) {
        this.result = result;
    }

    @Override
    public void totalCountOfDocuments(Path directoryPath) throws IOException {
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(directoryPath, options, MAX_VALUE, new LocalCounter(result));

        documentCountResult(result);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
        String fileName = file.toAbsolutePath().toString();
        if (fileName.endsWith(FileExtension.PDF.getFileExtension())) {
            int countOfPdfPages = readPdfFile(file);
            result.put(fileName, countOfPdfPages);
            return FileVisitResult.CONTINUE;
        }
        if (fileName.endsWith(FileExtension.DOCX.getFileExtension())) {
            int countOfWordPages = readWordFile(file);
            result.put(fileName, countOfWordPages);
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }

    private int readWordFile(Path nameOfFile) throws IOException {
        String getCountOfPages = nameOfFile.toAbsolutePath().toString();
        try(XWPFDocument docx = new XWPFDocument(new FileInputStream(getCountOfPages))) {
            return docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        }
    }

    private int readPdfFile(Path nameOfFile) throws IOException {
        String getCountOfPages = nameOfFile.toAbsolutePath().toString();
        try(PDDocument document = PDDocument.load(new FileInputStream(getCountOfPages))) {
            return document.getNumberOfPages();
        }
    }

    public void documentCountResult(Map<String, Integer> result) {
        int totalDocs = 0;
        int totalPages = 0;
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            if (!entry.getKey().isEmpty()) {
                totalDocs++;
            }
            totalPages += entry.getValue();
        }
        LOG.info("Documents: {}'\n'Pages: {}",totalDocs, totalPages);
        System.out.println("Documents: " + totalDocs + "\n" + "Pages: " + totalPages);
    }
}
