package com.punisher.balancer.dto;

import com.punisher.balancer.dto.validation.RoomExist;
import com.punisher.balancer.model.Coords;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * Created by guillaumenostrenoff on 11/05/2016.
 */
public class ProgrammerDto {

    @Length(min = 3)
    public String name;
    @Valid
    public Coords coords;
    @RoomExist
    public int idLauncher;

    /**
     * Default constructor used by YAMLBEANS.
     */
    public ProgrammerDto() {}

    public ProgrammerDto(String name, Coords coords, int idLauncher) {
        this.name = name;
        this.coords = coords;
        this.idLauncher = idLauncher;
    }
    public ProgrammerDto(String name) {
        this.name = name;
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

    public int getIdLauncher() {
        return idLauncher;
    }

    public void setIdLauncher(int idLauncher) {
        this.idLauncher = idLauncher;
    }
}
