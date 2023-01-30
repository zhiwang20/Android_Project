package com.example.eulerity_hackathon.Models;
/*
import android.graphics.Bitmap;

public class Image{
    private Bitmap bitmap;
    private String url;

    public Image(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

 */

public class Image {

    private String url;


     // @param eventUrl = “url” attribute pointing to a target image.

    public Image(String url){
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
