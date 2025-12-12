package com.example.project.models;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;
    public UserManager(){
        this.users =new ArrayList<>();
    }

    public void addUser(User user){
        users.add(user);
    }

    public User getUserByUsername(String username){
        for(User user: users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean usernameExists(String username){
        return getUserByUsername(username) != null;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public int getUserCount(){
        return users.size();
    }

    public void setUsers(ArrayList<User> users){
        this.users = users;
    }
}