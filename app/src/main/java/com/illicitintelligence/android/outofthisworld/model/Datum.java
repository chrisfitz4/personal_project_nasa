
package com.illicitintelligence.android.outofthisworld.model;

import androidx.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("photographer")
    @Expose
    private String photographer;
    @SerializedName("nasa_id")
    @Expose
    private String nasaId;
    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("secondary_creator")
    @Expose
    private String secondaryCreator;
    @SerializedName("description_508")
    @Expose
    private String description508;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondaryCreator() {
        return secondaryCreator;
    }

    public void setSecondaryCreator(String secondaryCreator) {
        this.secondaryCreator = secondaryCreator;
    }

    public String getDescription508() {
        return description508;
    }

    public void setDescription508(String description508) {
        this.description508 = description508;
    }


    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
