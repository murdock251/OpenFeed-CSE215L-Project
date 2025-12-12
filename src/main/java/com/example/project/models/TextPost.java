package com.example.project.models;

public class TextPost extends Post {
    private int wordCount;

    public TextPost(String postId, String authorName, String content,
                    String timestamp, int likeCount, int wordCount) {
        super(postId, authorName, content, timestamp, likeCount);
        this.wordCount = wordCount;
    }

    public TextPost(String authorName, String content) {
        super(authorName, content);
        this.wordCount = calculateWordCount();
    }

    @Override
    public void display() {
        System.out.println("====== TEXT POST ======");
        System.out.println("Author: " + authorName);
        System.out.println("Content: " + content);
        System.out.println("Words: " + wordCount);
        System.out.println("Likes: " + likeCount);
        System.out.println("Comments: " + getCommentCount());
        System.out.println("Time: " + timestamp);
        System.out.println("=======================");
    }

    @Override
    public String getPostType() {
        return "TextPost";
    }

    public int getWordCount(){
        return wordCount;
    }

    private int calculateWordCount(){
        if(content == null || content.trim().isEmpty()){
            return 0;
        }
        return content.trim().split("\\s+").length;
    }
}