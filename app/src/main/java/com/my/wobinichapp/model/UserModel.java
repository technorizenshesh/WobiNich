package com.my.wobinichapp.model;

public class UserModel {

    String name;

    private boolean isSelected = false;

    public UserModel(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
