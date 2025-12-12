package com.example.project.controllers;

import com.example.project.Main;
import com.example.project.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreatePostController {
    @FXML
    private TextArea contentArea;
    @FXML
    private Label charCountLabel;

    @FXML
    public void initialize() {
        System.out.println("=== CREATE POST CONTROLLER INITIALIZE ===");

        contentArea.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            charCountLabel.setText(length + " / 500 characters");

        });

        System.out.println("Create Post Controller initialized successfully");
    }

    @FXML
    private void handlePost() {
        System.out.println("=== POST BUTTON CLICKED ===");

        String content = contentArea.getText().trim();

        System.out.println("Content length: " + content.length());

        if (content.isEmpty()) {
            System.out.println("Content is empty");
            showAlert("Error", "Content cannot be empty!");
            return;
        }

        if (content.length() > 500) {
            System.out.println("Content too long");
            showAlert("Error", "Content too long! Maximum 500 characters.");
            return;
        }

        User currentUser = Main.getSystemController()
                .getAuthManager()
                .getCurrentUser();

        if (currentUser == null) {
            System.out.println("No user logged in");
            showAlert("Error", "You must be logged in to post!");
            return;
        }

        System.out.println("Current user: " + currentUser.getUsername());

        try {
            System.out.println("Creating TextPost...");
            Main.getSystemController()
                    .getPostManager()
                    .createPost(content, currentUser.getUsername());

            System.out.println("Saving data...");
            Main.getSystemController().saveAll();

            showAlert("Success", "Post created successfully!");

            System.out.println("Navigating back to feed...");

            Main.showFeedScreen();

        } catch (Exception e) {
            System.out.println("Error creating post: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Failed to create post: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        System.out.println("=== CANCEL BUTTON CLICKED ===");
        try {
            Main.showFeedScreen();
        } catch (Exception e) {
            System.out.println("Error returning to feed: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Cannot return to feed: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}