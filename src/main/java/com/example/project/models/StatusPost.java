package com.example.project.models;

public class StatusPost extends Post{
    private String mood;

    public StatusPost(String postId, String authorName, String content,
                      String timestamp, int likeCount, String mood) {
        super(postId, authorName, content, timestamp, likeCount);
        this.mood = mood;
    }

    public StatusPost(String authorName, String content, String mood) {
        super(authorName, content);
        this.mood = mood;
    }

    @Override
    public void display() {
        System.out.println("====== STATUS POST ======");
        System.out.println("Author: " + authorName);
        System.out.println("Mood: " + mood + " 😊");
        System.out.println("Content: " + content);
        System.out.println("Likes: " + likeCount);
        System.out.println("Comments: " + getCommentCount());
        System.out.println("Time: " + timestamp);
        System.out.println("=========================");
    }

    @Override
    public String getPostType() {
        return "StatusPost";
    }

    public String getMood(){
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}