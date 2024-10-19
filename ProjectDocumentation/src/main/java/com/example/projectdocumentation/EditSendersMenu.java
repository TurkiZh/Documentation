package com.example.projectdocumentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class EditSendersMenu {



    @FXML private TextField new_sender_name;
    @FXML private ListView senders_listView;



    @FXML private Button back;
    @FXML void btn_back() throws IOException {
        saveOptionsToFile();
        Parent root = FXMLLoader.load(getClass().getResource("adminMainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }



    @FXML private Button add_sender;
    @FXML void btn_add_sender(){

        if (!new_sender_name.getText().equals("")) {
            if (!senders_list.contains(new_sender_name.getText())){
                senders_list.add(new_sender_name.getText());
                senders_listView.getItems().clear();
                senders_listView.getItems().addAll(get_senders_list());
                new_sender_name.clear();
                saveOptionsToFile();}
            else
                Alert.showAlert("خطأ","الأسم الذي تحاول اضافته موجود مسبقاً");
        }

    }



    @FXML private Button delete_sender;
    @FXML void btn_delete_sender(){

        if (!senders_listView.getSelectionModel().getSelectedItem().toString().equals("")){
            senders_list.remove(senders_listView.getSelectionModel().getSelectedItem().toString());
            senders_listView.getItems().clear();
            senders_listView.getItems().addAll(get_senders_list());
            saveOptionsToFile();

        }

    }




    private List<String> senders_list;
    private static final String SENDERS_FILE = "senders.txt";

    public EditSendersMenu(){
        senders_list = loadOptionsFromFile();
    }

    public void initialize(){
        senders_listView.getItems().addAll(get_senders_list());
    }

    public List<String> get_senders_list() {
        return senders_list;
    }

    public List<String> loadOptionsFromFile() {
        List<String> loadedOptions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SENDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedOptions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedOptions;
    }

    public void saveOptionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SENDERS_FILE))) {
            for (String option : senders_list) {
                writer.write(option);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
