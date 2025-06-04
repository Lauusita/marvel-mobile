package com.laura.marvel.classes;

public class Characters {
    private int id;
    private String name;
    private String desc;
    private String img;
    private int comics;
    private int series;
    private int stories;

    public Characters(int comics, String desc, int id, String img, String name, int series, int stories) {
        this.comics = comics;
        this.desc = desc;
        this.id = id;
        this.img = img;
        this.name = name;
        this.series = series;
        this.stories = stories;
    }

    public int getComics() {
        return comics;
    }

    public void setComics(int comics) {
        this.comics = comics;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getStories() {
        return stories;
    }

    public void setStories(int stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "Character{" +
                "comics=" + comics +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                ", series=" + series +
                ", stories=" + stories +
                '}';
    }


}
