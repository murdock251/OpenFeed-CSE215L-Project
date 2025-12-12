package com.example.project.models;

public class AuthManager {
    private UserManager userManager;
    private User currentUser;

    public AuthManager(UserManager userManager) {
        this.userManager = userManager;
        this.currentUser = null;
    }

    public void register(String username, String name, String password, String email, String phone, String address)
            throws DuplicateUserException {
        if (userManager.usernameExists(username)) {
            throw new DuplicateUserException("Username '" + username + "' already exists!");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters!");
        }

        User newUser = new User(username, name, password, email, phone, address);
        userManager.addUser(newUser);
        System.out.println("Registration successful for: " + name);
    }

    public void login(String username, String password) throws InvalidLoginException {
        User user = userManager.getUserByUsername(username);

        if (user == null) {
            throw new InvalidLoginException("Username does not exist!");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidLoginException("Incorrect password!");
        }

        currentUser = user;
        System.out.println("Login successful! Welcome, " + username);
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.getUsername());
            currentUser = null;
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}