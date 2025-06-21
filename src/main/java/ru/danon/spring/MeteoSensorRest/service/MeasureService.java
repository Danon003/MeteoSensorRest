package ru.danon.spring.MeteoSensorRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danon.spring.MeteoSensorRest.models.Measure;
import ru.danon.spring.MeteoSensorRest.models.Sensor;
import ru.danon.spring.MeteoSensorRest.repositories.MeasureRepository;
import ru.danon.spring.MeteoSensorRest.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class MeasureService {

    private final MeasureRepository measureRepository;
    private final SensorRepository sensorRepository;
    @Autowired
    public MeasureService(MeasureRepository measureRepository, SensorRepository sensorRepository) {
        this.measureRepository = measureRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measure> findAll() {
        return measureRepository.findAll();
    }
    public Measure findOne(int id) {
        return measureRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Measure measure) {
        Sensor sensor = measure.getSensor();


        if (sensor != null && sensor.getId() == null) {
            Sensor existingSensor = sensorRepository.findBySensorName(sensor.getSensorName());
            if (existingSensor != null) {
                measure.setSensor(existingSensor);
            } else {
                sensorRepository.save(sensor);
            }
        }
        enrichMeasure(measure);
        measureRepository.save(measure);
    }

    public int rainyDayCount(){
        return measureRepository.countByRainingTrue();
    }
    @Transactional
    public void saveAll(List<Measure> measures){
        Sensor sensor = measures.get(0).getSensor();
        sensorRepository.save(sensor);
        measureRepository.saveAll(measures);
    }
    private void enrichMeasure(Measure measure){
        measure.setCreated_at(LocalDateTime.now());
    }
}
