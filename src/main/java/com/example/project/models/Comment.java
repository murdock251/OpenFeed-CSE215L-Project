package com.example.project.models;

public class Comment {
    private String commentId;
    private String postId;
    private String authorName;
    private String content;
    private String timestamp;

    public Comment(String commentId, String postId, String authorName, String content, String timestamp) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorName = authorName;
        this.content = content;
        this.timestamp = timestamp;
    }
    public Comment(String postId, String authorName, String content) {
        this.commentId = generateId();
        this.postId = postId;
        this.authorName = authorName;
        this.content = content;
        this.timestamp = getCurrentTimeStamp();
    }

    public String getCommentId() {
        return commentId;
    }

    public String getPostId() {
        return postId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    private String generateId(){
        return "C" + System.currentTimeMillis();
    }

    private String getCurrentTimeStamp(){
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date());
    }

    public void display(){
        System.out.println(authorName + ": " + content + " (" + timestamp + ")");
    }

    @Override
    public String toString() {
        return authorName + ": " + content;
    }
}