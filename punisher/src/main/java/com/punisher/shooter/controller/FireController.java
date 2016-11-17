package com.punisher.shooter.controller;

import com.punisher.shooter.service.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guillaumenostrenoff on 25/03/2016.
 */
@RestController
@RequestMapping("/api/fire")
public class FireController {

    @Autowired
    private FireService fireService;

    /**
     * trigger the shot
     *
     * @return http ok
     */
    @RequestMapping(value = "/{x}/{y}", method = RequestMethod.GET)
    public ResponseEntity fire(@PathVariable int x, @PathVariable int y) {
        if (x < 0 || x > 345 || y < 0 || y > 45) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
            fireService.fire(x, y);
        return new ResponseEntity(HttpStatus.OK);
    }
}
