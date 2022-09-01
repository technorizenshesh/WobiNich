package com.my.wobinichapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPostBluVoilet {

    @SerializedName("bluedata")
    @Expose
    private List<Bluedatum> bluedata = null;
    @SerializedName("voiletdata")
    @Expose
    private List<Voiletdatum> voiletdata = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Bluedatum> getBluedata() {
        return bluedata;
    }

    public void setBluedata(List<Bluedatum> bluedata) {
        this.bluedata = bluedata;
    }

    public List<Voiletdatum> getVoiletdata() {
        return voiletdata;
    }

    public void setVoiletdata(List<Voiletdatum> voiletdata) {
        this.voiletdata = voiletdata;
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

    public class Voiletdatum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
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
        private String group_id;
        @SerializedName("user_name")
        @Expose
        private String user_name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("group_name")
        @Expose
        private String groupName;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

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

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public class Bluedatum {

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
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("date_time")
        @Expose
        private String dateTime;

        @SerializedName("user_name")
        @Expose
        private String userName;





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


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}

