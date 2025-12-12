package com.example.project.controllers;

import com.example.project.models.*;

import java.io.IOException;
import java.util.ArrayList;

public class SystemController {
    private AuthManager authManager;
    private UserManager userManager;
    private PostManager postManager;

    public SystemController(){
        this.userManager = new UserManager();
        this.postManager = new PostManager();
        this.authManager = new AuthManager(userManager);
    }

    public void initialize(){
        System.out.println("=== System initialized ====");

        try{
            ArrayList<User> users = FileManager.loadUsers();
            userManager.setUsers(users);

            ArrayList<Post> posts = FileManager.loadPosts();
            postManager.setPosts(posts);

            ArrayList<Comment> comments = FileManager.loadComments();
            postManager.setComments(comments);

            for(Comment comment : comments){
                Post post = postManager.getPostById(comment.getPostId());
                if(post!=null){
                    post.addComment(comment);
                }
            }

            System.out.println("System ready");
        }
        catch(IOException e){
            System.err.println("Error loading data: " + e.getMessage());
        }
        catch (NumberFormatException e){
            System.err.println("Corrupted data file: " + e.getMessage());
        } finally {
            System.out.println("Initialization complete");
        }
    }

    public void saveAll(){
        System.out.println("=== Saving data ===");
        try{
            FileManager.saveUsers(userManager.getAllUsers());
            FileManager.savePosts(postManager.getAllPosts());
            FileManager.saveComments(postManager.getAllComments());
        } catch (IOException e){
            System.err.println("Error saving data: " + e.getMessage());
        } finally {
            System.out.println("Save operation complete");
        }


    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public PostManager getPostManager() {
        return postManager;
    }
    public UserManager getUserManager() {
        return userManager;
    }



}