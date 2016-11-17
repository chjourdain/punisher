package com.punisher.balancer.controller;

import com.punisher.balancer.model.Stat;
import com.punisher.balancer.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by guillaumenostrenoff on 05/05/2016.
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatService statService;

    @RequestMapping(value = "/{size}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Stat>> getStats(@PathVariable int size) {
        return new ResponseEntity<>(statService.getStats(size), HttpStatus.OK);
    }
}
