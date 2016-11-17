package com.punisher.balancer.model;

/**
 * Created by guillaumenostrenoff on 23/03/2016.
 */
public class Programmer {

    public String name;
    public Coords coords;
    public MissileLauncher launcher;
    public int stats;

    /**
     * Default constructor used by YAMLBEANS.
     */
    public Programmer() {}
    
    public Programmer(String name, Coords coords, MissileLauncher launcher) {
        this.name = name;
        this.coords = coords;
        this.launcher = launcher;
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

    public MissileLauncher getLauncher() {
        return launcher;
    }

    public void setLauncher(MissileLauncher launcher) {
        this.launcher = launcher;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Programmer that = (Programmer) o;

        if (stats != that.stats) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (coords != null ? !coords.equals(that.coords) : that.coords != null) return false;
        return launcher != null ? launcher.equals(that.launcher) : that.launcher == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        result = 31 * result + (launcher != null ? launcher.hashCode() : 0);
        result = 31 * result + stats;
        return result;
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
