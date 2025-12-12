package com.example.project.controllers;

import com.example.project.Main;
import com.example.project.models.Post;
import com.example.project.models.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class FeedController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button viewProfileButton;
    @FXML
    private Button createPostButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button logoutButton;
    @FXML
    private VBox contentContainer;

    private ArrayList<Post> currentPosts;
    private static Post selectedPost;
    private boolean isProfileView = false;

    @FXML
    public void initialize() {
        System.out.println("=== FEED CONTROLLER INITIALIZE ===");

        User currentUser = Main.getSystemController().getAuthManager().getCurrentUser();

        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getName() + "!");
            System.out.println("User: " + currentUser.getName());
        }

        loadFeedView();
    }

    private void loadFeedView() {
        System.out.println("=== LOADING FEED VIEW ===");
        isProfileView = false;

        contentContainer.getChildren().clear();

        currentPosts = Main.getSystemController().getPostManager().getAllPosts();
        System.out.println("Found " + currentPosts.size() + " posts");

        if (currentPosts.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(50));

            Label emptyLabel = new Label("No posts yet. Be the first to post!");
            emptyLabel.getStyleClass().add("empty-message");

            emptyBox.getChildren().add(emptyLabel);
            contentContainer.getChildren().add(emptyBox);
        } else {
            for (int i = currentPosts.size() - 1; i >= 0; i--) {
                Post post = currentPosts.get(i);
                VBox postCard = createPostCard(post);
                contentContainer.getChildren().add(postCard);
            }
        }
    }

    private void loadProfileView() {
        System.out.println("=== LOADING PROFILE VIEW ===");
        isProfileView = true;

        contentContainer.getChildren().clear();

        User currentUser = Main.getSystemController().getAuthManager().getCurrentUser();

        if (currentUser == null) {
            showAlert("Error", "No user logged in!");
            return;
        }

        VBox profileCard = new VBox(15);
        profileCard.getStyleClass().add("profile-card");
        profileCard.setMaxWidth(700);
        profileCard.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button("← Back to Feed");
        backButton.getStyleClass().add("btn-back");
        backButton.setOnAction(e -> loadFeedView());

        Label profileTitle = new Label("PROFILE");
        profileTitle.getStyleClass().add("profile-title");

        Separator sep1 = new Separator();
        sep1.getStyleClass().add("profile-separator");

        VBox userInfo = new VBox(10);
        userInfo.getChildren().addAll(
                createInfoRow("Username:", currentUser.getUsername()),
                createInfoRow("Name:", currentUser.getName()),
                createInfoRow("Email:", currentUser.getEmail()),
                createInfoRow("Phone:", currentUser.getPhoneNumber().isEmpty() ? "Not provided" : currentUser.getPhoneNumber()),
                createInfoRow("Address:", currentUser.getAddress().isEmpty() ? "Not provided" : currentUser.getAddress())
        );

        Separator sep2 = new Separator();
        sep2.getStyleClass().add("profile-separator");

        int userPostCount = 0;
        for (Post post : Main.getSystemController().getPostManager().getAllPosts()) {
            if (post.getAuthorName().equals(currentUser.getUsername())) {
                userPostCount++;
            }
        }

        Label statsLabel = new Label("Total Posts: " + userPostCount);
        statsLabel.getStyleClass().add("info-value");
        statsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Edit Profile Button
        Button editButton = new Button("Edit Profile");
        editButton.getStyleClass().add("btn-edit-profile");
        editButton.setOnAction(e -> handleEditProfile());

        profileCard.getChildren().addAll(backButton, profileTitle, sep1, userInfo, sep2, statsLabel, editButton);

        contentContainer.getChildren().add(profileCard);

        Label myPostsLabel = new Label("MY POSTS");
        myPostsLabel.getStyleClass().add("section-title");
        VBox.setMargin(myPostsLabel, new Insets(30, 0, 10, 0));
        contentContainer.getChildren().add(myPostsLabel);

        ArrayList<Post> allPosts = Main.getSystemController().getPostManager().getAllPosts();
        boolean hasUserPosts = false;

        for (int i = allPosts.size() - 1; i >= 0; i--) {
            Post post = allPosts.get(i);
            if (post.getAuthorName().equals(currentUser.getUsername())) {
                VBox postCard = createPostCard(post);
                contentContainer.getChildren().add(postCard);
                hasUserPosts = true;
            }
        }

        if (!hasUserPosts) {
            Label noPosts = new Label("You haven't posted anything yet.");
            noPosts.getStyleClass().add("empty-message");
            contentContainer.getChildren().add(noPosts);
        }
    }

    private HBox createInfoRow(String label, String value) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        Label labelText = new Label(label);
        labelText.getStyleClass().add("info-label");
        labelText.setMinWidth(100);

        Label valueText = new Label(value);
        valueText.getStyleClass().add("info-value");

        row.getChildren().addAll(labelText, valueText);
        return row;
    }

    private VBox createPostCard(Post post) {
        VBox card = new VBox(12);
        card.getStyleClass().add("post-card");
        card.setMaxWidth(700);

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label authorLabel = new Label("👤 " + post.getAuthorName());
        authorLabel.getStyleClass().add("post-author");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label timeLabel = new Label(post.getTimestamp());
        timeLabel.getStyleClass().add("post-time");

        header.getChildren().addAll(authorLabel, spacer, timeLabel);

        Label typeLabel = new Label(post.getPostType());
        typeLabel.getStyleClass().add("post-type-badge");

        Label contentLabel = new Label();
        contentLabel.getStyleClass().add("post-content");
        contentLabel.setWrapText(true);

        String fullContent = post.getContent();
        boolean needsTruncation = fullContent.length() > 150;

        VBox contentBox = new VBox(5);

        if (needsTruncation) {
            contentLabel.setText(fullContent.substring(0, 150) + "...");

            Hyperlink seeMoreLink = new Hyperlink("See More");
            seeMoreLink.getStyleClass().add("see-more-link");

            final boolean[] isExpanded = {false};

            seeMoreLink.setOnAction(e -> {
                if (isExpanded[0]) {
                    contentLabel.setText(fullContent.substring(0, 150) + "...");
                    seeMoreLink.setText("See More");
                    isExpanded[0] = false;
                } else {
                    contentLabel.setText(fullContent);
                    seeMoreLink.setText("See Less");
                    isExpanded[0] = true;
                }
            });

            contentBox.getChildren().addAll(contentLabel, seeMoreLink);
        } else {
            contentLabel.setText(fullContent);
            contentBox.getChildren().add(contentLabel);
        }

        Separator separator = new Separator();

        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        Label likesLabel = new Label("👍 " + post.getLikeCount() + " Likes");
        likesLabel.getStyleClass().add("post-stats-label");

        Label commentsLabel = new Label("💬 " + post.getCommentCount() + " Comments");
        commentsLabel.getStyleClass().add("post-stats-label");

        statsBox.getChildren().addAll(likesLabel, commentsLabel);

        HBox actionsBox = new HBox(10);
        actionsBox.setAlignment(Pos.CENTER);

        Button likeButton = new Button("👍 Like");
        likeButton.getStyleClass().add("btn-like");
        likeButton.setPrefWidth(150);
        likeButton.setOnAction(e -> handleLikeFromFeed(post, likesLabel));

        Button commentButton = new Button("💬 Comment");
        commentButton.getStyleClass().add("btn-comment");
        commentButton.setPrefWidth(150);
        commentButton.setOnAction(e -> handleCommentClick(post));

        actionsBox.getChildren().addAll(likeButton, commentButton);

        card.getChildren().addAll(header, typeLabel, contentBox, separator, statsBox, actionsBox);

        return card;
    }

    @FXML
    private void handleCreatePost() {
        System.out.println("=== CREATE POST BUTTON CLICKED ===");
        try {
            Main.showCreatePostScreen();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Cannot open create post screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleViewProfile() {
        System.out.println("=== VIEW PROFILE BUTTON CLICKED ===");
        loadProfileView();
    }

    @FXML
    private void handleRefresh() {
        System.out.println("=== REFRESH BUTTON CLICKED ===");
        if (isProfileView) {
            loadProfileView();
        } else {
            loadFeedView();
        }
        showAlert("Refreshed", "Feed refreshed successfully!");
    }

    @FXML
    private void handleLogout() {
        System.out.println("=== LOGOUT BUTTON CLICKED ===");
        Main.getSystemController().getAuthManager().logout();
        Main.getSystemController().saveAll();

        try {
            Main.showLoginScreen();
        } catch (Exception e) {
            showAlert("Error", "Cannot logout: " + e.getMessage());
        }
    }

    private void handleLikeFromFeed(Post post, Label likesLabel) {
        System.out.println("=== LIKE BUTTON CLICKED ===");

        post.addLike();
        likesLabel.setText("👍 " + post.getLikeCount() + " Likes");

        Main.getSystemController().saveAll();
        System.out.println("Post liked! New count: " + post.getLikeCount());
    }

    private void handleCommentClick(Post post) {
        System.out.println("=== COMMENT BUTTON CLICKED ===");
        selectedPost = post;

        try {
            Main.showPostDetailScreen();
        } catch (Exception e) {
            showAlert("Error", "Cannot open post details: " + e.getMessage());
        }
    }

    private void handleEditProfile() {
        System.out.println("=== EDIT PROFILE BUTTON CLICKED ===");
        try {
            Main.showEditProfileScreen();
        } catch (Exception e) {
            showAlert("Error", "Cannot open edit profile: " + e.getMessage());
        }
    }

    public static Post getSelectedPost() {
        return selectedPost;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}