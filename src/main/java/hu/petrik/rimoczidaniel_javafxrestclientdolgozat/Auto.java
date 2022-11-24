package hu.petrik.rimoczidaniel_javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;

public class Auto {
    private int id;
    @Expose
    private String gyarto;
    @Expose
    private String model;
    @Expose
    private int teljesitmeny;
    @Expose
    private boolean hybrid;

    public Auto(int id, String gyarto, String model, int teljesitmeny, boolean hybrid) {
        this.id = id;
        this.gyarto = gyarto;
        this.model = model;
        this.teljesitmeny = teljesitmeny;
        this.hybrid = hybrid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGyarto() {
        return gyarto;
    }

    public void setGyarto(String gyarto) {
        this.gyarto = gyarto;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTeljesitmeny() {
        return teljesitmeny;
    }

    public void setTeljesitmeny(int teljesitmeny) {
        this.teljesitmeny = teljesitmeny;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public void setHybrid(boolean hybrid) {
        this.hybrid = hybrid;
    }
}
