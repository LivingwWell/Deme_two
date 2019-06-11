package com.example.lee.deme_two.data;

public class Live {
    private String title;
    private int imgurl;
    private String liveurl;

    public Live(String title, int imgurl, String liveurl) {
        this.title = title;
        this.imgurl = imgurl;
        this.liveurl = liveurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    public String getLiveurl() {
        return liveurl;
    }

    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }
}
