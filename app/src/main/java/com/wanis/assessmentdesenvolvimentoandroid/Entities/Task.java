package com.wanis.assessmentdesenvolvimentoandroid.Entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by munirwanis on 17/12/17.
 */

public class Task {

    @SerializedName("id")
    public String id ;
    @SerializedName("description")
    public String description;
    @SerializedName("imagem")
    public String image;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return getDescription();
    }
}
