package com.example.project.models;

import java.util.ArrayList;

public interface Searchable {
    ArrayList<Post> search(String keyword);
}