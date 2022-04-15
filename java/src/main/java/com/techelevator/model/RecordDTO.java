package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecordDTO {
    private List<String> genre = new ArrayList<>();
    private String title = "";
    private String year;
    private String coverImg = "";
    @JsonProperty("artists")
    private List<Artists> artists = new ArrayList<>();
    @JsonProperty("tracklist")
    private List<Tracks> tracklist = new ArrayList<>();
    private Long recordId;

    public String getYear(){
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public RecordDTO () {};

    public RecordDTO(Long recordId, String title) {
        this.recordId = recordId;
        this.title = title;
    }
}
