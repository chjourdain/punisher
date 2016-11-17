package com.punisher.balancer.model;

/**
 * Created by guillaumenostrenoff on 05/05/2016.
 */
public class Stat {

    private String programmerName;
    private int shot;

    public Stat(String programmerName, int shot) {
        this.programmerName = programmerName;
        this.shot = shot;

    }

    public String getProgrammerName() {
        return programmerName;
    }

    public void setProgrammerName(String programmerName) {
        this.programmerName = programmerName;
    }

    public int getShot() {
        return shot;
    }

    public void setShot(int shot) {
        this.shot = shot;
    }
}
