package com.example.project.models;

import java.util.ArrayList;

public class PostManager implements Searchable{
    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;

    public PostManager(){
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Post createPost(String content,String authorName){
        Post post = new TextPost(authorName,content);
        posts.add(post);
        return post;
    }


    public Post createPost(String content,String authorName, String mood){
        Post post = new StatusPost(authorName,content,mood);
        posts.add(post);
        return post;
    }
    public boolean deletePost(String postId){
        for(int i=0; i<posts.size();i++){
            if(posts.get(i).getPostId().equals(postId)){
                posts.remove(i);
                return true;
            }
        }
        return false;
    }

    public Post getPostById(String postId){
        for(Post post: posts){
            if(post.getPostId().equals(postId)){
                return post;
            }
        }
        return null;
    }
    public ArrayList<Post>getAllPosts(){
        return posts;
    }

    public void addLike(String postId){
        Post post = getPostById(postId);
        if(post!=null){
            post.addLike();
        }
    }

    public void addComment(Comment comment){
        comments.add(comment);
        Post post = getPostById(comment.getPostId());
        if(post != null){
            post.addComment(comment);
        }
    }

    @Override
    public ArrayList<Post> search(String keyword) {
        ArrayList<Post>results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for(Post post: posts){
            if(post.getContent().toLowerCase().contains(lowerKeyword)){
                post.getAuthorName().toLowerCase().contains(lowerKeyword);
                results.add(post);
            }
        }
        return results;
    }

    public void setPosts(ArrayList<Post> posts){
        this.posts = posts;
    }

    public void setComments(ArrayList<Comment> comments){
        this.comments = comments;
    }

    public ArrayList<Comment> getAllComments(){
        return comments;
    }
}