package ru.danon.spring.MeteoSensorRest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measure")
public class Measure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value", precision = 4, scale = 1, nullable = false)
    @DecimalMin(value = "-100.0", message = "температура должна быть больше, чем -100.0")
    @DecimalMax(value = "100.0", message = "температура должна быть меньше, чем 100.0")
    @NotNull
    private BigDecimal value;

    @Column(name = "raining")
    @NotNull
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    public Measure() {}

    public Measure(BigDecimal value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
