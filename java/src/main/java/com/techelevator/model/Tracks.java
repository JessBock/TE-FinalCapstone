package com.techelevator.model;

import java.time.Duration;

public class Tracks {
    private String duration;
    private String position;
    private String title;

    public Tracks() {
        super();
    }

    public Tracks(String duration, String position, String title) {
        this.duration = duration;
        this.position = position;
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
