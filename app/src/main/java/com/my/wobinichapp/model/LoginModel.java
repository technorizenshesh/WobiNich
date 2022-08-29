package com.my.wobinichapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {

    private Result result;

    private String message;

    private String status;

    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }

    public class Result implements Serializable
    {
        private String id;

        private String name;

        private String email;

        private String image;

        private String password;

        private String register_id;

        private String contact;

        private String age;

        private String gender;

        private String lat;

        private String otp;

        private String lon;

        private String type;

        private String status;

        private String date_time;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setImage(String image){
            this.image = image;
        }
        public String getImage(){
            return this.image;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public String getPassword(){
            return this.password;
        }
        public void setRegister_id(String register_id){
            this.register_id = register_id;
        }
        public String getRegister_id(){
            return this.register_id;
        }
        public void setContact(String contact){
            this.contact = contact;
        }
        public String getContact(){
            return this.contact;
        }
        public void setAge(String age){
            this.age = age;
        }
        public String getAge(){
            return this.age;
        }
        public void setGender(String gender){
            this.gender = gender;
        }
        public String getGender(){
            return this.gender;
        }
        public void setLat(String lat){
            this.lat = lat;
        }
        public String getLat(){
            return this.lat;
        }
        public void setOtp(String otp){
            this.otp = otp;
        }
        public String getOtp(){
            return this.otp;
        }
        public void setLon(String lon){
            this.lon = lon;
        }
        public String getLon(){
            return this.lon;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
        }
        public void setDate_time(String date_time){
            this.date_time = date_time;
        }
        public String getDate_time(){
            return this.date_time;
        }
    }
}

