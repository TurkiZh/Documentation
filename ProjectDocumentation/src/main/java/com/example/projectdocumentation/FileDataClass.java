package com.example.projectdocumentation;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

class FileDataClass implements Serializable {
    private File file;
    private String filePath;
    private String title;
    private String date;
    private String sender;
    private String referenceNumber;
    private String name;


    public FileDataClass(File file, String title, String sender, String date, String referenceNumber) {
        this.file = file;
        this.title = title;
        this.date = date;
        this.sender = sender;
        this.referenceNumber = referenceNumber;
        this.filePath = file.getPath();
        this.name = sender + "_" + title + "_" + referenceNumber + "_" + date + ".pdf";
    }

    public File getFile() {
        return file;
    }

    public String getFilePath(){
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getName() { return name; }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDataClass that = (FileDataClass) o;
        return Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }

}
