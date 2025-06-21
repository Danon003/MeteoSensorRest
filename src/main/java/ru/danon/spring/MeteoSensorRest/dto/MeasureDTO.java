package ru.danon.spring.MeteoSensorRest.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import ru.danon.spring.MeteoSensorRest.models.Sensor;

import java.math.BigDecimal;

public class MeasureDTO {

    @DecimalMin(value = "-100.0", message = "температура должна быть больше, чем -100.0")
    @DecimalMax(value = "100.0", message = "температура должна быть меньше, чем 100.0")
    private BigDecimal value;

    private boolean raining;

    private SensorDTO sensor;

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
