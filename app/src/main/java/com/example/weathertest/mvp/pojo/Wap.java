package com.example.weathertest.mvp.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Wap {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("calctime")
    @Expose
    private String calctime;
    @SerializedName("cnt")
    @Expose
    private String cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<com.example.weathertest.mvp.pojo.List> list = null;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCalctime() {
        return calctime;
    }

    public void setCalctime(String calctime) {
        this.calctime = calctime;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.example.weathertest.mvp.pojo.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.weathertest.mvp.pojo.List> list) {
        this.list = list;
    }

}