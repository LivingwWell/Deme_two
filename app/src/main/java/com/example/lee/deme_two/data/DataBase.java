package com.example.lee.deme_two.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBase {
    @Id(autoincrement = true)
    private Long id;

    @Property
    private String place;//会议地点
    private String content;//会议内容
    private String theme;//会议主题
    private String date;//会议时间
    @Generated(hash = 1042070923)
    public DataBase(Long id, String place, String content, String theme,
            String date) {
        this.id = id;
        this.place = place;
        this.content = content;
        this.theme = theme;
        this.date = date;
    }
    @Generated(hash = 532938043)
    public DataBase() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPlace() {
        return this.place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTheme() {
        return this.theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
  


    

}
