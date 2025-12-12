/*
CSE215 Lab Project(Programming Language II)
Faculty: RRN
Done by,
Noor Mostafa Nafis(2512829642)
Abdullah Al Muntasir Talukdar(2512029042)
*/

package com.example.project;

import com.example.project.controllers.SystemController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static SystemController systemController;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        systemController = new SystemController();
        systemController.initialize();

        showLoginScreen();

        primaryStage.setOnCloseRequest(event -> {
            systemController.saveAll();
            System.out.println("Application closed");
        });
    }

    public static void showLoginScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("login.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void showRegisterScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("register.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void showFeedScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("feed.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }

    public static void showCreatePostScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("createPost.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void showPostDetailScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("postDetail.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }

    public static void showEditProfileScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("editProfile.fxml"));
        primaryStage.setTitle("OpenFeed");
        Image e = new Image("logo.png");
        primaryStage.getIcons().add(e);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static SystemController getSystemController() {
        return systemController;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}