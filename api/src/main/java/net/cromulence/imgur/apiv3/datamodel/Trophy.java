package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

public class Trophy {

    // The ID of the trophy, this is unique to each trophy
    private int id;

    // The name of the trophy
    private String name;

    // Can be thought of as the ID of a trophy type
    @SerializedName("name_clean")
    private String nameClean;

    // A description of the trophy and how it was earned.
    private String description;

    // The ID of the image or the ID of the comment where the trophy was earned
    private String data;

    // A link to where the trophy was earned
    @SerializedName("data_link")
    private String dataLink;

    // Date the trophy was earned, epoch time
    private long datetime;

    // URL of the image representing the trophy
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameClean() {
        return nameClean;
    }

    public void setNameClean(String nameClean) {
        this.nameClean = nameClean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataLink() {
        return dataLink;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
