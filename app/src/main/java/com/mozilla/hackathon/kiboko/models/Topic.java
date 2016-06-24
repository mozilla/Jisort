package com.mozilla.hackathon.kiboko.models;

public class Topic {

    private String name;
    private String tag;
    private int image;

    public Topic(String tag, String name, int image) {
        this.name = name;
        this.tag = tag;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
