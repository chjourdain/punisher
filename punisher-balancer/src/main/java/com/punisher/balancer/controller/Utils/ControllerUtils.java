package com.punisher.balancer.controller.Utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created by charles on 13/05/16.
 */
public class ControllerUtils {

    public static String formatBindResultError(BindingResult bindingResult) {
        String error = "";
        for (ObjectError err : bindingResult.getAllErrors()) {
            error += err.getDefaultMessage() + "  \n";
        }
        return error;
    }
}
