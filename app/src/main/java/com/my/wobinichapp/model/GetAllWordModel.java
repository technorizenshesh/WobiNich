package com.my.wobinichapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllWordModel {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lon")
        @Expose
        private String lon;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("maps")
        @Expose
        private String maps;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("landscape")
        @Expose
        private String landscape;
        @SerializedName("meal")
        @Expose
        private String meal;
        @SerializedName("places")
        @Expose
        private String places;
        @SerializedName("shop")
        @Expose
        private String shop;
        @SerializedName("all_world")
        @Expose
        private String allWorld;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("date_time")
        @Expose
        private String dateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getMaps() {
            return maps;
        }

        public void setMaps(String maps) {
            this.maps = maps;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getLandscape() {
            return landscape;
        }

        public void setLandscape(String landscape) {
            this.landscape = landscape;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getPlaces() {
            return places;
        }

        public void setPlaces(String places) {
            this.places = places;
        }

        public String getShop() {
            return shop;
        }

        public void setShop(String shop) {
            this.shop = shop;
        }

        public String getAllWorld() {
            return allWorld;
        }

        public void setAllWorld(String allWorld) {
            this.allWorld = allWorld;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

    }

}
