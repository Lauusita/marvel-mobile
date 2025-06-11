package com.laura.marvel.classes;

public class Comics {
    private int id;
    private String title;

    public Comics(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    @Override
    public String toString() {
        // Esto es lo que se mostrará en el Spinner
        return title;
    }
}

