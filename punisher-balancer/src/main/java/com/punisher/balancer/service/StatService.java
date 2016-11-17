package com.punisher.balancer.service;


import com.punisher.balancer.model.Stat;

import java.util.Map;

/**
 * Created by guillaumenostrenoff on 05/05/2016.
 */
public interface StatService {

    Map<String, Stat> getStats(int size);
}
