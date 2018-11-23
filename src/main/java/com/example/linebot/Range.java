package com.example.linebot;

//情報をとるセンサーを超音波センサーに切り替える。
public class Range {
    private float range_cm;

    public float getRange_cm() {
        return range_cm;
    }

    public void getRange_cm(float range_cm){
        this.range_cm=range_cm;
    }
}