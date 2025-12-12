package com.example.project.controllers;

import com.example.project.Main;
import com.example.project.models.InvalidLoginException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(){
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()){
            showError("Please fill in all fields");
            return;
        }

        try{
            Main.getSystemController().getAuthManager().login(username,password);
            Main.showFeedScreen();
        } catch (InvalidLoginException e){
            showError(e.getMessage());
        } catch (Exception e){
            showError("An error occurred: " + e.getMessage());
        }

    }

    @FXML
    private void handleGoToRegister(){
        try{
            Main.showRegisterScreen();
        } catch (Exception e){
            showError("Cannot open registration: " + e.getMessage());
        }
    }

    private void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
