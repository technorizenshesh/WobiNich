package com.my.wobinichapp.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeModel {

    @SerializedName("result")
    @Expose
    private ArrayList<Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
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
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_answer")
        @Expose
        private ArrayList<UserAnswer> userAnswer = null;
        @SerializedName("time_ago")
        @Expose
        private String timeAgo;

        @SerializedName("post_id")
        @Expose
        private String postId;


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

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public ArrayList<UserAnswer> getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(ArrayList<UserAnswer> userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getTimeAgo() {
            return timeAgo;
        }

        public void setTimeAgo(String timeAgo) {
            this.timeAgo = timeAgo;
        }

        public class UserAnswer {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("post_id")
            @Expose
            private String postId;
            @SerializedName("answer_user")
            @Expose
            private String answerUser;
            @SerializedName("post_user")
            @Expose
            private String postUser;
            @SerializedName("group_id")
            @Expose
            private String groupId;
            @SerializedName("answer")
            @Expose
            private String answer;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("userimage")
            @Expose
            private String userimage;
            @SerializedName("username")
            @Expose
            private String username;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPostId() {
                return postId;
            }

            public void setPostId(String postId) {
                this.postId = postId;
            }

            public String getAnswerUser() {
                return answerUser;
            }

            public void setAnswerUser(String answerUser) {
                this.answerUser = answerUser;
            }

            public String getPostUser() {
                return postUser;
            }

            public void setPostUser(String postUser) {
                this.postUser = postUser;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getUserimage() {
                return userimage;
            }

            public void setUserimage(String userimage) {
                this.userimage = userimage;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

        }


    }



}


