package com.example.projectdocumentation;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Register{



    @FXML private TextField new_username;
    @FXML private TextField new_password;
    @FXML private CheckBox admin;
    @FXML private ListView users_listView;



    @FXML private Button add_new_user;
    @FXML void  btn_add_new_user(){
        if (!new_username.getText().equals("") && !new_password.getText().equals("")){
            if (!accounts_List.contains(new_username.getText())){
                accounts_List.add(new Account(new_username.getText(), new_password.getText(), admin.isSelected()));
                usernames_List.add(new_username.getText());
                users_listView.getItems().clear();
                users_listView.getItems().addAll(get_usernames_list());
                new_username.clear();
                new_password.clear();
                save_accounts();
            } else
                Alert.showAlert("خطأ","اسم المستخدم الذي تحاول اضافته موجود بالفعل");

        }
    }



    @FXML private Button delete_user;
    @FXML void btn_delete_user(){
        boolean result = Alert.showConfirmation("رسالة","هل انت متأكد من حذف هذا الحساب؟");
        if (result){
            String selected_username = String.valueOf(users_listView.getSelectionModel().getSelectedItem());
            if (!selected_username.equals("Owner")){
                Account selected_account = null;
                for (Account account : accounts_List) {
                    if (account.getUsername().equals(selected_username)) {
                        selected_account = account;
                        break;
                    }
                }
                if (selected_account != null) {
                    accounts_List.remove(selected_account);
                    usernames_List.remove(selected_username);
                    users_listView.getItems().clear();
                    users_listView.getItems().addAll(usernames_List);
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERNAMES))) {
                        oos.writeObject(accounts_List);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else
                Alert.showAlert("خطأ","لايمكنك حذف هذا الحساب لكونه حساب احتياطي");
        }
    }



    @FXML private Button back;
    @FXML void btn_back() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMainMenu.fxml"));
        Scene Scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(Scene);
        stage.show();
    }






    private static final String USERNAMES = "users.dat";
    private List<Account> accounts_List;
    private List<String> usernames_List = new ArrayList<>();




    public Register(){
        accounts_List = load_accounts();
        for (Account account : accounts_List) {
            get_usernames_list().add(account.getUsername());
        }

    }


    public List<String> get_usernames_list(){
        return usernames_List;
    }


    public void initialize(){
        if (usernames_List != null)
            users_listView.getItems().addAll(usernames_List);
    }


    public String get_file_usernames(){
        return USERNAMES;
    }


    private List<Account> load_accounts(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERNAMES))) {
            return (List<Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private boolean check_for_duplicate(String username) {
        for (Account existingaccount : accounts_List) {
            if (existingaccount.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void save_accounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERNAMES))) {
            oos.writeObject(accounts_List);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
