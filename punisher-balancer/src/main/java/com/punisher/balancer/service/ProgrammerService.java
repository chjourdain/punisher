package com.punisher.balancer.service;

import com.punisher.balancer.model.Programmer;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaumenostrenoff on 23/03/2016.
 */
public interface ProgrammerService {

    Programmer get(String name);
    List<Programmer> get();
    void create(Programmer programmer);
    void delete(Programmer programmer);
    void update(Programmer programmer);

}
