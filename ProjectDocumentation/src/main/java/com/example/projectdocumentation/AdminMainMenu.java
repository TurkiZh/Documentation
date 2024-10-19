package com.example.projectdocumentation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AdminMainMenu {



    EditSendersMenu editSendersMenu = new EditSendersMenu();
    SaveDocuments saveDocuments = new SaveDocuments();
    FilesManager filesManager = new FilesManager();





    // SAVE SIDE






    File selected_file;

    @FXML private TextField uploaded_file_name;
    @FXML private TextField save_file_title;
    @FXML private TextField save_file_reference_number;
    @FXML private ChoiceBox save_file_sender;
    @FXML private DatePicker save_file_date;
    @FXML private Button upload_file;

    @FXML void btn_upload_file() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        selected_file = fileChooser.showOpenDialog(upload_file.getScene().getWindow());

        if (selected_file != null) {
            uploaded_file_name.setText(selected_file.getName());
        }
    }

    @FXML private Button delete_uploaded_file;
    @FXML void btn_delete_uploaded_file () {
        selected_file = null;
        uploaded_file_name.setText("");
    }

    @FXML private Label success_message;

    @FXML void btn_save_file () {
        if (save_file_title.getText().equals("") || save_file_sender.getValue().toString().equals("") ||
                save_file_date.getValue().toString().equals("") || save_file_reference_number.getText().equals("")){
            Alert.showAlert("خطأ","يرجى ارفاق ملف وادخال المعلومات كاملة");
        }

        else {
            FileDataClass fileDataClass = new FileDataClass(selected_file, save_file_title.getText(), save_file_sender.getValue().toString(),
                save_file_date.getValue().toString(), save_file_reference_number.getText());
            saveDocuments.save_document(fileDataClass);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0), event -> success_message.setVisible(true)),
                    new KeyFrame(Duration.seconds(3), event -> success_message.setVisible(false))
            );
            timeline.play();
            save_file_sender.setValue(null);
            save_file_title.clear();
            save_file_reference_number.clear();
            save_file_date.setValue(null);
            btn_delete_uploaded_file();
        }

    }







    // ========================= SEARCH SIDE ==========================


    @FXML private TextField search_file_title;
    @FXML private TextField search_file_reference_number;
    @FXML private DatePicker search_file_date;
    @FXML private ChoiceBox senders_list;

    List<FileDataClass> files_result;

    @FXML private ListView<String> result_ListView;

    @FXML private Button search;
    @FXML void btn_search(){
        result_ListView.getItems().clear();
        if(senders_list.getValue() == null && search_file_date.getValue() == null)
            files_result = filesManager.searchDocuments(search_file_title.getText(), null,
                null, search_file_reference_number.getText());

        else if (search_file_date.getValue() == null)
            files_result = filesManager.searchDocuments(search_file_title.getText(), senders_list.getValue().toString(),
                    null, search_file_reference_number.getText());

        else if (senders_list.getValue() == null)
            files_result = filesManager.searchDocuments(search_file_title.getText(), null,
                    search_file_date.getValue().toString(), search_file_reference_number.getText());

        else
            files_result = filesManager.searchDocuments(search_file_title.getText(), senders_list.getValue().toString(),
                    search_file_date.getValue().toString(), search_file_reference_number.getText());

        for (FileDataClass document : files_result)
            result_ListView.getItems().add(document.getName());
    }


    @FXML
    private Button open_selected_file;
    @FXML
    private void btn_open_selected_file() {
        String documentName = result_ListView.getSelectionModel().getSelectedItem();
        for (FileDataClass document : files_result) {
            if (document.getName().equals(documentName)) {
                String filePath = String.valueOf(document.getFilePath());
                try {
                    File file = new File(filePath);
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML private Button send_copy;
    @FXML void btn_send_copy(){
        String documentName = result_ListView.getSelectionModel().getSelectedItem();
        for (FileDataClass document : files_result) {
            if (document.getName().equals(documentName)) {
                try {
                    String sourceDirectory = "files";
                    String fileName = document.getName();

                    File sourceFile = new File(sourceDirectory, fileName);

                    String desktopPath = System.getProperty("user.home") + "/Desktop";
                    Path destinationPath = new File(desktopPath, fileName).toPath();

                    Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    System.out.println("Error copying the file: " + e.getMessage());
                }
            }
        }
    }

    @FXML void btn_reset_search(){
        search_file_title.clear();
        search_file_reference_number.clear();
        senders_list.setValue(null);
        search_file_date.setValue(null);
    }





    // ============================ other buttons ================================

    @FXML private Button users_control;
    @FXML private Button senders_control;
    @FXML private Button delete_file;
    @FXML private ImageView logo;

    @FXML void btn_users_control() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene Scene = new Scene(root);
        Stage stage = (Stage) users_control.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(Scene);
        stage.show();
    }

    @FXML void btn_senders_control() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("editSendersMenu.fxml"));
        Scene Scene = new Scene(root);
        Stage stage = (Stage) senders_control.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(Scene);
        stage.show();
    }

    @FXML void btn_delete_file() throws IOException {

        String documentName = result_ListView.getSelectionModel().getSelectedItem();
        for (FileDataClass document : files_result) {
            if (document.getName().equals(documentName)) {
                saveDocuments.delete_document(document);
                saveDocuments.deletePdfFile(document);
                result_ListView.getItems().remove(documentName);
            }
        }
    }

    public void initialize() throws MalformedURLException {
        senders_list.getItems().addAll(editSendersMenu.get_senders_list());
        save_file_sender.getItems().addAll(editSendersMenu.get_senders_list());
        File imageFile = new File("logo.png");
        String imageUrl = imageFile.toURI().toURL().toString();

        Image image = new Image(imageUrl);
        logo.setImage(image);
    }





    @FXML Button logout;
    @FXML void btn_logout() throws IOException {
        boolean result = Alert.showConfirmation("رسالة","هل تريد تسجيل الخروج");
        if (result){
            Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Scene Scene = new Scene(root);
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(Scene);
            stage.show();
        }
    }

}
