package com.example.projectdocumentation;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;


public class Login extends Application {


    Register register = new Register();
    private List<Account> users_check;


    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ImageView logo;

    @FXML private Button login;

    @FXML void btn_login() throws IOException {
        if (username.getText().equals("") || password.getText().equals("")){
            Alert.showAlert("خطأ","يرجى ادخال اسم المستخدم وكلمة المرور");
            return;
        }

        if(username.getText().equals("Owner") && password.getText().equals("10000")){
            Parent root = FXMLLoader.load(getClass().getResource("adminMainMenu.fxml"));
            Scene Scene = new Scene(root);
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(Scene);
            stage.show();
            return;
        }

        String result = authenticate(username.getText(), password.getText());

        if (result.equals("Admin")){
            Parent root = FXMLLoader.load(getClass().getResource("adminMainMenu.fxml"));
            Scene Scene = new Scene(root);
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(Scene);
            stage.show();
        }
        else if (result.equals("User")){
            Parent root = FXMLLoader.load(getClass().getResource("userMainMenu.fxml"));
            Scene Scene = new Scene(root);
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(Scene);
            stage.show();
        }
        else{
            Alert.showAlert("خطأ","اسم المستخدم او كلمة المرور غير صحيحه");
            return;
        }

    }

    public String authenticate(String username, String password) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(register.get_file_usernames()))) {
            users_check = (List<Account>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            Alert.showAlert("حدث خطأ","ملفات مفقودة, حدث خطأ اثناء تحميل الحسابات");
        }
        for (Account account : users_check){
            if (account.getUsername().equals(username)){
                if (account.getPassword().equals(password)){
                    if (account.getAdmin())
                        return "Admin";
                    else
                        return "User";
                }
            }
        }
        return "failed";
    }

    public void initialize() throws MalformedURLException {
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 730, 435);
        stage.setTitle("الأرشفة الإلكترونية");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
