package com.punisher.balancer.controller;

import com.punisher.balancer.model.MissileLauncher;
import com.punisher.balancer.service.MissileLauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.punisher.balancer.controller.Utils.ControllerUtils.formatBindResultError;

@RestController
@RequestMapping("/api/launcher")
public class LauncherController {

    @Autowired
    private MissileLauncherService launcherService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MissileLauncher>> get() {
        return new ResponseEntity<List<MissileLauncher>>(launcherService.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MissileLauncher> get(@PathVariable int id) {
        return new ResponseEntity<MissileLauncher>(launcherService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id) {
        MissileLauncher programmer = launcherService.get(id);
        if (programmer != null) {
            launcherService.delete(programmer);
            return new ResponseEntity<String>("deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Launcher does not exist.", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody @Valid MissileLauncher launcher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(formatBindResultError(bindingResult), HttpStatus.BAD_REQUEST);
        }
        launcherService.create(launcher);
        return new ResponseEntity<String>("Created.", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody @Valid MissileLauncher launcher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(formatBindResultError(bindingResult), HttpStatus.BAD_REQUEST);
        }
        if (launcherService.get(launcher.id) == null) {
            return new ResponseEntity<String>("Launcher does not exist.", HttpStatus.BAD_REQUEST);
        }
        launcherService.update(launcher);
        return new ResponseEntity<String>("updated.", HttpStatus.OK);
    }
}