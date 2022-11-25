package com.example.loginexample;

public class UploadImage {
    private String imagename,imageuri;

    public UploadImage(){}

    public UploadImage(String imagename, String imageuri) {
        this.imagename = imagename;
        this.imageuri = imageuri;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
