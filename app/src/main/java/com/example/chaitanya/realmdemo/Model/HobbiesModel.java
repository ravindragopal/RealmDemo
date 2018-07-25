package com.example.chaitanya.realmdemo.Model;

import io.realm.RealmObject;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 25/7/18,4:04 PM.
 * For : ISS 24/7, Pune.
 */
public class HobbiesModel extends RealmObject{

    private String reading;
    private String writing;
    private String drawing;

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    public String getDrawing() {
        return drawing;
    }

    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }

    @Override
    public String toString() {
        return "HobbiesModel{" +
                "reading='" + reading + '\'' +
                ", writing='" + writing + '\'' +
                ", drawing='" + drawing + '\'' +
                '}';
    }
}
