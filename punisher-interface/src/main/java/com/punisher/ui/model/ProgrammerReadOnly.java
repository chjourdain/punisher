package com.punisher.ui.model;

/**
 * Created by guillaumenostrenoff on 11/05/2016.
 */
public class ProgrammerReadOnly {


    public String name;
    public Coords coords;
    public MissileLauncher launcher;
    public int stats;

    /**
     * Default constructor used by YAMLBEANS.
     */
    public ProgrammerReadOnly() {}

    public ProgrammerReadOnly(String name, Coords coords, MissileLauncher launcher) {
        this.name = name;
        this.coords = coords;
        this.launcher = launcher;
    }

    public String getName() {
        return name;
    }

    public Coords getCoords() {
        return coords;
    }

    public MissileLauncher getLauncher() {
        return launcher;
    }

    public int getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", coords=" + coords +
                ", launcher=" + launcher +
                ", stats=" + stats +
                '}';
    }

}
