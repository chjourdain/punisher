package com.punisher.balancer.dto.validation;

import com.punisher.balancer.service.MissileLauncherService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by charles on 13/05/16.
 */
public class RoomExistValidator implements ConstraintValidator<RoomExist, Integer> {

    @Autowired
    private MissileLauncherService service;
    @Override
    public void initialize(RoomExist arg0) {
    }

    @Override
    public boolean isValid(Integer room, ConstraintValidatorContext arg1) {
        System.out.println(room);
        System.out.println(service.get(room));
        if (service.get(room) != null) {return true;}
        return false;
    }
}