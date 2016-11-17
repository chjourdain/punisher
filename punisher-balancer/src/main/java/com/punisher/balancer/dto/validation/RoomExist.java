package com.punisher.balancer.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by charles on 13/05/16.
 */
@Documented
@Constraint(validatedBy = RoomExistValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomExist {
        String message() default "Room does not exist";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

