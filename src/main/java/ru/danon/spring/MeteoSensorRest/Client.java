package ru.danon.spring.MeteoSensorRest;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        final String sensorName = "Sensor-4";

        registerSensor(sensorName);

        Random random = new Random();

        for (int i = 0; i < 500; i++) {
            System.out.println(i);
            sendMeasurement(BigDecimal.valueOf(-100.0 + 200.0 * random.nextDouble())
                            .setScale(2, RoundingMode.HALF_UP),
                    random.nextBoolean(), sensorName);
        }
    }

    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8070/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("sensorName", sensorName);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void sendMeasurement(BigDecimal value, boolean raining, String sensorName) {
        final String url = "http://localhost:8070/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("sensorName", sensorName));

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("Измерение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}