package com.laura.marvel.classes;

public class Comics {
    private String name;
    private String img;

    public Comics(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Comics{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
