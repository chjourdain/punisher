package com.punisher.ui.mapper;

import com.punisher.ui.model.Programmer;
import com.punisher.ui.model.ProgrammerReadOnly;

/**
 * Created by guillaumenostrenoff on 11/05/2016.
 */
public class ProgrammerMapper {

    public static Programmer toProgrammer (ProgrammerReadOnly programmerReadOnly) {
        return new Programmer(programmerReadOnly.getName(), programmerReadOnly.getCoords(), programmerReadOnly.getLauncher().getId());
    }

}
