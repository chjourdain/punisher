package com.punisher.balancer.controller;

import com.punisher.balancer.dto.ProgrammerDto;
import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.service.MissileLauncherService;
import com.punisher.balancer.service.ProgrammerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static com.punisher.balancer.controller.Utils.ControllerUtils.formatBindResultError;

@RestController
@RequestMapping("/api/programmer")
public class ProgrammerController {

    @Autowired
    private ProgrammerService programmerService;
    @Autowired
    private MissileLauncherService missileLauncherService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Programmer>> get() {
        return new ResponseEntity<List<Programmer>>(programmerService.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Programmer> get(@PathVariable String name) {
        return new ResponseEntity<Programmer>(programmerService.get(name), HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String name) {
        Programmer programmer = programmerService.get(name);
        if (programmer != null) {
            programmerService.delete(programmer);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody @Valid ProgrammerDto programmer, BindingResult bindingResult) {
        // validate
        if (programmerService.get(programmer.getName()) != null) {
            return new ResponseEntity<>("Name already Taken", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(formatBindResultError(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Programmer p = new Programmer(programmer.getName(), programmer.getCoords(), missileLauncherService.get(programmer.getIdLauncher()));
        programmerService.create(p);
        return new ResponseEntity<String>("Programmer added"+p.toString(),HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable String name, @RequestBody @Valid ProgrammerDto programmer, BindingResult bindingResult) {
        // validate
        if (programmerService.get(programmer.getName()) == null) {
            return new ResponseEntity<>("Programmer does not exist", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(formatBindResultError(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Programmer p = new Programmer(programmer.getName(), programmer.getCoords(), missileLauncherService.get(programmer.getIdLauncher()));
        programmerService.update(p);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}