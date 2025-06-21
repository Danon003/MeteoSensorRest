package ru.danon.spring.MeteoSensorRest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danon.spring.MeteoSensorRest.dto.MeasureDTO;
import ru.danon.spring.MeteoSensorRest.dto.MeasuresResponse;
import ru.danon.spring.MeteoSensorRest.models.Measure;
import ru.danon.spring.MeteoSensorRest.service.MeasureService;
import ru.danon.spring.MeteoSensorRest.util.MeasureErrorResponse;
import ru.danon.spring.MeteoSensorRest.util.MeasureNotCreatedException;
import java.util.List;
import java.util.stream.Collectors;
import static ru.danon.spring.MeteoSensorRest.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasureController {
    private final MeasureService measureService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasureController(MeasureService measureService, ModelMapper modelMapper) {
        this.measureService = measureService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public MeasuresResponse getAllMeasures() {
        return new MeasuresResponse(measureService.findAll().stream()
                .map(this::convertToMeasureDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Integer getRainyDaysCount() {
        return measureService.rainyDayCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasureDTO measureDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        measureService.save(convertToMeasure(measureDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private MeasureDTO convertToMeasureDTO(Measure measure) {
        return modelMapper.map(measure, MeasureDTO.class);
    }
    private Measure convertToMeasure(MeasureDTO measureDTO) {
        return modelMapper.map(measureDTO, Measure.class);
    }

    @ExceptionHandler
    public ResponseEntity<MeasureErrorResponse> handleException(MeasureNotCreatedException e) {
        MeasureErrorResponse response = new MeasureErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
