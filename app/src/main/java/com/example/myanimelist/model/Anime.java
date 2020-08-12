package com.example.myanimelist.model;

public class Anime {
    private String title, episode, imageUrl, id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Anime(String title, String episode, String imageUrl, String id) {
        this.title = title;
        this.episode = episode;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public Anime(String id, String title, String imageUrl) {
        this.title = title;
        this.id = id;
        this.imageUrl = imageUrl;
    }
}
