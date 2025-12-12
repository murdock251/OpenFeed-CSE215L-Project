# 🌐 OpenFeed — Social Media Application

A JavaFX-based social media application demonstrating key Object-Oriented Programming (OOP) principles and the **Model-View-Controller (MVC)** architecture.  
This project was developed as part of **CSE215 – Programming Language II** at **North South University**.

---

## 👥 Team Members

| Name | ID |
|------|------|
| Noor Mostafa Nafis | 2512829642 |
| Abdullah Al Muntasir Talukdar | 2512029042 |

---

## 🎯 Objectives

The goal of this project is to develop a **multi-layered social media application** that implements:
- User Authentication (Login, Registration)
- Polymorphic Post Management (Text & Status Posts)
- Commenting and Liking features
- Persistent data storage using file handling
- Clear application of **Encapsulation, Inheritance, Polymorphism, Abstraction**
- Functional **MVC architecture** using JavaFX

---

## 🧩 System Overview

**OpenFeed** simulates a simple social media platform where users can:
- Register and log in
- Create text or status posts
- Like and comment on posts
- View and edit their profile
- Persist all user and post data locally in text files

The project follows a strict **MVC design pattern**:

| Layer | Description |
|-------|--------------|
| **Model** | Business logic classes: `User`, `Post`, `Comment`, `UserManager`, `PostManager`, `AuthManager`, etc. |
| **View** | JavaFX FXML interfaces (e.g., Login, Register, Feed, Create Post, Profile). |
| **Controller** | JavaFX controllers (e.g., `LoginController`, `FeedController`, `CreatePostController`). |

---

## 🏗️ Object-Oriented Concepts Demonstrated

### 🔒 Encapsulation
Private fields with public getters/setters control access.  
Example: `User.username` and `User.userId` are read-only to prevent tampering.

### 🧬 Inheritance
`TextPost` and `StatusPost` inherit from the abstract parent class `Post`.

### 🔁 Polymorphism
- Method overriding for `display()` in subclasses  
- Interface-based polymorphism via `Likeable` and `Searchable`

### 🧠 Abstraction
- Abstract class: `Post` defines shared structure for all posts  
- Interfaces: `Likeable` and `Searchable` abstract reusable behaviors

### ⚙️ Composition & Aggregation
- `Post` **composes** multiple `Comment` objects  
- `AuthManager` **aggregates** a `UserManager`

### 🧯 Exception Handling
Custom exceptions for cleaner logic:
- `InvalidLoginException`
- `DuplicateUserException`
- `IOException` handling in `FileManager`

### 💾 File Handling
All user and post data are stored in text files (`users.txt`, `posts.txt`, `comments.txt`) using the `FileManager` class.

---

## 🧱 Class Overview

| Class | Purpose |
|-------|----------|
| **User** | Represents users and their credentials. |
| **UserManager** | Manages users (add, find, validate). |
| **AuthManager** | Handles login and registration logic. |
| **Post** | Abstract parent for all posts (implements `Likeable`). |
| **TextPost / StatusPost** | Specific post types with unique properties. |
| **Comment** | Represents user comments on posts. |
| **PostManager** | Manages posts and comments (implements `Searchable`). |
| **FileManager** | Handles saving/loading data from text files. |
| **SystemController** | Central controller that links all managers. |
| **FeedController, LoginController, etc.** | Handle UI logic and navigation in JavaFX. |

---

## 🖥️ GUI Previews

| Login Screen | Register Screen | Feed |
|---------------|----------------|------|
| <img src="https://github.com/user-attachments/assets/3428b780-6865-40c5-914e-b343a533fc1d" width="300"/> | <img src="https://github.com/user-attachments/assets/ede19fd2-e9ce-4934-8604-cc33c6c7d20a" width="300"/> | <img src="https://github.com/user-attachments/assets/596e0c9e-26f4-4d0a-845e-314a1e64ab0c" width="300"/> |

| Comments | Profile | Edit Profile |
|-----------|----------|---------------|
| <img src="https://github.com/user-attachments/assets/c3c73997-eb7a-4160-a497-ecfe8875c538" width="300"/> | <img src="https://github.com/user-attachments/assets/ca549db8-1b69-4a62-b2a8-322ab55763cb" width="300"/> | <img src="https://github.com/user-attachments/assets/190c5fcf-4396-4389-ad13-cc7189477bd7" width="300"/> |

---

## ⚙️ How to Run

1. Clone this repository:
2. Open the project in IntelliJ IDEA or VS Code.
3. Make sure you have JavaFX SDK installed.
4. Add the following VM options to run JavaFX:
```
--module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
```
5. Run the Main.java file
   
## 📚 Technologies Used

- Java 17+
- JavaFX
- MVC Architecture
- Object-Oriented Programming (OOP)
- File I/O (java.io)
- Custom Exception Handling

## ✅ Conclusion

OpenFeed successfully demonstrates a robust application of OOP principles and MVC architecture.
It integrates real-world features — login, posts, likes, comments, and data persistence — to form a complete and maintainable software system.

📘 Course: CSE215 – Programming Language II
🏫 North South University
👨‍🏫 Instructor: Professor Dr. Md. Rashedur Rahman
   ```bash
   git clone https://github.com/yourusername/OpenFeed.git
