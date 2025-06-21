package ru.danon.spring.MeteoSensorRest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danon.spring.MeteoSensorRest.dto.SensorDTO;
import ru.danon.spring.MeteoSensorRest.models.Sensor;
import ru.danon.spring.MeteoSensorRest.service.SensorService;
import ru.danon.spring.MeteoSensorRest.util.MeasureErrorResponse;
import ru.danon.spring.MeteoSensorRest.util.MeasureNotCreatedException;


import java.util.List;
import java.util.stream.Collectors;

import static ru.danon.spring.MeteoSensorRest.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(final SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());

    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasureErrorResponse> handleException(MeasureNotCreatedException e) {
        MeasureErrorResponse response = new MeasureErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
    private Sensor convertToSensor(SensorDTO sensorDTO) {return modelMapper.map(sensorDTO, Sensor.class);}
}
