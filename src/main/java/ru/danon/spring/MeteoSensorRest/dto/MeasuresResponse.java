package ru.danon.spring.MeteoSensorRest.dto;

import java.util.List;

public class MeasuresResponse {
    private List<MeasureDTO> measures;

    public MeasuresResponse(List<MeasureDTO> measures) {
        this.measures = measures;
    }
    public List<MeasureDTO> getMeasures() {
        return measures;
    }
    public void setMeasures(List<MeasureDTO> measures) {
        this.measures = measures;
    }
}
