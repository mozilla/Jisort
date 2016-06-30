package com.mozilla.hackathon.kiboko.models;

public class IconTopic {

    private String description;
    private String title;
    private String tag;
    private int image;

    public IconTopic(String tag, String title, String description, int image) {
        this.description = description;
        this.tag = tag;
        this.image = image;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
