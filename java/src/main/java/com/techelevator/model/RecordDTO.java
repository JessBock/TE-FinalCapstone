package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class RecordDTO {
    private List<String> genre = new ArrayList<>();
    private String title = "";
    @JsonProperty("artists")
    private List<Artists> artists;
    @JsonProperty("tracklist")
    private List<Tracks> tracklist = new ArrayList<>();
    private Date year;
    private String coverImg = "";

    public Date getYear(){ return year = Date.valueOf(String.valueOf(year.toLocalDate()));}

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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
}
