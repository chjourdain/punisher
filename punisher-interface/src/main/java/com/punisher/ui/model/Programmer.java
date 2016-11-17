package com.punisher.ui.model;

/**
 * Created by guillaumenostrenoff on 23/03/2016.
 */
public class Programmer {

    public String name;
    public Coords coords;
    public int idLauncher;

    /**
     * Default constructor used by YAMLBEANS.
     */
    public Programmer() {}
    
    public Programmer(String name, Coords coords, int idLauncher) {
        this.name = name;
        this.coords = coords;
        this.idLauncher = idLauncher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }



}
