package ru.danon.spring.MeteoSensorRest.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        throw new MeasureNotCreatedException(errorMessage.toString());
    }
}
