package com.example.projectdocumentation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilesManager {

    private static final String DOCUMENTS_FILE_PATH = "Documents_Library.dat";

    private List<FileDataClass> files_list;

    public FilesManager() {
        files_list = loadDocuments();
    }

    public List<FileDataClass> getAllDocuments() {
        return loadDocuments();
    }


    public List<FileDataClass> searchDocuments(String title, String sender, String date, String reference_number) {
        List<FileDataClass> searchResults = new ArrayList<>();
        files_list = loadDocuments();
        for (FileDataClass document : files_list) {
            if ((title == null || title.isEmpty() || document.getTitle().equalsIgnoreCase(title))
                    && (sender == null || sender.isEmpty() || document.getSender().equalsIgnoreCase(sender))
                    && (date == null || document.getDate().equals(date))
                    && (reference_number == null || reference_number.isEmpty() || document.getReferenceNumber().equalsIgnoreCase(reference_number))) {
                searchResults.add(document);
            }
        }

        return searchResults;
    }

    private List<FileDataClass> loadDocuments() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DOCUMENTS_FILE_PATH))) {
            return (List<FileDataClass>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
