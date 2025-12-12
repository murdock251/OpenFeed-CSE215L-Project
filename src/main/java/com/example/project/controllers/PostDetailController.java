package com.example.project.controllers;

import com.example.project.Main;
import com.example.project.models.Comment;
import com.example.project.models.Post;
import com.example.project.models.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PostDetailController {
    @FXML
    private Label authorLabel;
    @FXML
    private Label timestampLabel;
    @FXML
    private Label postTypeLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private Label likesLabel;
    @FXML
    private Label commentCountLabel;
    @FXML
    private Label charCountLabel;
    @FXML
    private Button likeButton;
    @FXML
    private VBox commentsContainer;
    @FXML
    private TextArea commentArea;

    private Post currentPost;

    @FXML
    public void initialize() {
        System.out.println("=== POST DETAIL CONTROLLER INITIALIZE ===");

        currentPost = FeedController.getSelectedPost();

        if (currentPost == null) {
            System.err.println("ERROR: No post selected!");
            showAlert("Error", "No post selected");
            return;
        }

        System.out.println("Loading post: " + currentPost.getPostId());

        commentArea.textProperty().addListener((obs, oldText, newText) -> {
            int length = newText.length();
            charCountLabel.setText(length + " / 300 characters");

        });

        loadPostDetails();
    }

    private void loadPostDetails() {
        System.out.println("=== LOADING POST DETAILS ===");

        authorLabel.setText("👤 " + currentPost.getAuthorName());
        timestampLabel.setText(currentPost.getTimestamp());
        postTypeLabel.setText(currentPost.getPostType());
        contentLabel.setText(currentPost.getContent());
        likesLabel.setText("👍 " + currentPost.getLikeCount() + " Likes");

        loadComments();

        System.out.println("Post details loaded successfully");
    }

    private void loadComments() {
        System.out.println("Loading comments...");
        ArrayList<Comment> comments = currentPost.getComments();
        System.out.println("Found " + comments.size() + " comments");

        commentsContainer.getChildren().clear();

        if (comments.isEmpty()) {
            Label noComments = new Label("No comments yet. Be the first to comment!");
            noComments.getStyleClass().add("no-comments");
            commentsContainer.getChildren().add(noComments);
        } else {
            for (Comment comment : comments) {
                VBox commentCard = createCommentCard(comment);
                commentsContainer.getChildren().add(commentCard);
            }
        }

        commentCountLabel.setText("(" + comments.size() + ")");
    }

    private VBox createCommentCard(Comment comment) {
        VBox card = new VBox(8);
        card.getStyleClass().add("comment-card");
        card.setMaxWidth(650);

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label authorLabel = new Label("👤 " + comment.getAuthorName());
        authorLabel.getStyleClass().add("comment-author");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label timeLabel = new Label(comment.getTimestamp());
        timeLabel.getStyleClass().add("comment-time");

        header.getChildren().addAll(authorLabel, spacer, timeLabel);

        Label contentLabel = new Label(comment.getContent());
        contentLabel.getStyleClass().add("comment-content");
        contentLabel.setWrapText(true);

        card.getChildren().addAll(header, contentLabel);

        return card;
    }

    @FXML
    private void handleLike() {
        System.out.println("=== LIKE BUTTON CLICKED ===");

        if (currentPost == null) {
            System.err.println("ERROR: No post loaded!");
            showAlert("Error", "No post loaded!");
            return;
        }

        currentPost.addLike();
        System.out.println("Like added. Total likes: " + currentPost.getLikeCount());

        likesLabel.setText("👍 " + currentPost.getLikeCount() + " Likes");

        Main.getSystemController().saveAll();
        System.out.println("Changes saved");

        String originalStyle = likeButton.getStyle();
        likeButton.setStyle("-fx-background-color: #2980b9;");

        new Thread(() -> {
            try {
                Thread.sleep(200);
                javafx.application.Platform.runLater(() -> likeButton.setStyle(originalStyle));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleAddComment() {
        System.out.println("=== ADD COMMENT BUTTON CLICKED ===");

        String content = commentArea.getText().trim();
        System.out.println("Comment content length: " + content.length());

        if (content.isEmpty()) {
            System.out.println("Comment is empty");
            showAlert("Error", "Comment cannot be empty!");
            return;
        }

        if (content.length() > 300) {
            System.out.println("Comment too long");
            showAlert("Error", "Comment too long! Maximum 300 characters.");
            return;
        }

        User currentUser = Main.getSystemController()
                .getAuthManager()
                .getCurrentUser();

        if (currentUser == null) {
            System.err.println("ERROR: No user logged in!");
            showAlert("Error", "You must be logged in to comment!");
            return;
        }

        System.out.println("Creating comment for user: " + currentUser.getUsername());

        Comment comment = new Comment(
                currentPost.getPostId(),
                currentUser.getUsername(),
                content
        );

        Main.getSystemController()
                .getPostManager()
                .addComment(comment);

        System.out.println("Comment added to post and manager");
        Main.getSystemController().saveAll();
        System.out.println("Changes saved");

        commentArea.clear();

        loadComments();

    }

    @FXML
    private void handleBack() {
        System.out.println("=== BACK BUTTON CLICKED ===");
        try {
            Main.showFeedScreen();
        } catch (Exception e) {
            System.err.println("Error returning to feed: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Cannot go back: " + e.getMessage());
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