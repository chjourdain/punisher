package com.punisher.balancer.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by charles on 13/04/16.
 */
public class Coords {
    @Min(value = 0, message = "X cood must be > 0 °")
    @Max(value = 345, message = "X cood must be < 345 °")
    public int x;
    @Min(value = 0, message = "Y cood must be > 0 °")
    @Max(value = 345, message = "Y cood must be < 45°")
    public int y;

    public Coords() {
    }

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coords coords = (Coords) o;

        if (x != coords.x) return false;
        return y == coords.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coords{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
