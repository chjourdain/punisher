package com.punisher.balancer.controller;

import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.service.FireService;
import com.punisher.balancer.service.ProgrammerService;
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
    private ProgrammerService programmerService;

    @Autowired
    private FireService fireService;

    /**
     * trigger the shot
     *
     * @param name name of the programmer who fucked up
     * @return http ok
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> fire(@PathVariable String name) {

        //retrieve programmer
        Programmer programmer = programmerService.get(name);
        if (programmer != null) {
            fireService.fire(programmer);
            return new ResponseEntity<>("Order to fire sended!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Programmer does not exist.", HttpStatus.BAD_REQUEST);
    }
}
