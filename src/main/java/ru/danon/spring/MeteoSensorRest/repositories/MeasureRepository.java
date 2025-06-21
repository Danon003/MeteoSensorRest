package ru.danon.spring.MeteoSensorRest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danon.spring.MeteoSensorRest.models.Measure;

import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Integer> {
    int countByRainingTrue();
    List<Measure> findBySensorId(int sensorId);
}
