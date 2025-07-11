package ru.danon.spring.MeteoSensorRest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sensor_name", unique = true)
    @Size(min = 2, max = 100, message = "Имя сенсора должно быть не меньше 2 и не больше 100 символов")
    @NotEmpty(message = "Название сенсора не должно быть пустым!")
    private String sensorName;

    @OneToMany(mappedBy = "sensor")
    List<Measure> measures;

    public Sensor() {}

    public Sensor(String sensorName) {
        this.sensorName = sensorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }


}
