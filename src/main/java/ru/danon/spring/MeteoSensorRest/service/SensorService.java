package ru.danon.spring.MeteoSensorRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danon.spring.MeteoSensorRest.models.Sensor;
import ru.danon.spring.MeteoSensorRest.repositories.SensorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    public Sensor findOne(int id) {
        return sensorRepository.findById(id).orElse(null);
    }
    public Sensor findByName(String name) {return sensorRepository.findBySensorName(name);}

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
