package com.laura.marvel.classes;

public class Comics {
    private int id;
    private String title;
    private String img;

    public Comics(int id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    @Override
    public String toString() {
        // Esto es lo que se mostrará en el Spinner
        return title;
    }
}

