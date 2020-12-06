package com.ecm.consulting.application.filereader;

public enum FileExtension {

    DOCX(".docx"),
    PDF(".pdf");

    String extension;

    FileExtension(String fileExtension) {
        this.extension = fileExtension;
    }

    public String getFileExtension() {
        return extension;
    }
}
