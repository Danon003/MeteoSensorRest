package ru.danon.spring.MeteoSensorRest.dto;

import jakarta.validation.constraints.NotEmpty;
import ru.danon.spring.MeteoSensorRest.models.Measure;

import java.util.List;

public class SensorDTO {

    @NotEmpty(message = "Название сенсора не должно быть пустым!")
    private String sensorName;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
    public SensorDTO() {}
    public SensorDTO(String sensorName) {
        this.sensorName = sensorName;
    }
}
