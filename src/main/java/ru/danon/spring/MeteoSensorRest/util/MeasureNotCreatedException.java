package ru.danon.spring.MeteoSensorRest.util;

public class MeasureNotCreatedException extends RuntimeException {
    public MeasureNotCreatedException(String message) {
        super(message);
    }
}
