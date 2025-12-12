package com.example.project.controllers;

import com.example.project.Main;
import com.example.project.models.DuplicateUserException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showMessage("Please fill in all required fields (Username, Name, Email, Password)", true);
            return;
        }

        if (!email.contains("@")) {
            showMessage("Invalid email format", true);
            return;
        }

        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters", true);
            return;
        }

        try {
            Main.getSystemController()
                    .getAuthManager()
                    .register(username, name, password, email, phone, address);
            System.out.println("Saving user to file...");
            Main.getSystemController().saveAll();
            System.out.println("User saved successfully!");

            showMessage("Registration successful! Redirecting to login...", false);

            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(() -> {
                        try {
                            Main.showLoginScreen();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (DuplicateUserException e) {
            showMessage(e.getMessage(), true);
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            Main.showLoginScreen();
        } catch (Exception e) {
            showMessage("Cannot go back: " + e.getMessage(), true);
        }
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setTextFill(isError ? Color.RED : Color.GREEN);
        messageLabel.setVisible(true);
    }
}


