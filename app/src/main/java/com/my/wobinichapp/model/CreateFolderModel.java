package com.my.wobinichapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateFolderModel {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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

        @SerializedName("folder_image")
        @Expose
        private String folderImage;

        public String getFolderImage() {
            return folderImage;
        }

        public void setFolderImage(String folderImage) {
            this.folderImage = folderImage;
        }

}



}