package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecordDTO {
    private List<String> genre;
    private String title;
    @JsonProperty("artists")
    private List<Artists> artists;
    @JsonProperty("tracklist")
    private List<Tracks> tracklist;
    private String year;
    private String thumb;

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tracks> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Tracks> tracklist) {
        this.tracklist = tracklist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /*
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

     */

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
}
