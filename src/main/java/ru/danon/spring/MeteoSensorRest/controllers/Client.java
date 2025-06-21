package ru.danon.spring.MeteoSensorRest.controllers;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danon.spring.MeteoSensorRest.dto.MeasureDTO;
import ru.danon.spring.MeteoSensorRest.dto.SensorDTO;
import ru.danon.spring.MeteoSensorRest.models.Measure;
import ru.danon.spring.MeteoSensorRest.models.Sensor;
import ru.danon.spring.MeteoSensorRest.service.MeasureService;
import ru.danon.spring.MeteoSensorRest.service.SensorService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/client")
public class Client {
    private final MeasureService measureService;
    private final ModelMapper modelMapper;
    @Autowired
    public Client(MeasureService measureService, ModelMapper modelMapper) {
        this.measureService = measureService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add1000Metrics")
    public ResponseEntity<HttpStatus> add1000Metrics(@RequestBody MeasureDTO measureDTO) {
        List<Measure> measures = new ArrayList<>();
        Random random = new Random();
        Sensor sensor = new Sensor("sensor-4");

        for (int i = 0; i < 999; i++) {
            Measure measure = new Measure();

            measure.setValue(BigDecimal.valueOf(-100.0 + 200.0 * random.nextDouble())
                    .setScale(2, RoundingMode.HALF_UP));
            measure.setRaining(random.nextBoolean());
            measure.setSensor(sensor);
            measure.setCreated_at(LocalDateTime.now());
            measures.add(measure);

        }
        measureService.saveAll(measures);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/graph", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] graph() throws IOException {
        List<Measure> measures = measureService.findAll();

        List<Double> xData = IntStream.range(0, measures.size())
                .asDoubleStream()
                .boxed()
                .toList();

        List<Double> yData = measures.stream()
                .map(m -> m.getValue().doubleValue())
                .toList();

        XYChart chart = QuickChart.getChart(
                "Температурные измерения",
                "Номер измерения",
                "Температура (°C)",
                "Температура",
                xData,
                yData
        );

        chart.getStyler().setYAxisMin(-100.0);
        chart.getStyler().setYAxisMax(100.0);


        return BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
    }

}
