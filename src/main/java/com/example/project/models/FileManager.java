package com.example.project.models;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String USERS_FILE = "users.txt";
    private static final String POSTS_FILE = "posts.txt";
    private static final String COMMENTS_FILE = "comments.txt";

    public static void saveUsers(ArrayList<User> users) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE));
        try {
            for (User user : users) {
                String line = user.getUserId() + "|" +
                        user.getUsername() + "|" +
                        user.getName() + "|" +
                        user.getPassword() + "|" +
                        user.getEmail() + "|" +
                        user.getPhoneNumber() + "|" +
                        user.getAddress();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✓ Saved " + users.size() + " users");
        } finally {
            writer.close();
        }
    }

    public static ArrayList<User> loadUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists()) {
            System.out.println("No users file found");
            return users;
        }

        BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    User user = new User(
                            parts[0],  // userId
                            parts[1],  // username
                            parts[2],  // name
                            parts[3],  // password
                            parts[4],  // email
                            parts[5],  // phoneNumber
                            parts[6]   // address
                    );
                    users.add(user);
                } else if (parts.length == 4) {
                    // Old format compatibility - migrate old users
                    User user = new User(
                            parts[0],  // userId
                            parts[1],  // username
                            parts[1],  // name = username
                            parts[2],  // password
                            parts[3],  // email
                            "",        // phoneNumber
                            ""         // address
                    );
                    users.add(user);
                }
            }
            System.out.println("✓ Loaded " + users.size() + " users");
        } catch (Exception e) {
            System.err.println("Error parsing user data: " + e.getMessage());
        } finally {
            reader.close();
        }

        return users;
    }

    public static void savePosts(ArrayList<Post> posts) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(POSTS_FILE));
        try {
            for (Post post : posts) {
                String line = post.getPostId() + "|" +
                        post.getPostType() + "|" +
                        post.getAuthorName() + "|" +
                        post.getContent() + "|" +
                        post.getTimestamp() + "|" +
                        post.getLikeCount() + "|";

                if (post instanceof TextPost) {
                    line += ((TextPost) post).getWordCount();
                } else if (post instanceof StatusPost) {
                    line += ((StatusPost) post).getMood();
                }

                writer.write(line);
                writer.newLine();
            }
            System.out.println("✓ Saved " + posts.size() + " posts");
        } finally {
            writer.close();
        }
    }

    public static ArrayList<Post> loadPosts() throws IOException {
        ArrayList<Post> posts = new ArrayList<>();
        File file = new File(POSTS_FILE);

        if (!file.exists()) {
            System.out.println("No posts file found");
            return posts;
        }

        BufferedReader reader = new BufferedReader(new FileReader(POSTS_FILE));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    String postId = parts[0];
                    String type = parts[1];
                    String author = parts[2];
                    String content = parts[3];
                    String timestamp = parts[4];
                    int likes = Integer.parseInt(parts[5]);

                    Post post;
                    if (type.equals("TextPost")) {
                        int wordCount = Integer.parseInt(parts[6]);
                        post = new TextPost(postId, author, content, timestamp, likes, wordCount);
                    } else {
                        String mood = parts[6];
                        post = new StatusPost(postId, author, content, timestamp, likes, mood);
                    }
                    posts.add(post);
                }
            }
            System.out.println("✓ Loaded " + posts.size() + " posts");
        } catch (Exception e) {
            System.err.println("Error parsing post data: " + e.getMessage());
        } finally {
            reader.close();
        }

        return posts;
    }

    public static void saveComments(ArrayList<Comment> comments) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(COMMENTS_FILE));
        try {
            for (Comment comment : comments) {
                String line = comment.getCommentId() + "|" +
                        comment.getPostId() + "|" +
                        comment.getAuthorName() + "|" +
                        comment.getContent() + "|" +
                        comment.getTimestamp();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✓ Saved " + comments.size() + " comments");
        } finally {
            writer.close();
        }
    }

    public static ArrayList<Comment> loadComments() throws IOException {
        ArrayList<Comment> comments = new ArrayList<>();
        File file = new File(COMMENTS_FILE);

        if (!file.exists()) {
            System.out.println("No comments file found");
            return comments;
        }

        BufferedReader reader = new BufferedReader(new FileReader(COMMENTS_FILE));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Comment comment = new Comment(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    comments.add(comment);
                }
            }
            System.out.println("✓ Loaded " + comments.size() + " comments");
        } finally {
            reader.close();
        }

        return comments;
    }

}
