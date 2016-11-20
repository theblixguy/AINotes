package com.ssrij.ainotes;

import java.util.Date;

import io.realm.RealmObject;

public class TextNoteItem extends RealmObject {

    private String uniqueID;
    private String title;
    private String subTitle;
    private String noteText;
    private Date creationTime;
    private String creationLoc;
    private Double creationLocLat;
    private Double creationLocLon;

    public Double getCreationLocLat() {
        return creationLocLat;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setCreationLocLat(Double creationLocLat) {
        this.creationLocLat = creationLocLat;
    }

    public Double getCreationLocLon() {
        return creationLocLon;
    }

    public void setCreationLocLon(Double creationLocLon) {
        this.creationLocLon = creationLocLon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationLoc() {
        return creationLoc;
    }

    public void setCreationLoc(String creationLoc) {
        this.creationLoc = creationLoc;
    }
}