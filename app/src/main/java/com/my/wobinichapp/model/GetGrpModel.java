package com.my.wobinichapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetGrpModel {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("post_count")
    @Expose
    private int postCount;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("group_name")
        @Expose
        private String groupName;
        @SerializedName("group_image")
        @Expose
        private String groupImage;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("group_admin")
        @Expose
        private String groupAdmin;
        @SerializedName("chat_status")
        @Expose
        private String chatStatus;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("count")
        @Expose
        private int count;

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

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
            this.groupImage = groupImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroupAdmin() {
            return groupAdmin;
        }

        public void setGroupAdmin(String groupAdmin) {
            this.groupAdmin = groupAdmin;
        }

        public String getChatStatus() {
            return chatStatus;
        }

        public void setChatStatus(String chatStatus) {
            this.chatStatus = chatStatus;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

    }
}

