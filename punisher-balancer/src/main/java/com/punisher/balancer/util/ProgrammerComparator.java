package com.punisher.balancer.util;

import com.punisher.balancer.model.Programmer;

import java.util.Comparator;

/**
 * Created by guillaumenostrenoff on 05/05/2016.
 */
public class ProgrammerComparator implements Comparator<Programmer> {
    @Override
    public int compare(Programmer prog1, Programmer prog2) {
        return prog1.getStats() < prog2.getStats() ? -1 : (prog1.getStats() == prog2.getStats() ? 0 : 1);
    }
}
