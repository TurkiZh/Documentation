package com.example.projectdocumentation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class SaveDocuments {

    public static final String DOCUMENTS_FILE_PATH = "Documents_Library.dat";
    private List<FileDataClass> documentList;

    public SaveDocuments() {
        documentList = load_documents();
    }

    public void save_document(FileDataClass document) {
        if (!check_for_duplicate(document)) {
            documentList.add(document);
            transfer_PDF_file(document);
            save_documents();
        }
    }

    public List<FileDataClass> getAllDocuments() {
        return new ArrayList<>(documentList);
    }

    private void save_documents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOCUMENTS_FILE_PATH))) {
            oos.writeObject(documentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<FileDataClass> load_documents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DOCUMENTS_FILE_PATH))) {
            return (List<FileDataClass>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void delete_document(FileDataClass document){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOCUMENTS_FILE_PATH))) {
            documentList.remove(document);
            oos.writeObject(documentList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean check_for_duplicate(FileDataClass document) {
        for (FileDataClass existingDocument : documentList) {
            if (existingDocument.getTitle().equals(document.getTitle()) &&
                    existingDocument.getSender().equals(document.getSender()) &&
                    existingDocument.getDate().equals(document.getDate()) &&
                    existingDocument.getReferenceNumber().equals(document.getReferenceNumber())) {
                return true;
            }
        }
        return false;
    }


    private void transfer_PDF_file(FileDataClass document){
        try {
            String destinationDirectory = "files";
            String newFileName = document.getName();

            File directory = new File(destinationDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path sourcePath = document.getFile().toPath();
            Path destinationPath = directory.toPath().resolve(newFileName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            document.setFilePath(destinationPath.toString());

        } catch (IOException e) {
            System.out.println("Error transferring the file: " + e.getMessage());
        }
    }

    public void deletePdfFile(FileDataClass document) {
        try {
            Files.deleteIfExists(Path.of(document.getFilePath()));
        } catch (IOException e) {
            System.out.println("Error deleting PDF file: " + e.getMessage());
        }
    }

}