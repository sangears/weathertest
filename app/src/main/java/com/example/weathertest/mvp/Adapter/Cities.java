package com.example.weathertest.mvp.Adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Cities {

    private String city;
    private String logo;
    private String temp;
    private String temp2;
    private String desc;
    private String pressure;
    private String humidity;
    private String wind;

    private Integer id;

    public Cities(String city, String logo, String temp, String temp2, String desc, String pressure, String humidity, String wind,Integer id) {
        this.id = id;
        this.city = city;
        this.logo = logo;
        this.temp = temp;
        this.temp2 = temp2;
        this.desc = desc;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;

    }

    public Cities() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
