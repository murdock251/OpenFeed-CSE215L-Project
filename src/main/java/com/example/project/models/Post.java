package com.example.project.models;

import java.util.ArrayList;

public abstract class Post implements Likeable{
    protected String postId;
    protected String authorName;
    protected String content;
    protected String timestamp;
    protected int likeCount;
    protected ArrayList<Comment> comments;

    public Post(String postId, String authorName, String content,
                String timestamp, int likeCount) {
        this.postId = postId;
        this.authorName = authorName;
        this.content = content;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
        this.comments = new ArrayList<>();
    }

    public Post(String authorName, String content) {
        this.postId = generateId();
        this.authorName = authorName;
        this.content = content;
        this.timestamp = getCurrentTimeStamp();
        this.likeCount = 0;
        this.comments = new ArrayList<>();
    }

    public abstract void display();
    public abstract String getPostType();

    @Override
    public void addLike() {
        likeCount++;
    }

    @Override
    public void removeLike() {
        if(likeCount>0){
            likeCount--;
        }
    }

    @Override
    public int getLikeCount() {
        return likeCount;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }
    public ArrayList<Comment> getComments(){
        return comments;
    }
    public int getCommentCount(){
        return comments.size();
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
        return "P" + System.currentTimeMillis();
    }

    private String getCurrentTimeStamp(){
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new java.util.Date());
    }




}